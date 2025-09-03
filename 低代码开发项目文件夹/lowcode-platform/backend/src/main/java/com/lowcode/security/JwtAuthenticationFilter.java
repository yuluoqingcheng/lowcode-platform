package com.lowcode.security;

import com.lowcode.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT认证过滤器
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String REDIS_BLACKLIST_PREFIX = "blacklist:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = getJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                // 检查令牌是否在黑名单中
                if (isTokenBlacklisted(jwt)) {
                    log.warn("令牌已在黑名单中: {}", jwt.substring(0, Math.min(jwt.length(), 20)) + "...");
                } else {
                    // 从令牌中获取用户信息
                    Long userId = jwtUtil.getUserIdFromToken(jwt);
                    String username = jwtUtil.getUsernameFromToken(jwt);
                    
                    if (userId != null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        // 创建认证对象
                        UserDetails userDetails = User.builder()
                            .username(username)
                            .password("") // JWT认证不需要密码
                            .authorities(new ArrayList<>()) // 这里可以根据需要添加权限
                            .build();
                        
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        // 设置到Security上下文
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        // 将用户ID添加到请求属性中，方便控制器使用
                        request.setAttribute("userId", userId);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("无法设置用户认证: {}", ex.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中获取JWT令牌
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 检查令牌是否在黑名单中
     */
    private boolean isTokenBlacklisted(String token) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_BLACKLIST_PREFIX + token));
        } catch (Exception e) {
            log.warn("检查令牌黑名单时发生错误: {}", e.getMessage());
            return false;
        }
    }
}