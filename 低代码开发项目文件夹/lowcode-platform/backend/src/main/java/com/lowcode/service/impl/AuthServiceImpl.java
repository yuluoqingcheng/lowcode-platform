package com.lowcode.service.impl;

import com.lowcode.common.BusinessException;
import com.lowcode.dto.LoginRequest;
import com.lowcode.dto.RegisterRequest;
import com.lowcode.entity.User;
import com.lowcode.service.AuthService;
import com.lowcode.service.UserService;
import com.lowcode.util.JwtUtil;
import com.lowcode.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.expire:604800}")
    private Long jwtExpire;

    private static final String REDIS_TOKEN_PREFIX = "token:";
    private static final String REDIS_BLACKLIST_PREFIX = "blacklist:";

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查询用户
        User user = userService.getByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账户已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 生成令牌
        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        // 存储令牌到Redis
        redisTemplate.opsForValue().set(
            REDIS_TOKEN_PREFIX + user.getId(),
            accessToken,
            jwtExpire,
            TimeUnit.SECONDS
        );

        // 更新最后登录信息
        userService.updateLastLogin(user.getId(), request.getLoginIp());

        // 构建响应
        LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .realName(user.getRealName())
            .avatar(user.getAvatar())
            .userType(user.getUserType())
            .build();

        return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(jwtExpire)
            .userInfo(userInfo)
            .build();
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userService.existsByUsername(request.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userService.existsByEmail(request.getEmail())) {
            throw new BusinessException("邮箱已被使用");
        }

        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRealName(request.getRealName());
        user.setStatus(1); // 启用状态
        user.setUserType(1); // 普通用户
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
        log.info("用户注册成功: {}", request.getUsername());
    }

    @Override
    public void logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            // 验证令牌并获取用户ID
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 将令牌加入黑名单
            Long expiration = jwtUtil.getExpirationFromToken(token);
            if (expiration != null) {
                long ttl = expiration - System.currentTimeMillis() / 1000;
                if (ttl > 0) {
                    redisTemplate.opsForValue().set(
                        REDIS_BLACKLIST_PREFIX + token,
                        "blacklisted",
                        ttl,
                        TimeUnit.SECONDS
                    );
                }
            }

            // 删除Redis中的用户令牌
            redisTemplate.delete(REDIS_TOKEN_PREFIX + userId);
            
            log.info("用户登出成功: userId={}", userId);
        } catch (Exception e) {
            log.warn("登出时令牌验证失败: {}", e.getMessage());
        }
    }

    @Override
    public LoginResponse refreshToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 验证令牌
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException("令牌无效");
        }

        // 获取用户信息
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        
        User user = userService.getById(userId);
        if (user == null || user.getStatus() == 0) {
            throw new BusinessException("用户不存在或已被禁用");
        }

        // 生成新令牌
        String newAccessToken = jwtUtil.generateToken(userId, username);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId, username);

        // 更新Redis中的令牌
        redisTemplate.opsForValue().set(
            REDIS_TOKEN_PREFIX + userId,
            newAccessToken,
            jwtExpire,
            TimeUnit.SECONDS
        );

        // 将旧令牌加入黑名单
        Long expiration = jwtUtil.getExpirationFromToken(token);
        if (expiration != null) {
            long ttl = expiration - System.currentTimeMillis() / 1000;
            if (ttl > 0) {
                redisTemplate.opsForValue().set(
                    REDIS_BLACKLIST_PREFIX + token,
                    "blacklisted",
                    ttl,
                    TimeUnit.SECONDS
                );
            }
        }

        // 构建响应
        LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .realName(user.getRealName())
            .avatar(user.getAvatar())
            .userType(user.getUserType())
            .build();

        return LoginResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .tokenType("Bearer")
            .expiresIn(jwtExpire)
            .userInfo(userInfo)
            .build();
    }
}