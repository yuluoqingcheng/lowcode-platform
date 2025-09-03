-- 低代码开发平台数据库初始化脚本
-- 创建时间: 2024-01-01
-- 描述: 包含用户管理、项目管理、组件配置等核心表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `lowcode_platform` 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lowcode_platform`;

-- ===========================================
-- 用户管理相关表
-- ===========================================

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `avatar` VARCHAR(200) DEFAULT NULL COMMENT '头像URL',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `user_type` TINYINT NOT NULL DEFAULT 1 COMMENT '用户类型：1-普通用户，2-管理员',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_status` (`status`),
  KEY `idx_user_type` (`user_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `fk_user_role_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ===========================================
-- 项目管理相关表
-- ===========================================

-- 项目表
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` VARCHAR(100) NOT NULL COMMENT '项目名称',
  `description` TEXT COMMENT '项目描述',
  `thumbnail` VARCHAR(500) DEFAULT NULL COMMENT '项目缩略图',
  `template_type` VARCHAR(50) DEFAULT 'blank' COMMENT '项目模板类型',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用，2-已发布',
  `user_id` BIGINT NOT NULL COMMENT '创建者ID',
  `version` VARCHAR(20) DEFAULT '1.0.0' COMMENT '项目版本',
  `config` JSON DEFAULT NULL COMMENT '项目配置信息',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  CONSTRAINT `fk_project_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目表';

-- 页面表
DROP TABLE IF EXISTS `project_page`;
CREATE TABLE `project_page` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '页面ID',
  `project_id` BIGINT NOT NULL COMMENT '项目ID',
  `name` VARCHAR(100) NOT NULL COMMENT '页面名称',
  `path` VARCHAR(200) DEFAULT NULL COMMENT '页面路径',
  `title` VARCHAR(100) DEFAULT NULL COMMENT '页面标题',
  `description` TEXT COMMENT '页面描述',
  `config` JSON DEFAULT NULL COMMENT '页面配置数据',
  `components` JSON DEFAULT NULL COMMENT '页面组件配置',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_sort_order` (`sort_order`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_page_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目页面表';

-- ===========================================
-- 组件管理相关表
-- ===========================================

-- 组件库表
DROP TABLE IF EXISTS `component_library`;
CREATE TABLE `component_library` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '组件ID',
  `name` VARCHAR(100) NOT NULL COMMENT '组件名称',
  `type` VARCHAR(50) NOT NULL COMMENT '组件类型',
  `category` VARCHAR(50) NOT NULL COMMENT '组件分类',
  `description` TEXT COMMENT '组件描述',
  `icon` VARCHAR(100) DEFAULT NULL COMMENT '组件图标',
  `thumbnail` VARCHAR(500) DEFAULT NULL COMMENT '组件缩略图',
  `config_schema` JSON DEFAULT NULL COMMENT '配置项schema',
  `default_props` JSON DEFAULT NULL COMMENT '默认属性',
  `default_style` JSON DEFAULT NULL COMMENT '默认样式',
  `version` VARCHAR(20) DEFAULT '1.0.0' COMMENT '组件版本',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `usage_count` INT DEFAULT 0 COMMENT '使用次数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type` (`type`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_usage_count` (`usage_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组件库表';

-- ===========================================
-- 代码生成相关表
-- ===========================================

-- 代码模板表
DROP TABLE IF EXISTS `code_template`;
CREATE TABLE `code_template` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `name` VARCHAR(100) NOT NULL COMMENT '模板名称',
  `type` VARCHAR(50) NOT NULL COMMENT '模板类型：vue, java, sql等',
  `category` VARCHAR(50) NOT NULL COMMENT '模板分类',
  `description` TEXT COMMENT '模板描述',
  `template_content` LONGTEXT NOT NULL COMMENT '模板内容',
  `variables` JSON DEFAULT NULL COMMENT '模板变量',
  `version` VARCHAR(20) DEFAULT '1.0.0' COMMENT '模板版本',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码模板表';

-- 代码生成记录表
DROP TABLE IF EXISTS `code_generation_log`;
CREATE TABLE `code_generation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `project_id` BIGINT NOT NULL COMMENT '项目ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `generation_type` VARCHAR(50) NOT NULL COMMENT '生成类型：vue, java, database等',
  `config` JSON DEFAULT NULL COMMENT '生成配置',
  `file_count` INT DEFAULT 0 COMMENT '生成文件数量',
  `file_list` JSON DEFAULT NULL COMMENT '生成文件列表',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-失败，1-成功，2-进行中',
  `error_message` TEXT COMMENT '错误信息',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_generation_type` (`generation_type`),
  KEY `idx_create_time` (`create_time`),
  CONSTRAINT `fk_generation_log_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_generation_log_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成记录表';

-- ===========================================
-- 系统配置相关表
-- ===========================================

-- 系统配置表
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
  `config_value` TEXT COMMENT '配置值',
  `config_type` VARCHAR(50) DEFAULT 'string' COMMENT '配置类型',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '配置描述',
  `group_name` VARCHAR(50) DEFAULT 'default' COMMENT '配置分组',
  `is_system` TINYINT DEFAULT 0 COMMENT '是否系统配置：0-否，1-是',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`),
  KEY `idx_group_name` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';