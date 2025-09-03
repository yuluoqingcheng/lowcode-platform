package com.lowcode.controller;

import com.lowcode.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Tag(name = "系统测试", description = "系统功能测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "公开测试接口")
    @GetMapping("/public")
    public Result<Map<String, Object>> publicTest() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "公开接口访问成功");
        data.put("timestamp", LocalDateTime.now());
        data.put("status", "ok");
        return Result.success(data);
    }

    @Operation(summary = "需要认证的测试接口")
    @GetMapping("/auth")
    public Result<Map<String, Object>> authTest(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> data = new HashMap<>();
        data.put("message", "认证接口访问成功");
        data.put("timestamp", LocalDateTime.now());
        data.put("authenticated", authentication.isAuthenticated());
        data.put("principal", authentication.getName());
        data.put("userId", request.getAttribute("userId"));
        
        return Result.success(data);
    }

    @Operation(summary = "系统健康检查")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", LocalDateTime.now());
        data.put("service", "lowcode-backend");
        data.put("version", "1.0.0");
        
        return Result.success("系统运行正常", data);
    }
}