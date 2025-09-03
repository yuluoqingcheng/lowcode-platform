<template>
  <div class="projects-page">
    <div class="page-header">
      <h2>项目管理</h2>
      <el-button type="primary" @click="createProject">
        <el-icon><Plus /></el-icon>
        新建项目
      </el-button>
    </div>

    <div class="page-content">
      <!-- 项目列表 -->
      <div class="projects-grid">
        <div 
          v-for="project in projects" 
          :key="project.id"
          class="project-card"
          @click="openProject(project)"
        >
          <div class="project-thumbnail">
            <img v-if="project.thumbnail" :src="project.thumbnail" :alt="project.name" />
            <div v-else class="placeholder-thumbnail">
              <el-icon size="48"><Document /></el-icon>
            </div>
          </div>
          
          <div class="project-info">
            <h3>{{ project.name }}</h3>
            <p>{{ project.description }}</p>
            <div class="project-meta">
              <span class="update-time">{{ formatTime(project.updateTime) }}</span>
              <span class="component-count">{{ project.componentCount }} 个组件</span>
            </div>
          </div>
          
          <div class="project-actions" @click.stop>
            <el-dropdown @command="handleCommand">
              <el-button text>
                <el-icon><MoreFilled /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :command="{action: 'edit', project}">编辑</el-dropdown-item>
                  <el-dropdown-item :command="{action: 'duplicate', project}">复制</el-dropdown-item>
                  <el-dropdown-item :command="{action: 'export', project}">导出</el-dropdown-item>
                  <el-dropdown-item :command="{action: 'delete', project}" divided>删除</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="projects.length === 0" class="empty-state">
        <el-icon size="64"><FolderOpened /></el-icon>
        <h3>还没有项目</h3>
        <p>创建你的第一个低代码项目吧！</p>
        <el-button type="primary" @click="createProject">立即创建</el-button>
      </div>
    </div>

    <!-- 新建项目对话框 -->
    <el-dialog v-model="showCreateDialog" title="新建项目" width="500px">
      <el-form :model="newProject" label-width="80px">
        <el-form-item label="项目名称" required>
          <el-input v-model="newProject.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目描述">
          <el-input 
            v-model="newProject.description" 
            type="textarea" 
            :rows="3"
            placeholder="请输入项目描述"
          />
        </el-form-item>
        <el-form-item label="项目模板">
          <el-select v-model="newProject.template" placeholder="选择模板">
            <el-option label="空白项目" value="blank" />
            <el-option label="管理后台" value="admin" />
            <el-option label="营销页面" value="landing" />
            <el-option label="移动端应用" value="mobile" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmCreate">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Document, MoreFilled, FolderOpened } from '@element-plus/icons-vue'

const router = useRouter()

// 项目数据
const projects = ref([
  {
    id: 1,
    name: '电商管理系统',
    description: '基于Vue3的电商后台管理系统，包含商品管理、订单管理等功能',
    thumbnail: '',
    updateTime: new Date('2024-01-01 10:30:00'),
    componentCount: 25,
    template: 'admin'
  },
  {
    id: 2,
    name: '企业官网',
    description: '响应式企业官网，包含首页、产品展示、关于我们等页面',
    thumbnail: '',
    updateTime: new Date('2024-01-02 15:20:00'),
    componentCount: 18,
    template: 'landing'
  },
  {
    id: 3,
    name: '移动端商城',
    description: '移动端购物应用，支持商品浏览、购物车、支付等功能',
    thumbnail: '',
    updateTime: new Date('2024-01-03 09:15:00'),
    componentCount: 32,
    template: 'mobile'
  }
])

// 新建项目对话框
const showCreateDialog = ref(false)
const newProject = reactive({
  name: '',
  description: '',
  template: 'blank'
})

// 格式化时间
const formatTime = (time: Date) => {
  return time.toLocaleString('zh-CN')
}

// 打开项目
const openProject = (project: any) => {
  router.push('/editor')
  ElMessage.success(`打开项目：${project.name}`)
}

// 创建项目
const createProject = () => {
  showCreateDialog.value = true
  // 重置表单
  newProject.name = ''
  newProject.description = ''
  newProject.template = 'blank'
}

// 确认创建
const confirmCreate = () => {
  if (!newProject.name.trim()) {
    ElMessage.warning('请输入项目名称')
    return
  }

  const project = {
    id: Date.now(),
    name: newProject.name,
    description: newProject.description,
    thumbnail: '',
    updateTime: new Date(),
    componentCount: 0,
    template: newProject.template
  }

  projects.value.unshift(project)
  showCreateDialog.value = false
  ElMessage.success('项目创建成功！')
  
  // 自动打开新创建的项目
  openProject(project)
}

// 处理项目操作
const handleCommand = (command: any) => {
  const { action, project } = command
  
  switch (action) {
    case 'edit':
      openProject(project)
      break
    case 'duplicate':
      const duplicatedProject = {
        ...project,
        id: Date.now(),
        name: `${project.name}_副本`,
        updateTime: new Date()
      }
      projects.value.unshift(duplicatedProject)
      ElMessage.success('项目复制成功！')
      break
    case 'export':
      ElMessage.info('导出功能开发中...')
      break
    case 'delete':
      ElMessageBox.confirm(
        `确定要删除项目"${project.name}"吗？此操作不可恢复。`,
        '确认删除',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        const index = projects.value.findIndex(p => p.id === project.id)
        if (index > -1) {
          projects.value.splice(index, 1)
          ElMessage.success('项目删除成功！')
        }
      }).catch(() => {
        // 用户取消删除
      })
      break
  }
}
</script>

<style scoped>
.projects-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
}

.page-header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.page-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.projects-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
}

.project-card {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
}

.project-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.project-thumbnail {
  height: 160px;
  background: #f8f9fa;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #e4e7ed;
}

.project-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.placeholder-thumbnail {
  color: #c0c4cc;
}

.project-info {
  padding: 16px 20px;
}

.project-info h3 {
  margin: 0 0 8px 0;
  font-size: 16px;
  color: #303133;
  font-weight: 500;
}

.project-info p {
  margin: 0 0 12px 0;
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.project-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-time,
.component-count {
  font-size: 12px;
  color: #909399;
}

.project-actions {
  position: absolute;
  top: 12px;
  right: 12px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
  opacity: 0;
  transition: opacity 0.3s;
}

.project-card:hover .project-actions {
  opacity: 1;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 400px;
  color: #909399;
}

.empty-state h3 {
  margin: 16px 0 8px 0;
  color: #303133;
}

.empty-state p {
  margin: 0 0 24px 0;
  color: #606266;
}
</style>