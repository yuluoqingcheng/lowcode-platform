# 🎨 低代码开发平台 - 前端

> 基于 Vue 3 + TypeScript + Vite 构建的现代化可视化拖拽式Web应用开发前端。

## ✨ 特性

- 🎯 **Vue 3 Composition API** - 现代化响应式框架
- 🔷 **TypeScript** - 完整的类型安全支持
- ⚡ **Vite** - 极速的开发体验
- 🎨 **Element Plus** - 优雅的 UI 组件库
- 🍍 **Pinia** - 直观的状态管理
- 🛣️ **Vue Router** - 官方路由管理
- 📝 **Monaco Editor** - 专业的代码编辑器

## 🚀 快速开始

### 环境要求
- Node.js >= 18.0.0
- npm >= 8.0.0

### 安装依赖
```bash
npm install
```

### 开发环境启动
```bash
npm run dev
```

访问: http://localhost:3000

### 构建生产版本
```bash
npm run build
```

### 预览构建结果
```bash
npm run preview
```

## 📁 项目结构

```
src/
├── components/                # 公共组件
│   ├── editor/               # 编辑器相关组件
│   │   ├── DesignCanvas.vue  # 设计画布
│   │   ├── ComponentPanel.vue # 组件面板
│   │   └── PropertyPanel.vue # 属性面板
│   └── canvas/               # 画布渲染组件
│       ├── CanvasButton.vue  # 按钮组件
│       ├── CanvasInput.vue   # 输入框组件
│       ├── CanvasText.vue    # 文本组件
│       └── CanvasContainer.vue # 容器组件
├── views/                    # 页面组件
│   ├── HomePage.vue          # 首页
│   ├── EditorPage.vue        # 编辑器页面
│   ├── ProjectsPage.vue      # 项目管理页面
│   ├── ComponentsPage.vue    # 组件库页面
│   └── SettingsPage.vue      # 设置页面
├── stores/                   # Pinia 状态管理
│   └── component.ts          # 组件状态管理
├── router/                   # Vue Router 配置
│   └── index.ts              # 路由配置
├── utils/                    # 工具函数
├── types/                    # TypeScript 类型定义
├── assets/                   # 静态资源
└── App.vue                   # 根组件
```

## 🎨 核心功能

### 可视化编辑器
- ✅ **拖拽式设计** - 直观的组件拖拽操作
- ✅ **实时预览** - 编辑即预览，所见即所得
- ✅ **组件树** - 清晰的组件层级结构
- ✅ **属性面板** - 可视化属性配置
- ✅ **画布操作** - 缩放、移动、选择等

### 组件库系统
- ✅ **基础组件** - 按钮、输入框、文本、容器
- ✅ **布局组件** - 栅格、弹性布局
- ⏳ **表单组件** - 开发中
- ⏳ **展示组件** - 开发中
- ⏳ **导航组件** - 开发中

### 项目管理
- ✅ **项目创建** - 快速创建新项目
- ✅ **项目保存** - 本地存储项目数据
- ✅ **项目加载** - 恢复历史项目
- ⏳ **云端同步** - 开发中

### 代码生成
- ✅ **Vue 代码生成** - 生成 Vue 3 组件代码
- ⏳ **React 代码生成** - 开发中
- ⏳ **微信小程序** - 开发中
- ⏳ **TypeScript 支持** - 开发中

## 🛠️ 开发指南

### 添加新组件
1. 在 `src/components/canvas/` 下创建组件文件
2. 在组件库中注册组件
3. 添加组件配置和属性定义
4. 更新类型定义

### 状态管理
使用 Pinia 进行状态管理，主要 store：
- `componentStore` - 组件相关状态
- `editorStore` - 编辑器状态
- `projectStore` - 项目状态

### 路由配置
路由配置位于 `src/router/index.ts`，支持：
- 页面路由
- 嵌套路由
- 路由守卫
- 懒加载

## 📦 构建配置

### Vite 配置
- 别名配置
- 插件集成
- 开发服务器配置
- 构建优化

### TypeScript 配置
- 严格模式
- 路径映射
- 类型检查
- Vue SFC 支持

## 🔧 开发工具

### 推荐 VSCode 插件
- Vue Language Features (Volar)
- TypeScript Vue Plugin (Volar)
- ESLint
- Prettier
- Auto Rename Tag

### 代码规范
- ESLint + Prettier 自动格式化
- Vue 3 官方风格指南
- TypeScript 严格模式
- Git hooks 提交检查

## 🚀 性能优化

- **代码分割** - 路由级别的懒加载
- **虚拟滚动** - 大列表性能优化
- **组件懒加载** - 按需加载组件
- **图片懒加载** - 图片资源优化
- **缓存策略** - 智能缓存机制

## 🐛 调试

### 开发工具
- Vue DevTools
- Vite HMR
- TypeScript 类型检查
- ESLint 实时检查

### 常见问题
1. **组件不渲染** - 检查组件注册和类型定义
2. **状态更新异常** - 检查 Pinia store 配置
3. **路由跳转失败** - 检查路由配置和权限
4. **构建失败** - 检查 TypeScript 类型错误

## 📚 相关文档

- [Vue 3 官方文档](https://vuejs.org/)
- [TypeScript 官方文档](https://www.typescriptlang.org/)
- [Vite 官方文档](https://vitejs.dev/)
- [Element Plus 文档](https://element-plus.org/)
- [Pinia 文档](https://pinia.vuejs.org/)

## 🔄 更新日志

### v1.0.0 (2025-09-03)
- ✅ 完成基础架构搭建
- ✅ 实现可视化编辑器核心功能
- ✅ 完成基础组件库
- ✅ 实现项目管理功能
- ✅ 完成代码生成基础功能

---

**🎯 下一步计划：增强组件库、优化性能、完善代码生成功能**