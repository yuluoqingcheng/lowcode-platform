-- 初始化数据脚本
-- 插入系统初始数据

USE `lowcode_platform`;

-- ===========================================
-- 插入默认用户数据
-- ===========================================

-- 插入默认管理员用户 (密码: admin123)
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `real_name`, `status`, `user_type`) VALUES
(1, 'admin', '$2a$10$yGmZGXdT1xKQ.L8DJyZdkeOGd7.H.Dg8Qg7xHg9K9N9N9N9N9N9N9N', 'admin@lowcode.com', '超级管理员', 1, 2),
(2, 'demo', '$2a$10$yGmZGXdT1xKQ.L8DJyZdkeOGd7.H.Dg8Qg7xHg9K9N9N9N9N9N9N9N', 'demo@lowcode.com', '演示用户', 1, 1);

-- 插入默认角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `status`, `sort_order`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', '拥有系统所有权限', 1, 1),
(2, '普通管理员', 'ADMIN', '拥有基本管理权限', 1, 2),
(3, '普通用户', 'USER', '基础用户权限', 1, 3);

-- 分配用户角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES 
(1, 1),  -- admin用户分配超级管理员角色
(2, 3);  -- demo用户分配普通用户角色

-- ===========================================
-- 插入基础组件库数据
-- ===========================================

INSERT INTO `component_library` (`name`, `type`, `category`, `description`, `icon`, `default_props`, `default_style`, `status`, `usage_count`) VALUES
-- 基础组件
('按钮', 'button', 'basic', '触发业务逻辑的按钮组件', 'el-icon-cpu', 
 '{"text": "按钮", "type": "primary", "size": "default", "disabled": false}', 
 '{"backgroundColor": "#409eff", "color": "#fff", "border": "none", "borderRadius": "4px", "padding": "8px 16px"}', 
 1, 0),

('文本', 'text', 'basic', '用于显示文本内容', 'el-icon-document', 
 '{"content": "文本内容", "fontSize": 14, "color": "#333", "textAlign": "left", "fontWeight": "normal"}', 
 '{"color": "#333", "fontSize": "14px", "lineHeight": "1.5"}', 
 1, 0),

('图片', 'image', 'basic', '图片展示组件', 'el-icon-picture', 
 '{"src": "", "alt": "图片", "fit": "cover", "borderRadius": 0}', 
 '{"width": "100%", "height": "100%", "objectFit": "cover"}', 
 1, 0),

-- 布局组件
('容器', 'container', 'layout', '用于包装其他组件的容器', 'el-icon-box', 
 '{"padding": 16, "backgroundColor": "transparent", "borderColor": "#dcdfe6", "borderStyle": "dashed", "borderWidth": 1}', 
 '{"border": "1px dashed #ddd", "minHeight": "100px", "backgroundColor": "transparent"}', 
 1, 0),

('卡片', 'card', 'layout', '卡片容器组件', 'el-icon-postcard', 
 '{"title": "卡片标题", "shadow": "always", "bodyStyle": {}}', 
 '{"border": "1px solid #ebeef5", "borderRadius": "4px", "backgroundColor": "#fff"}', 
 1, 0),

-- 表单组件
('输入框', 'input', 'form', '用户输入数据的表单控件', 'el-icon-edit', 
 '{"placeholder": "请输入", "value": "", "type": "text", "clearable": false, "disabled": false}', 
 '{"border": "1px solid #ddd", "borderRadius": "4px", "padding": "8px 12px"}', 
 1, 0),

('选择器', 'select', 'form', '下拉选择表单控件', 'el-icon-arrow-down', 
 '{"placeholder": "请选择", "value": "", "options": [], "clearable": false, "disabled": false}', 
 '{"border": "1px solid #ddd", "borderRadius": "4px", "minWidth": "120px"}', 
 1, 0),

-- 数据展示组件
('表格', 'table', 'display', '数据表格展示组件', 'el-icon-s-grid', 
 '{"data": [], "columns": [], "border": true, "stripe": false, "size": "default"}', 
 '{"width": "100%", "border": "1px solid #ebeef5"}', 
 1, 0),

('列表', 'list', 'display', '数据列表展示组件', 'el-icon-s-order', 
 '{"data": [], "bordered": false, "split": true, "size": "default"}', 
 '{"width": "100%"}', 
 1, 0);

-- ===========================================
-- 插入系统配置数据
-- ===========================================

INSERT INTO `sys_config` (`config_key`, `config_value`, `config_type`, `description`, `group_name`, `is_system`) VALUES
('system.title', '低代码开发平台', 'string', '系统标题', 'system', 1),
('system.version', '1.0.0', 'string', '系统版本', 'system', 1),
('system.logo', '/logo.png', 'string', '系统Logo', 'system', 1),
('user.default.avatar', '/default-avatar.png', 'string', '用户默认头像', 'user', 1),
('user.password.min_length', '6', 'number', '用户密码最小长度', 'user', 1),
('project.max_count_per_user', '50', 'number', '每个用户最大项目数量', 'project', 1),
('code.generation.output_path', './generated', 'string', '代码生成输出路径', 'codegen', 1),
('file.upload.max_size', '10485760', 'number', '文件上传最大大小(字节)', 'file', 1),
('jwt.expire_days', '7', 'number', 'JWT过期天数', 'security', 1);

-- ===========================================
-- 插入示例项目数据
-- ===========================================

INSERT INTO `project` (`id`, `name`, `description`, `template_type`, `status`, `user_id`, `version`, `config`) VALUES
(1, '电商管理系统', '基于Vue3的电商后台管理系统，包含商品管理、订单管理等功能', 'admin', 1, 2, '1.0.0', 
 '{"theme": "default", "layout": "sidebar", "language": "zh-CN"}'),
(2, '企业官网', '响应式企业官网，包含首页、产品展示、关于我们等页面', 'landing', 1, 2, '1.0.0', 
 '{"theme": "business", "layout": "top", "language": "zh-CN"}');

-- 插入示例页面数据
INSERT INTO `project_page` (`project_id`, `name`, `path`, `title`, `description`, `components`, `sort_order`, `status`) VALUES
(1, '首页', '/dashboard', '仪表板', '系统首页仪表板', 
 '[{"id": "1", "type": "card", "props": {"title": "欢迎使用"}, "style": {"width": "100%", "height": "200px"}}]', 
 1, 1),
(1, '用户管理', '/users', '用户管理', '系统用户管理页面', 
 '[{"id": "2", "type": "table", "props": {"data": [], "columns": []}, "style": {"width": "100%"}}]', 
 2, 1),
(2, '首页', '/', '企业首页', '企业官网首页', 
 '[{"id": "3", "type": "container", "props": {}, "style": {"width": "100%", "minHeight": "100vh"}}]', 
 1, 1);