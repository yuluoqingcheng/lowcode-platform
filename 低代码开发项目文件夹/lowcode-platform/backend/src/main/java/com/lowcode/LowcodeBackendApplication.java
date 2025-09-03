package com.lowcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 低代码开发平台启动类
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@SpringBootApplication
public class LowcodeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LowcodeBackendApplication.class, args);
        System.out.println("🚀 低代码开发平台后端服务启动成功！");
        System.out.println("📖 API文档地址: http://localhost:8080/doc.html");
    }

}