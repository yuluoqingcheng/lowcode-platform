# 🗄️ 低代码开发平台 - 数据库

> MySQL 8.0 数据库设计和配置说明

## 📋 数据库概览

- **数据库名称**: `lowcode_platform`
- **字符集**: `utf8mb4`
- **排序规则**: `utf8mb4_unicode_ci`
- **版本要求**: MySQL >= 8.0

## 🚀 快速配置

### 1. 创建数据库
```sql
CREATE DATABASE lowcode_platform 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### 2. 执行初始化脚本
```bash
mysql -u root -p lowcode_platform < init.sql
```

### 3. 验证配置
```sql
USE lowcode_platform;
SHOW TABLES;
```

## 📊 数据表结构

### 用户系统表

#### sys_user (用户表)
```sql
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(200) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(50) COMMENT '最后登录IP',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除 1-已删除'
) COMMENT '用户表';
```

#### sys_role (角色表)
```sql
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description VARCHAR(200) COMMENT '角色描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记'
) COMMENT '角色表';
```

#### sys_permission (权限表)
```sql
CREATE TABLE sys_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    permission_name VARCHAR(50) NOT NULL COMMENT '权限名称',
    permission_code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
    resource_type TINYINT NOT NULL COMMENT '资源类型: 1-菜单 2-按钮 3-接口',
    parent_id BIGINT DEFAULT 0 COMMENT '父权限ID',
    path VARCHAR(200) COMMENT '路径',
    method VARCHAR(10) COMMENT '请求方法',
    description VARCHAR(200) COMMENT '权限描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记'
) COMMENT '权限表';
```

### 项目管理表

#### lc_project (项目表)
```sql
CREATE TABLE lc_project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '项目ID',
    project_name VARCHAR(100) NOT NULL COMMENT '项目名称',
    project_code VARCHAR(50) NOT NULL UNIQUE COMMENT '项目编码',
    description TEXT COMMENT '项目描述',
    project_type TINYINT DEFAULT 1 COMMENT '项目类型: 1-Web应用 2-小程序 3-移动应用',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-开发中 2-已发布',
    template_id BIGINT COMMENT '模板ID',
    thumbnail VARCHAR(200) COMMENT '缩略图',
    config JSON COMMENT '项目配置',
    user_id BIGINT NOT NULL COMMENT '创建用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) COMMENT '项目表';
```

#### lc_page (页面表)
```sql
CREATE TABLE lc_page (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '页面ID',
    project_id BIGINT NOT NULL COMMENT '项目ID',
    page_name VARCHAR(100) NOT NULL COMMENT '页面名称',
    page_code VARCHAR(50) NOT NULL COMMENT '页面编码',
    page_type TINYINT DEFAULT 1 COMMENT '页面类型: 1-普通页面 2-弹窗 3-组件',
    route_path VARCHAR(200) COMMENT '路由路径',
    page_config JSON COMMENT '页面配置',
    page_schema JSON COMMENT '页面结构',
    page_style JSON COMMENT '页面样式',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    FOREIGN KEY (project_id) REFERENCES lc_project(id) ON DELETE CASCADE,
    UNIQUE KEY uk_project_page (project_id, page_code)
) COMMENT '页面表';
```

### 组件管理表

#### lc_component (组件表)
```sql
CREATE TABLE lc_component (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '组件ID',
    component_name VARCHAR(100) NOT NULL COMMENT '组件名称',
    component_code VARCHAR(50) NOT NULL UNIQUE COMMENT '组件编码',
    component_type TINYINT NOT NULL COMMENT '组件类型: 1-基础 2-表单 3-展示 4-导航 5-反馈 6-布局',
    category VARCHAR(50) COMMENT '组件分类',
    description TEXT COMMENT '组件描述',
    icon VARCHAR(100) COMMENT '组件图标',
    preview_image VARCHAR(200) COMMENT '预览图',
    component_schema JSON NOT NULL COMMENT '组件结构定义',
    default_props JSON COMMENT '默认属性',
    style_config JSON COMMENT '样式配置',
    is_system TINYINT DEFAULT 1 COMMENT '是否系统组件: 0-自定义 1-系统',
    is_public TINYINT DEFAULT 1 COMMENT '是否公开: 0-私有 1-公开',
    user_id BIGINT COMMENT '创建用户ID',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    version VARCHAR(20) DEFAULT '1.0.0' COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) COMMENT '组件表';
