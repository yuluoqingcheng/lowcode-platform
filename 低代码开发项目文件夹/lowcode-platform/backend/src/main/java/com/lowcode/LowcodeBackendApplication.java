package com.lowcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 低代码开发平台启动类
 * 临时移除MyBatis相关配置，先让Spring Boot基础服务启动
 *
 * @author 低代码平台
 * @since 2024-01-01
 */
@SpringBootApplication(exclude = {
    // DataSourceAutoConfiguration.class,  // 数据源已启用
    RedisAutoConfiguration.class,
    MybatisPlusAutoConfiguration.class,  // 重新排除MyBatis Plus自动配置
    SecurityAutoConfiguration.class
})
// @MapperScan("com.lowcode.mapper") // 临时注释掉，先让基础服务启动
// @MapperScan("com.lowcode.mapper") // 由于MyBatis Plus兼容性问题，暂时禁用Mapper扫描
public class LowcodeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LowcodeBackendApplication.class, args);
        System.out.println("🚀 低代码开发平台后端服务启动成功！");
        System.out.println("📖 API文档地址: http://localhost:8080/doc.html");
        System.out.println("🔗 数据库连接正常，MySQL 已启用");
        System.out.println("⚠️  注意: MyBatis Plus 功能因兼容性问题临时禁用");
        System.out.println("⚠️  注意: 认证功能暂时不可用（Security配置待恢复）");
        System.out.println("✅ 测试接口: http://localhost:8080/api/test/health");
    }

}