# ⚙️ 低代码开发平台 - 后端

> 基于 Spring Boot 3.x + MySQL + Redis 构建的现代化企业级后端服务。

## ✨ 特性

- 🍃 **Spring Boot 3.x** - 最新的企业级Java框架
- 🔐 **Spring Security 6.x** - 强大的安全框架
- 🗄️ **MySQL 8.0** - 稳定的关系型数据库
- 🚀 **Redis 7.0+** - 高性能缓存数据库
- 🔑 **JWT** - 无状态令牌认证
- 📚 **Knife4j** - 优雅的API文档
- 🔨 **Maven** - 项目构建管理

## 🚀 快速开始

### 环境要求
- Java >= 17
- Maven >= 3.8 (可使用 Maven Wrapper)
- MySQL >= 8.0
- Redis >= 7.0 (可选)

### 数据库配置
```sql
-- 创建数据库
CREATE DATABASE lowcode_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入初始化脚本
SOURCE /path/to/database/init.sql;
```

### 配置文件
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lowcode_platform
    username: root
    password: your_password
```

### 启动服务
```bash
# 使用 Maven Wrapper (推荐)
./mvnw.cmd spring-boot:run

# 或使用本地 Maven
mvn spring-boot:run
```

服务访问地址：
- 🌐 **API 接口**: http://localhost:8080/api
- 📖 **API 文档**: http://localhost:8080/doc.html
- ❤️ **健康检查**: http://localhost:8080/api/test/health

## 📁 项目结构

```
src/main/java/com/lowcode/
├── controller/                # 控制器层
│   ├── AuthController.java   # 认证控制器
│   ├── TestController.java   # 测试控制器
│   └── SimpleTestController.java # 简化测试控制器
├── service/                   # 服务层
│   ├── AuthService.java      # 认证服务接口
│   └── impl/
│       ├── AuthServiceImpl.java # 认证服务实现
│       └── UserServiceImpl.java # 用户服务实现
├── mapper/                    # 数据访问层
│   └── UserMapper.java       # 用户数据访问
├── entity/                    # 实体类
│   ├── User.java            # 用户实体
│   └── Project.java         # 项目实体
├── dto/                      # 数据传输对象
│   ├── LoginRequest.java    # 登录请求
│   └── RegisterRequest.java # 注册请求
├── vo/                       # 视图对象
│   └── LoginResponse.java   # 登录响应
├── config/                   # 配置类
│   ├── SecurityConfig.java  # 安全配置 (临时禁用)
│   └── MybatisPlusConfig.java # MyBatis配置 (临时禁用)
├── security/                 # 安全相关
│   ├── JwtAuthenticationFilter.java     # JWT过滤器 (临时禁用)
│   └── JwtAuthenticationEntryPoint.java # JWT入口点 (临时禁用)
├── util/                     # 工具类
│   └── JwtUtil.java         # JWT工具类
├── common/                   # 公共类
│   ├── Result.java          # 统一响应结果
│   └── PageResult.java      # 分页结果
├── exception/                # 异常处理
│   ├── GlobalExceptionHandler.java # 全局异常处理
│   └── BusinessException.java      # 业务异常
└── LowcodeBackendApplication.java  # 启动类
```

## 🎯 核心功能

### ✅ 已实现功能
- **基础服务框架** - Spring Boot 3.x 完整配置
- **数据库连接** - MySQL 8.0 连接池配置
- **API 接口** - RESTful API 设计规范
- **统一响应** - 标准化的响应格式
- **异常处理** - 全局异常捕获和处理
- **健康检查** - 服务状态监控端点
- **API 文档** - Knife4j 自动生成文档
- **请求验证** - 参数校验和数据验证

### ⏳ 开发中功能
- **用户认证系统** - JWT 令牌认证 (兼容性调试中)
- **权限管理** - RBAC 基于角色的访问控制
- **数据持久化** - MyBatis Plus ORM (兼容性调试中)
- **Redis 缓存** - 分布式缓存支持
- **项目管理 API** - 低代码项目 CRUD
- **组件管理 API** - 可视化组件管理
- **代码生成 API** - 多端代码生成服务

### 🔄 兼容性状态
- ❌ **MyBatis Plus 3.5.5** - Spring Boot 3.x 兼容性问题，临时禁用
- ❌ **Spring Security** - 依赖 MyBatis Plus，临时禁用
- ❌ **Redis 集成** - 临时禁用，可按需启用

## 🛠️ 技术架构

### 核心依赖
```xml
<!-- Spring Boot 基础 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.2.0</version>
</dependency>

