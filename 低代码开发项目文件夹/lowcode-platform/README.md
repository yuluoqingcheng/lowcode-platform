# 🚀 低代码开发平台 - 可视化Web应用构建工具

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue.js-3.3.8-4FC08D.svg)](https://vuejs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0+-blue.svg)](https://www.typescriptlang.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-orange.svg)](https://www.mysql.com/)

> 一个现代化的可视化拖拽式Web应用开发平台，面向非技术人员和开发者，支持通过拖拽方式快速构建Web应用、小程序和后端服务。

## ✨ 核心特性

- 🎨 **可视化设计器** - 拖拽式组件编辑，所见即所得
- ⚡ **实时渲染引擎** - 即时预览，快速迭代
- 🧩 **丰富组件库** - 内置常用组件，支持自定义扩展
- 🤖 **智能代码生成** - 多端代码输出（Vue、React、微信小程序）
- 📊 **项目管理** - 完整的项目生命周期管理
- 🔐 **安全认证** - JWT认证 + RBAC权限控制
- 🚀 **一键部署** - 自动化构建和部署流程

## 🏗️ 技术架构

### 前端技术栈
- **Vue 3** - 渐进式前端框架
- **TypeScript** - 静态类型检查
- **Vite** - 现代化构建工具
- **Element Plus** - 组件库
- **Pinia** - 状态管理
- **Monaco Editor** - 代码编辑器

### 后端技术栈
- **Spring Boot 3.x** - 企业级Java框架
- **Spring Security 6.x** - 安全框架
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存数据库
- **JWT** - 令牌认证
- **Knife4j** - API文档

## 🚀 快速开始

### 环境要求
- Node.js >= 18.0.0
- Java >= 17
- MySQL >= 8.0
- Maven >= 3.8

### 1. 克隆项目
```bash
git clone https://github.com/your-username/lowcode-platform.git
cd lowcode-platform
```

### 2. 前端启动
```bash
cd frontend
npm install
npm run dev
```

前端访问：http://localhost:3000

### 3. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE lowcode_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入初始化脚本
SOURCE /path/to/database/init.sql;
```

### 4. 后端启动
```bash
cd backend
./mvnw.cmd spring-boot:run
```

后端访问：http://localhost:8080/api  
API文档：http://localhost:8080/doc.html  
健康检查：http://localhost:8080/api/test/health

## 📁 项目结构

```
lowcode-platform/
├── frontend/                    # 前端Vue3项目
│   ├── src/
│   │   ├── components/          # 公共组件
│   │   │   ├── editor/          # 编辑器组件
│   │   │   └── canvas/          # 画布渲染组件
│   │   ├── views/               # 页面组件
│   │   │   ├── HomePage.vue     # 首页
│   │   │   ├── EditorPage.vue   # 编辑器页面
│   │   │   └── ProjectsPage.vue # 项目管理页面
│   │   ├── stores/              # Pinia状态管理
│   │   ├── router/              # 路由配置
│   │   ├── utils/               # 工具函数
│   │   └── types/               # TypeScript类型定义
│   ├── public/                  # 静态资源
│   ├── package.json             # 前端依赖配置
│   └── vite.config.ts           # Vite构建配置
├── backend/                     # 后端Spring Boot项目
│   ├── src/main/java/com/lowcode/
│   │   ├── controller/          # 控制器层
│   │   ├── service/             # 服务层
│   │   ├── mapper/              # 数据访问层
│   │   ├── entity/              # 实体类
│   │   ├── dto/                 # 数据传输对象
│   │   ├── vo/                  # 视图对象
│   │   ├── config/              # 配置类
│   │   ├── security/            # 安全相关
│   │   ├── util/                # 工具类
│   │   ├── common/              # 公共类
│   │   └── exception/           # 异常处理
│   ├── src/main/resources/
│   │   ├── mapper/              # MyBatis映射文件
│   │   └── application.yml      # 应用配置
│   └── pom.xml                  # Maven依赖配置
├── database/                    # 数据库相关
│   ├── init.sql                 # 数据库初始化脚本
│   └── data.sql                 # 测试数据脚本
├── docs/                        # 项目文档
├── .gitignore                   # Git忽略文件
├── README.md                    # 项目说明文档
└── package.json                 # 根目录依赖配置
```

## 🎯 功能模块

### 前端功能
- ✅ **可视化编辑器** - 拖拽式组件编辑
- ✅ **组件库系统** - 按钮、输入框、文本、容器等
- ✅ **实时预览** - 编辑即预览
- ✅ **项目管理** - 项目创建、保存、加载
- ✅ **代码生成** - Vue、React、微信小程序代码输出
- ✅ **页面关系图谱** - 可视化页面结构
- ✅ **响应式设计** - 多设备适配

### 后端功能
- ✅ **基础服务框架** - Spring Boot 3.x
- ✅ **数据库连接** - MySQL 8.0
- ✅ **API接口** - RESTful API设计
- ✅ **健康检查** - 服务状态监控
- ⚠️ **用户认证** - JWT认证（开发中）
- ⚠️ **权限管理** - RBAC权限控制（开发中）
- ⚠️ **数据持久化** - MyBatis Plus（兼容性调试中）

## 🔧 开发状态

### ✅ 已完成
- [x] 前端Vue3+TypeScript架构搭建
- [x] 可视化编辑器核心功能
- [x] 组件库基础组件
- [x] 后端Spring Boot服务框架
- [x] 数据库连接配置
- [x] 基础API接口
- [x] 项目构建和部署流程

### 🚧 开发中
- [ ] MyBatis Plus兼容性优化
- [ ] 用户认证系统
- [ ] 权限管理系统
- [ ] Redis缓存集成
- [ ] 更多组件类型
- [ ] 代码生成引擎优化

### 📅 计划中
- [ ] 微信小程序适配
- [ ] 数据可视化组件
- [ ] 工作流组件
- [ ] 插件系统
- [ ] 国际化支持
- [ ] 性能监控

## 🛠️ 构建和部署

### 开发环境构建
```bash
# 前端构建
cd frontend
npm run build

# 后端构建
cd backend
mvn clean package
```

### 生产环境部署
```bash
# Docker构建（规划中）
docker-compose up -d

# 传统部署
# 1. 部署前端静态文件到Nginx
# 2. 部署后端JAR包到服务器
# 3. 配置数据库和Redis
```

## 🤝 贡献指南

欢迎贡献代码！请遵循以下流程：

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📝 开发日志

### 2025-09-03
- ✅ 修复Spring Boot启动问题
- ✅ 解决MyBatis Plus兼容性问题
- ✅ 完善项目文档结构
- ✅ 优化构建和部署流程

## 📄 许可证

MIT License - 详见 [LICENSE](LICENSE) 文件

## 📧 联系方式

- 项目仓库：[GitHub](https://github.com/your-username/lowcode-platform)
- 问题反馈：[Issues](https://github.com/your-username/lowcode-platform/issues)
- 邮箱：dev@lowcode.com

---

**⭐ 如果这个项目对你有帮助，请给个Star支持一下！**