```

### 代码生成表

#### lc_code_template (代码模板表)
```sql
CREATE TABLE lc_code_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    template_name VARCHAR(100) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) NOT NULL UNIQUE COMMENT '模板编码',
    framework_type TINYINT NOT NULL COMMENT '框架类型: 1-Vue3 2-React 3-小程序 4-Flutter',
    language VARCHAR(20) NOT NULL COMMENT '语言: javascript, typescript',
    template_content LONGTEXT NOT NULL COMMENT '模板内容',
    description TEXT COMMENT '模板描述',
    is_system TINYINT DEFAULT 1 COMMENT '是否系统模板',
    user_id BIGINT COMMENT '创建用户ID',
    version VARCHAR(20) DEFAULT '1.0.0' COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) COMMENT '代码模板表';
```

### 系统日志表

#### sys_log (系统日志表)
```sql
CREATE TABLE sys_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    operation VARCHAR(50) NOT NULL COMMENT '操作类型',
    method VARCHAR(200) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    response_time INT COMMENT '响应时间(ms)',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent VARCHAR(500) COMMENT '用户代理',
    status TINYINT COMMENT '状态: 0-失败 1-成功',
    error_message TEXT COMMENT '错误信息',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) COMMENT '系统日志表';
```

## 🔗 表关系图

```
sys_user (用户)
    ├── lc_project (项目) - 一对多
    │   └── lc_page (页面) - 一对多
    ├── lc_component (组件) - 一对多
    ├── lc_code_template (代码模板) - 一对多
    └── sys_log (日志) - 一对多

sys_role (角色)
    └── sys_user_role (用户角色) - 多对多

sys_permission (权限)
    └── sys_role_permission (角色权限) - 多对多
```

## 🛠️ 索引优化

### 关键索引
```sql
-- 用户表索引
CREATE INDEX idx_user_username ON sys_user(username);
CREATE INDEX idx_user_email ON sys_user(email);
CREATE INDEX idx_user_phone ON sys_user(phone);
CREATE INDEX idx_user_status ON sys_user(status, deleted);

-- 项目表索引
CREATE INDEX idx_project_user ON lc_project(user_id);
CREATE INDEX idx_project_code ON lc_project(project_code);
CREATE INDEX idx_project_status ON lc_project(status, deleted);

-- 页面表索引
CREATE INDEX idx_page_project ON lc_page(project_id);
CREATE INDEX idx_page_route ON lc_page(route_path);

-- 组件表索引
CREATE INDEX idx_component_type ON lc_component(component_type);
CREATE INDEX idx_component_category ON lc_component(category);
CREATE INDEX idx_component_user ON lc_component(user_id);
```

## 🔐 数据库安全

### 用户权限配置
```sql
-- 创建应用用户
CREATE USER 'lowcode_app'@'localhost' IDENTIFIED BY 'secure_password';

-- 授权
GRANT SELECT, INSERT, UPDATE, DELETE ON lowcode_platform.* TO 'lowcode_app'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;
```

### 备份策略
```bash
# 数据备份
mysqldump -u root -p lowcode_platform > backup_$(date +%Y%m%d_%H%M%S).sql

# 数据恢复
mysql -u root -p lowcode_platform < backup_20250903_120000.sql
```

## 📈 性能优化

### 配置建议
```ini
# my.cnf 配置优化
[mysqld]
innodb_buffer_pool_size = 1G
innodb_log_file_size = 256M
innodb_flush_log_at_trx_commit = 2
query_cache_size = 128M
max_connections = 200
```

### 查询优化
- 使用适当的索引
- 避免全表扫描
- 优化 JSON 字段查询
- 合理使用分页

## 🧪 测试数据

### 初始化管理员用户
```sql
INSERT INTO sys_user (username, password, email, nickname, status) 
VALUES ('admin', '$2a$10$encrypted_password', 'admin@lowcode.com', '系统管理员', 1);
```

### 基础角色数据
```sql
INSERT INTO sys_role (role_name, role_code, description) VALUES 
('超级管理员', 'SUPER_ADMIN', '系统超级管理员'),
('管理员', 'ADMIN', '系统管理员'),
('开发者', 'DEVELOPER', '应用开发者'),
('普通用户', 'USER', '普通用户');
```

## 📚 维护命令

### 常用查询
```sql
-- 检查表状态
SHOW TABLE STATUS FROM lowcode_platform;

-- 检查索引使用情况
SHOW INDEX FROM sys_user;

-- 分析表性能
ANALYZE TABLE lc_project;

-- 优化表
OPTIMIZE TABLE sys_log;
```

### 监控查询
```sql
-- 检查连接数
SHOW PROCESSLIST;

-- 检查慢查询
SHOW VARIABLES LIKE 'slow_query_log';

-- 查看数据库大小
SELECT 
    table_schema AS '数据库',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS '大小(MB)'
FROM information_schema.tables 
WHERE table_schema = 'lowcode_platform'
GROUP BY table_schema;
```

---

**💾 保持数据安全，定期备份，监控性能！**