<!-- 数据库 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<!-- API 文档 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.4.0</version>
</dependency>
```

### 配置文件
```yaml
# 服务配置
server:
  port: 8080
  servlet:
    context-path: /api

# 数据源配置
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/lowcode_platform
    username: root
    password: fuyuhao925
    hikari:
      maximum-pool-size: 20
```

## 🔧 开发指南

### 添加新的 API 接口
1. 在 `controller` 包下创建控制器
2. 使用 `@RestController` 和 `@RequestMapping` 注解
3. 返回统一的 `Result<T>` 响应格式
4. 添加接口文档注解

### 数据库操作
当前使用原生 JDBC 或 Spring Data JPA：
```java
@Autowired
private JdbcTemplate jdbcTemplate;

public List<User> findAll() {
    return jdbcTemplate.query("SELECT * FROM users", 
           new BeanPropertyRowMapper<>(User.class));
}
```

### 统一响应格式
```java
// 成功响应
return Result.success("操作成功", data);

// 失败响应
return Result.error("操作失败");

// 分页响应
return Result.success("查询成功", PageResult.of(list, total));
```

## 🧪 测试

### 单元测试
```bash
mvn test
```

### 集成测试
```bash
mvn verify
```

### API 测试
访问 http://localhost:8080/doc.html 使用在线文档测试

## 📦 构建和部署

### 本地构建
```bash
mvn clean package
```

### 生产部署
```bash
# 生成 JAR 包
mvn clean package -Dmaven.test.skip=true

# 运行服务
java -jar target/lowcode-backend-1.0.0.jar

# 后台运行
nohup java -jar target/lowcode-backend-1.0.0.jar > app.log 2>&1 &
```

### Docker 部署 (规划中)
```dockerfile
FROM openjdk:17-jre-slim
COPY target/lowcode-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🐛 故障排除

### 常见问题

1. **启动失败 - MyBatis Plus 兼容性错误**
   ```
   错误: Invalid value type for attribute 'factoryBeanObjectType'
   解决: 已临时禁用 MyBatis Plus，使用替代方案
   ```

2. **数据库连接失败**
   ```
   检查: MySQL 服务状态、连接信息、防火墙设置
   ```

3. **端口占用**
   ```bash
   # 检查端口占用
   netstat -ano | findstr :8080
   
   # 修改端口配置
   server.port=8081
   ```

### 日志配置
```yaml
logging:
  level:
    com.lowcode: debug
    org.springframework.web: info
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{50} - %msg%n"
```

## 📚 技术文档

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring Security 文档](https://spring.io/projects/spring-security)
- [MySQL 文档](https://dev.mysql.com/doc/)
- [Knife4j 文档](https://doc.xiaominfo.com/)
- [Maven 官方文档](https://maven.apache.org/)

## 🔄 更新日志

### v1.0.0 (2025-09-03)
- ✅ 完成 Spring Boot 3.x 基础架构
- ✅ 配置 MySQL 数据库连接
- ✅ 集成 Knife4j API 文档
- ✅ 实现统一响应格式
- ✅ 添加全局异常处理
- ✅ 完成健康检查接口
- ⚠️ 临时禁用 MyBatis Plus (兼容性问题)
- ⚠️ 临时禁用 Spring Security (依赖问题)

### 下一版本规划
- 🔧 解决 MyBatis Plus 兼容性问题
- 🔐 恢复完整的认证授权功能
- 📊 实现项目管理 API
- 🎨 添加组件管理 API
- 🚀 集成 Redis 缓存

---

**💪 目标：构建稳定、高效、易维护的企业级后端服务**