<template>
  <div class="component-panel">
    <div class="panel-header">
      <h3>组件库</h3>
      <el-input
        v-model="searchText"
        placeholder="搜索组件..."
        :prefix-icon="Search"
        size="small"
      />
    </div>

    <div class="panel-content">
      <!-- 基础组件 -->
      <div class="component-group">
        <div class="group-title" @click="toggleGroup('basic')">
          <el-icon><CaretRight :class="{ 'rotate': expandedGroups.basic }" /></el-icon>
          基础组件
        </div>
        <div class="group-content" v-show="expandedGroups.basic">
          <div
            class="component-item"
            v-for="component in filteredBasicComponents"
            :key="component.type"
            :draggable="true"
            @dragstart="handleDragStart($event, component)"
          >
            <el-icon :size="20">
              <component :is="component.icon" />
            </el-icon>
            <span>{{ component.name }}</span>
          </div>
        </div>
      </div>

      <!-- 布局组件 -->
      <div class="component-group">
        <div class="group-title" @click="toggleGroup('layout')">
          <el-icon><CaretRight :class="{ 'rotate': expandedGroups.layout }" /></el-icon>
          布局组件
        </div>
        <div class="group-content" v-show="expandedGroups.layout">
          <div
            class="component-item"
            v-for="component in filteredLayoutComponents"
            :key="component.type"
            :draggable="true"
            @dragstart="handleDragStart($event, component)"
          >
            <el-icon :size="20">
              <component :is="component.icon" />
            </el-icon>
            <span>{{ component.name }}</span>
          </div>
        </div>
      </div>

      <!-- 表单组件 -->
      <div class="component-group">
        <div class="group-title" @click="toggleGroup('form')">
          <el-icon><CaretRight :class="{ 'rotate': expandedGroups.form }" /></el-icon>
          表单组件
        </div>
        <div class="group-content" v-show="expandedGroups.form">
          <div
            class="component-item"
            v-for="component in filteredFormComponents"
            :key="component.type"
            :draggable="true"
            @dragstart="handleDragStart($event, component)"
          >
            <el-icon :size="20">
              <component :is="component.icon" />
            </el-icon>
            <span>{{ component.name }}</span>
          </div>
        </div>
      </div>

      <!-- 数据展示组件 -->
      <div class="component-group">
        <div class="group-title" @click="toggleGroup('display')">
          <el-icon><CaretRight :class="{ 'rotate': expandedGroups.display }" /></el-icon>
          数据展示
        </div>
        <div class="group-content" v-show="expandedGroups.display">
          <div
            class="component-item"
            v-for="component in filteredDisplayComponents"
            :key="component.type"
            :draggable="true"
            @dragstart="handleDragStart($event, component)"
          >
            <el-icon :size="20">
              <component :is="component.icon" />
            </el-icon>
            <span>{{ component.name }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { Search, CaretRight } from '@element-plus/icons-vue'
import { useComponentStore } from '@/stores/component'

const componentStore = useComponentStore()
const searchText = ref('')

// 分组展开状态
const expandedGroups = reactive({
  basic: true,
  layout: true,
  form: true,
  display: true
})

// 基础组件
const basicComponents = [
  { type: 'button', name: '按钮', icon: 'ElementPlus', category: 'basic' },
  { type: 'text', name: '文本', icon: 'Document', category: 'basic' },
  { type: 'image', name: '图片', icon: 'Picture', category: 'basic' },
  { type: 'link', name: '链接', icon: 'Link', category: 'basic' },
  { type: 'icon', name: '图标', icon: 'Star', category: 'basic' }
]

// 布局组件
const layoutComponents = [
  { type: 'container', name: '容器', icon: 'Box', category: 'layout' },
  { type: 'row', name: '行布局', icon: 'Grid', category: 'layout' },
  { type: 'col', name: '列布局', icon: 'Menu', category: 'layout' },
  { type: 'card', name: '卡片', icon: 'Postcard', category: 'layout' },
  { type: 'divider', name: '分割线', icon: 'Minus', category: 'layout' }
]

// 表单组件
const formComponents = [
  { type: 'input', name: '输入框', icon: 'Edit', category: 'form' },
  { type: 'select', name: '选择器', icon: 'Select', category: 'form' },
  { type: 'radio', name: '单选框', icon: 'Select', category: 'form' },
  { type: 'checkbox', name: '多选框', icon: 'Check', category: 'form' },
  { type: 'date-picker', name: '日期选择', icon: 'Calendar', category: 'form' },
  { type: 'switch', name: '开关', icon: 'Switch', category: 'form' }
]

// 数据展示组件
const displayComponents = [
  { type: 'table', name: '表格', icon: 'Grid', category: 'display' },
  { type: 'list', name: '列表', icon: 'List', category: 'display' },
  { type: 'tree', name: '树形控件', icon: 'Share', category: 'display' },
  { type: 'chart', name: '图表', icon: 'TrendCharts', category: 'display' },
  { type: 'progress', name: '进度条', icon: 'Loading', category: 'display' }
]

// 过滤组件
const filteredBasicComponents = computed(() => 
  basicComponents.filter(comp => 
    comp.name.toLowerCase().includes(searchText.value.toLowerCase())
  )
)

const filteredLayoutComponents = computed(() => 
  layoutComponents.filter(comp => 
    comp.name.toLowerCase().includes(searchText.value.toLowerCase())
  )
)

const filteredFormComponents = computed(() => 
  formComponents.filter(comp => 
    comp.name.toLowerCase().includes(searchText.value.toLowerCase())
  )
)

const filteredDisplayComponents = computed(() => 
  displayComponents.filter(comp => 
    comp.name.toLowerCase().includes(searchText.value.toLowerCase())
  )
)

// 切换分组展开状态
const toggleGroup = (group: string) => {
  expandedGroups[group] = !expandedGroups[group]
}

// 处理拖拽开始
const handleDragStart = (event: DragEvent, component: any) => {
  if (event.dataTransfer) {
    event.dataTransfer.setData('application/json', JSON.stringify(component))
    event.dataTransfer.effectAllowed = 'copy'
  }
}
</script>

<style scoped>
.component-panel {
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #fafafa;
}

.panel-header {
  padding: 16px;
  border-bottom: 1px solid #e4e7ed;
  background: #fff;
}

.panel-header h3 {
  margin: 0 0 12px 0;
  color: #303133;
  font-size: 16px;
}

.panel-content {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.component-group {
  margin-bottom: 8px;
}

.group-title {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  background: #f0f2f5;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  color: #606266;
  transition: background 0.2s;
}

.group-title:hover {
  background: #e8ebf0;
}

.group-title .el-icon {
  transition: transform 0.2s;
}

.group-title .rotate {
  transform: rotate(90deg);
}

.group-content {
  padding: 8px 0;
}

.component-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  margin: 4px 0;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  cursor: grab;
  transition: all 0.2s;
  user-select: none;
}

.component-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.2);
  transform: translateY(-1px);
}

.component-item:active {
  cursor: grabbing;
  transform: translateY(0);
}

.component-item span {
  font-size: 14px;
  color: #606266;
}
</style>