<template>
  <div class="editor-page">
    <!-- 顶部工具栏 -->
    <div class="toolbar">
      <div class="toolbar-left">
        <el-button type="primary" :icon="Back" @click="goHome">返回首页</el-button>
        <el-button :icon="Document" @click="saveProject">保存项目</el-button>
        <el-button :icon="View" @click="previewProject">预览</el-button>
        <el-button :icon="Download" @click="generateCode">生成代码</el-button>
      </div>
      <div class="toolbar-center">
        <h2>低代码可视化编辑器</h2>
      </div>
      <div class="toolbar-right">
        <el-button :icon="Setting" @click="openSettings">设置</el-button>
      </div>
    </div>

    <!-- 主体内容区 -->
    <div class="editor-content">
      <!-- 左侧组件面板 -->
      <div class="left-panel">
        <ComponentPanel />
      </div>

      <!-- 中间画布区域 -->
      <div class="canvas-area">
        <DesignCanvas />
      </div>

      <!-- 右侧属性面板 -->
      <div class="right-panel">
        <PropertyPanel />
      </div>
    </div>

    <!-- 底部状态栏 -->
    <div class="status-bar">
      <span>组件数量: {{ componentCount }}</span>
      <span>画布尺寸: {{ canvasSize }}</span>
      <span>缩放比例: {{ zoomLevel }}%</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Back, Document, View, Download, Setting } from '@element-plus/icons-vue'
import ComponentPanel from '@/components/editor/ComponentPanel.vue'
import DesignCanvas from '@/components/editor/DesignCanvas.vue'
import PropertyPanel from '@/components/editor/PropertyPanel.vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

// 状态数据
const componentCount = ref(0)
const canvasSize = ref('1920x1080')
const zoomLevel = ref(100)

// 方法
const goHome = () => {
  router.push('/')
}

const saveProject = () => {
  ElMessage.success('项目保存成功！')
}

const previewProject = () => {
  ElMessage.info('预览功能开发中...')
}

const generateCode = () => {
  ElMessage.info('代码生成功能开发中...')
}

const openSettings = () => {
  ElMessage.info('设置功能开发中...')
}
</script>

<style scoped>
.editor-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #fff;
}

.toolbar {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #fff;
}

.toolbar-left {
  display: flex;
  gap: 10px;
}

.toolbar-center h2 {
  color: #303133;
  font-size: 18px;
}

.toolbar-right {
  display: flex;
  gap: 10px;
}

.editor-content {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.left-panel {
  width: 300px;
  border-right: 1px solid #e4e7ed;
  background: #fafafa;
}

.canvas-area {
  flex: 1;
  background: #f5f5f5;
  position: relative;
}

.right-panel {
  width: 300px;
  border-left: 1px solid #e4e7ed;
  background: #fafafa;
}

.status-bar {
  height: 30px;
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 0 20px;
  border-top: 1px solid #e4e7ed;
  background: #f8f9fa;
  font-size: 12px;
  color: #909399;
}
</style>