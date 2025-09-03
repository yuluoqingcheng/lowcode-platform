<template>
  <div class="home-page">
    <!-- 头部导航 -->
    <div class="header">
      <div class="header-content">
        <div class="logo">
          <h1>🚀 低代码开发平台</h1>
        </div>
        <div class="nav">
          <el-button text @click="goToEditor">编辑器</el-button>
          <el-button text @click="goToProjects">项目管理</el-button>
          <el-button text @click="goToComponents">组件库</el-button>
          <el-button text @click="goToSettings">设置</el-button>
        </div>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 欢迎区域 -->
      <div class="welcome-section">
        <h2>欢迎使用低代码开发平台</h2>
        <p>通过拖拽方式快速构建Vue应用，支持实时预览和代码生成</p>
        <div class="quick-actions">
          <el-button type="primary" size="large" @click="createNewProject">
            <el-icon><Plus /></el-icon>
            创建新项目
          </el-button>
          <el-button size="large" @click="openExistingProject">
            <el-icon><FolderOpened /></el-icon>
            打开项目
          </el-button>
        </div>
      </div>

      <!-- 功能特性 -->
      <div class="features-section">
        <h3>核心功能</h3>
        <div class="features-grid">
          <div class="feature-card">
            <el-icon size="48" color="#409eff"><Edit /></el-icon>
            <h4>拖拽编辑器</h4>
            <p>可视化拖拽组件，所见即所得的页面设计</p>
          </div>
          <div class="feature-card">
            <el-icon size="48" color="#67c23a"><View /></el-icon>
            <h4>实时预览</h4>
            <p>实时渲染页面效果，支持多端响应式预览</p>
          </div>
          <div class="feature-card">
            <el-icon size="48" color="#e6a23c"><Files /></el-icon>
            <h4>代码生成</h4>
            <p>自动生成Vue组件代码和完整项目结构</p>
          </div>
          <div class="feature-card">
            <el-icon size="48" color="#f56c6c"><Connection /></el-icon>
            <h4>关系图谱</h4>
            <p>可视化展示页面关系和组件使用情况</p>
          </div>
        </div>
      </div>

      <!-- 最近项目 -->
      <div class="recent-projects">
        <h3>最近项目</h3>
        <div class="projects-list">
          <div class="project-card" v-for="project in recentProjects" :key="project.id">
            <div class="project-info">
              <h4>{{ project.name }}</h4>
              <p>{{ project.description }}</p>
              <span class="update-time">{{ project.updateTime }}</span>
            </div>
            <div class="project-actions">
              <el-button size="small" @click="openProject(project)">打开</el-button>
              <el-button size="small" type="danger" @click="deleteProject(project)">删除</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, FolderOpened, Edit, View, Files, Connection } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 最近项目数据
const recentProjects = ref([
  {
    id: 1,
    name: '电商管理系统',
    description: '基于Vue3的电商后台管理系统',
    updateTime: '2024-01-01 10:30'
  },
  {
    id: 2,
    name: '博客网站',
    description: '个人博客展示网站',
    updateTime: '2024-01-02 15:20'
  }
])

// 导航方法
const goToEditor = () => {
  router.push('/editor')
}

const goToProjects = () => {
  router.push('/projects')
}

const goToComponents = () => {
  router.push('/components')
}

const goToSettings = () => {
  router.push('/settings')
}

// 项目操作方法
const createNewProject = () => {
  router.push('/editor')
  ElMessage.success('创建新项目成功！')
}

const openExistingProject = () => {
  ElMessage.info('打开项目功能开发中...')
}

const openProject = (project: any) => {
  router.push('/editor')
  ElMessage.success(`打开项目：${project.name}`)
}

const deleteProject = (project: any) => {
  ElMessage.warning(`删除项目：${project.name}`)
}
</script>

<style scoped>
.home-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo h1 {
  color: white;
  font-size: 24px;
}

.nav {
  display: flex;
  gap: 20px;
}

.nav .el-button {
  color: white;
  font-size: 16px;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.welcome-section {
  text-align: center;
  margin-bottom: 60px;
}

.welcome-section h2 {
  color: white;
  font-size: 48px;
  margin-bottom: 20px;
}

.welcome-section p {
  color: rgba(255, 255, 255, 0.8);
  font-size: 20px;
  margin-bottom: 40px;
}

.quick-actions {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.features-section {
  margin-bottom: 60px;
}

.features-section h3 {
  color: white;
  font-size: 32px;
  text-align: center;
  margin-bottom: 40px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
}

.feature-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  padding: 30px 20px;
  text-align: center;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.feature-card h4 {
  color: white;
  font-size: 20px;
  margin: 20px 0 10px;
}

.feature-card p {
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.6;
}

.recent-projects h3 {
  color: white;
  font-size: 32px;
  text-align: center;
  margin-bottom: 40px;
}

.projects-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.project-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  padding: 20px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.project-info h4 {
  color: white;
  font-size: 18px;
  margin-bottom: 8px;
}

.project-info p {
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 8px;
}

.update-time {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
}

.project-actions {
  display: flex;
  gap: 10px;
}
</style>