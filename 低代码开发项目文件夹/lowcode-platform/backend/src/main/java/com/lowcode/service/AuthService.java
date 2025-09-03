package com.lowcode.service;

import com.lowcode.dto.LoginRequest;
import com.lowcode.dto.RegisterRequest;
import com.lowcode.vo.LoginResponse;

/**
 * 认证服务接口
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
public interface AuthService {
    
    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户注册
     *
     * @param request 注册请求
     */
    void register(RegisterRequest request);
    
    /**
     * 用户登出
     *
     * @param token JWT令牌
     */
    void logout(String token);
    
    /**
     * 刷新Token
     *
     * @param token 当前Token
     * @return 新的Token信息
     */
    LoginResponse refreshToken(String token);
}