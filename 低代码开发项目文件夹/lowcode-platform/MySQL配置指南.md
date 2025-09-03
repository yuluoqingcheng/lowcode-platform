# MySQL数据库配置指南

## 第一步：连接MySQL数据库
```sql
-- 使用root用户连接MySQL (请根据您的实际密码替换)
mysql -u root -p
```

## 第二步：创建数据库
```sql
-- 创建数据库，指定字符集和排序规则
CREATE DATABASE lowcode_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 验证数据库创建成功
SHOW DATABASES;

-- 切换到新创建的数据库
USE lowcode_platform;
```

## 第三步：创建专用数据库用户（可选，推荐）
```sql
-- 创建专用用户（更安全）
CREATE USER 'lowcode_user'@'localhost' IDENTIFIED BY 'lowcode123456';

-- 授予权限
GRANT ALL PRIVILEGES ON lowcode_platform.* TO 'lowcode_user'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;

-- 验证用户创建成功
SELECT User, Host FROM mysql.user WHERE User = 'lowcode_user';
```

## 第四步：导入数据库结构
有两种方式导入我们的初始化脚本：

### 方式1：命令行导入（推荐）
```bash
# 在Windows命令行或PowerShell中执行
# 请替换为您的实际密码和文件路径
mysql -u root -p lowcode_platform < "d:\大数据创新创业平台\雨落倾城自己项目\低代码开发平台\项目文件夹\lowcode-platform\database\init.sql"
```

### 方式2：MySQL客户端内导入
```sql
-- 在MySQL客户端中执行
USE lowcode_platform;
SOURCE d:/大数据创新创业平台/雨落倾城自己项目/低代码开发平台/项目文件夹/lowcode-platform/database/init.sql;
```

## 第五步：验证表结构
```sql
-- 查看所有表
SHOW TABLES;

-- 查看用户表结构
DESCRIBE sys_user;

-- 验证是否有数据
SELECT COUNT(*) FROM sys_user;
```

## 数据库连接配置
如果您使用了专用用户，需要更新后端配置文件：

文件位置：`backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lowcode_platform?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
    username: lowcode_user  # 使用专用用户
    password: lowcode123456  # 专用用户密码
```

## 常见问题解决

### 1. 字符编码问题
确保MySQL配置文件(my.ini或my.cnf)包含：
```ini
[client]
default-character-set=utf8mb4

[mysql]
default-character-set=utf8mb4

[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
```

### 2. 时区问题
如果遇到时区错误，在MySQL中执行：
```sql
SET GLOBAL time_zone = '+8:00';
```

### 3. 连接权限问题
如果遇到连接被拒绝，执行：
```sql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'your_password';
FLUSH PRIVILEGES;
```