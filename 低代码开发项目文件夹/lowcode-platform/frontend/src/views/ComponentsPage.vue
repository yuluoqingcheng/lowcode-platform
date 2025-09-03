<template>
  <div class="components-page">
    <div class="page-header">
      <h2>组件库</h2>
      <el-button type="primary">
        <el-icon><Plus /></el-icon>
        自定义组件
      </el-button>
    </div>

    <div class="page-content">
      <!-- 组件分类 -->
      <div class="component-categories">
        <div 
          v-for="category in categories" 
          :key="category.key"
          class="category-section"
        >
          <h3 class="category-title">{{ category.name }}</h3>
          <div class="components-grid">
            <div 
              v-for="component in category.components"
              :key="component.type"
              class="component-card"
            >
              <div class="component-preview">
                <el-icon size="32" :color="component.color">
                  <component :is="component.icon" />
                </el-icon>
              </div>
              <div class="component-info">
                <h4>{{ component.name }}</h4>
                <p>{{ component.description }}</p>
                <div class="component-meta">
                  <span class="usage-count">使用 {{ component.usageCount }} 次</span>
                </div>
              </div>
              <div class="component-actions">
                <el-button size="small" @click="previewComponent(component)">预览</el-button>
                <el-button size="small" type="primary" @click="useComponent(component)">使用</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 使用统计 -->
      <div class="usage-stats">
        <h3>组件使用统计</h3>
        <div class="stats-chart">
          <!-- 这里可以集成图表组件显示使用统计 -->
          <div class="chart-placeholder">
            <el-icon size="48" color="#ccc"><TrendCharts /></el-icon>
            <p>组件使用统计图表</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { 
  Plus, ElementPlus, Document, Picture, 
  Box, Grid, Edit, Select, Calendar,
  List, TrendCharts
} from '@element-plus/icons-vue'

const router = useRouter()

// 组件分类数据
const categories = ref([
  {
    key: 'basic',
    name: '基础组件',
    components: [
      {
        type: 'button',
        name: '按钮',
        description: '触发业务逻辑的按钮组件',
        icon: 'ElementPlus',
        color: '#409eff',
        usageCount: 25
      },
      {
        type: 'text',
        name: '文本',
        description: '用于显示文本内容',
        icon: 'Document',
        color: '#67c23a',
        usageCount: 18
      },
      {
        type: 'image',
        name: '图片',
        description: '图片展示组件',
        icon: 'Picture',
        color: '#e6a23c',
        usageCount: 12
      }
    ]
  },
  {
    key: 'layout',
    name: '布局组件',
    components: [
      {
        type: 'container',
        name: '容器',
        description: '用于包装其他组件的容器',
        icon: 'Box',
        color: '#f56c6c',
        usageCount: 15
      },
      {
        type: 'row',
        name: '行布局',
        description: '水平排列的布局组件',
        icon: 'Grid',
        color: '#909399',
        usageCount: 8
      }
    ]
  },
  {
    key: 'form',
    name: '表单组件',
    components: [
      {
        type: 'input',
        name: '输入框',
        description: '用户输入数据的表单控件',
        icon: 'Edit',
        color: '#409eff',
        usageCount: 22
      },
      {
        type: 'select',
        name: '选择器',
        description: '下拉选择表单控件',
        icon: 'Select',
        color: '#67c23a',
        usageCount: 10
      },
      {
        type: 'date-picker',
        name: '日期选择',
        description: '日期和时间选择器',
        icon: 'Calendar',
        color: '#e6a23c',
        usageCount: 6
      }
    ]
  },
  {
    key: 'display',
    name: '数据展示',
    components: [
      {
        type: 'table',
        name: '表格',
        description: '数据表格展示组件',
        icon: 'Grid',
        color: '#f56c6c',
        usageCount: 14
      },
      {
        type: 'list',
        name: '列表',
        description: '数据列表展示组件',
        icon: 'List',
        color: '#909399',
        usageCount: 9
      },
      {
        type: 'chart',
        name: '图表',
        description: '数据可视化图表',
        icon: 'TrendCharts',
        color: '#409eff',
        usageCount: 7
      }
    ]
  }
])

// 预览组件
const previewComponent = (component: any) => {
  ElMessage.info(`预览组件：${component.name}`)
}

// 使用组件
const useComponent = (component: any) => {
  router.push('/editor')
  ElMessage.success(`已切换到编辑器，可以使用${component.name}组件`)
}
</script>

<style scoped>
.components-page {
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

.component-categories {
  margin-bottom: 40px;
}

.category-section {
  margin-bottom: 32px;
}

.category-title {
  margin: 0 0 16px 0;
  color: #303133;
  font-size: 18px;
  font-weight: 500;
}

.components-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.component-card {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  padding: 20px;
  transition: all 0.3s;
  cursor: pointer;
}

.component-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.component-preview {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  margin-bottom: 16px;
}

.component-info h4 {
  margin: 0 0 8px 0;
  color: #303133;
  font-size: 16px;
}

.component-info p {
  margin: 0 0 12px 0;
  color: #606266;
  font-size: 14px;
  line-height: 1.5;
}

.component-meta {
  margin-bottom: 16px;
}

.usage-count {
  font-size: 12px;
  color: #909399;
}

.component-actions {
  display: flex;
  gap: 8px;
}

.usage-stats {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  padding: 24px;
}

.usage-stats h3 {
  margin: 0 0 20px 0;
  color: #303133;
  font-size: 18px;
}

.chart-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #909399;
}

.chart-placeholder p {
  margin: 12px 0 0 0;
  font-size: 14px;
}
</style>