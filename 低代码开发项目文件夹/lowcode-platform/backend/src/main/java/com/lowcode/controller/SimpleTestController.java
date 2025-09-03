package com.lowcode.controller;

import com.lowcode.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 简化测试控制器
 * 不依赖数据库，用于验证Spring Boot基础功能
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "用于验证后端服务基础功能")
public class SimpleTestController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", LocalDateTime.now());
        data.put("message", "低代码平台后端服务运行正常");
        
        log.info("健康检查请求");
        return Result.success("服务健康", data);
    }

    @Operation(summary = "回显测试")
    @PostMapping("/echo")
    public Result<Map<String, Object>> echo(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("received", request);
        response.put("timestamp", LocalDateTime.now());
        response.put("echo", "数据已成功接收并返回");
        
        log.info("回显测试请求: {}", request);
        return Result.success("回显成功", response);
    }

    @Operation(summary = "获取系统信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("projectName", "低代码开发平台");
        info.put("version", "1.0.0");
        info.put("framework", "Spring Boot 3.2.0");
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("currentTime", LocalDateTime.now());
        info.put("status", "基础服务运行中（数据库功能待配置）");
        
        return Result.success("系统信息获取成功", info);
    }
}