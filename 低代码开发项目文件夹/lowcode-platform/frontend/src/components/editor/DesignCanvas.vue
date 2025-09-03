<template>
  <div class="design-canvas" ref="canvasRef">
    <!-- 画布工具栏 -->
    <div class="canvas-toolbar">
      <div class="toolbar-left">
        <el-button-group size="small">
          <el-button :icon="Back" @click="undo" :disabled="!canUndo">撤销</el-button>
          <el-button :icon="Right" @click="redo" :disabled="!canRedo">重做</el-button>
        </el-button-group>
        <el-divider direction="vertical" />
        <el-button-group size="small">
          <el-button :icon="CopyDocument" @click="copySelected">复制</el-button>
          <el-button :icon="Delete" @click="deleteSelected" :disabled="!selectedComponent">删除</el-button>
        </el-button-group>
        <el-divider direction="vertical" />
        <el-button size="small" :icon="Refresh" @click="clearCanvas">清空</el-button>
      </div>
      
      <div class="toolbar-right">
        <span class="zoom-info">{{ Math.round(canvasConfig.scale * 100) }}%</span>
        <el-slider
          v-model="scaleValue"
          :min="25"
          :max="200"
          :step="25"
          size="small"
          style="width: 100px; margin: 0 10px;"
          @change="handleScaleChange"
        />
        <el-button-group size="small">
          <el-button :icon="ZoomOut" @click="zoomOut">缩小</el-button>
          <el-button @click="resetZoom">100%</el-button>
          <el-button :icon="ZoomIn" @click="zoomIn">放大</el-button>
        </el-button-group>
      </div>
    </div>

    <!-- 画布容器 -->
    <div class="canvas-container" @click="handleCanvasClick">
      <!-- 网格背景 -->
      <div class="grid-background" v-if="canvasConfig.gridVisible"></div>
      
      <!-- 画布内容区 -->
      <div 
        class="canvas-content"
        :style="canvasStyle"
        @drop="handleDrop"
        @dragover="handleDragOver"
      >
        <!-- 渲染组件 -->
        <div
          v-for="component in components"
          :key="component.id"
          class="canvas-component"
          :class="{ 'selected': selectedComponentId === component.id }"
          :style="getComponentStyle(component)"
          @click.stop="selectComponent(component.id)"
          @mousedown="handleMouseDown($event, component)"
        >
          <!-- 组件内容 -->
          <component 
            :is="getComponentType(component.type)"
            v-bind="component.props"
            :style="component.style"
          />
          
          <!-- 选中状态的控制点 -->
          <div v-if="selectedComponentId === component.id" class="selection-handles">
            <div class="handle handle-nw" @mousedown.stop="handleResizeStart($event, 'nw', component)"></div>
            <div class="handle handle-ne" @mousedown.stop="handleResizeStart($event, 'ne', component)"></div>
            <div class="handle handle-sw" @mousedown.stop="handleResizeStart($event, 'sw', component)"></div>
            <div class="handle handle-se" @mousedown.stop="handleResizeStart($event, 'se', component)"></div>
            <div class="handle handle-n" @mousedown.stop="handleResizeStart($event, 'n', component)"></div>
            <div class="handle handle-s" @mousedown.stop="handleResizeStart($event, 's', component)"></div>
            <div class="handle handle-w" @mousedown.stop="handleResizeStart($event, 'w', component)"></div>
            <div class="handle handle-e" @mousedown.stop="handleResizeStart($event, 'e', component)"></div>
          </div>
        </div>
      </div>

      <!-- 选择框 -->
      <div 
        v-if="selectionBox.visible"
        class="selection-box"
        :style="selectionBoxStyle"
      ></div>
    </div>

    <!-- 右键菜单 -->
    <el-dropdown 
      ref="contextMenuRef"
      :style="contextMenuStyle"
      @command="handleContextMenu"
      trigger="contextmenu"
    >
      <span></span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="copy" :disabled="!selectedComponent">复制</el-dropdown-item>
          <el-dropdown-item command="delete" :disabled="!selectedComponent">删除</el-dropdown-item>
          <el-dropdown-item divided command="bring-forward" :disabled="!selectedComponent">向前一层</el-dropdown-item>
          <el-dropdown-item command="send-backward" :disabled="!selectedComponent">向后一层</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, reactive, nextTick } from 'vue'
import { useComponentStore, type ComponentConfig } from '@/stores/component'
import { 
  Back, Right, CopyDocument, Delete, Refresh, 
  ZoomIn, ZoomOut
} from '@element-plus/icons-vue'
import CanvasButton from '@/components/canvas/CanvasButton.vue'
import CanvasText from '@/components/canvas/CanvasText.vue'
import CanvasInput from '@/components/canvas/CanvasInput.vue'
import CanvasImage from '@/components/canvas/CanvasImage.vue'
import CanvasContainer from '@/components/canvas/CanvasContainer.vue'

const componentStore = useComponentStore()
const canvasRef = ref<HTMLElement>()

// 从store获取状态
const { 
  components, 
  selectedComponentId, 
  canvasConfig,
  selectComponent,
  addComponent,
  updateComponentPosition,
  updateComponentSize,
  deleteComponent,
  copyComponent,
  undo,
  redo,
  clearCanvas
} = componentStore

// 计算属性
const selectedComponent = computed(() => componentStore.getSelectedComponent())
const canUndo = computed(() => true) // 这里需要从store获取实际状态
const canRedo = computed(() => true) // 这里需要从store获取实际状态

// 缩放相关
const scaleValue = ref(100)

const canvasStyle = computed(() => ({
  width: `${canvasConfig.width}px`,
  height: `${canvasConfig.height}px`,
  transform: `scale(${canvasConfig.scale})`,
  transformOrigin: 'top left',
  backgroundColor: canvasConfig.backgroundColor
}))

// 拖拽状态
const dragState = reactive({
  isDragging: false,
  startX: 0,
  startY: 0,
  component: null as ComponentConfig | null
})

// 调整大小状态
const resizeState = reactive({
  isResizing: false,
  direction: '',
  startX: 0,
  startY: 0,
  component: null as ComponentConfig | null,
  startWidth: 0,
  startHeight: 0
})

// 选择框状态
const selectionBox = reactive({
  visible: false,
  startX: 0,
  startY: 0,
  endX: 0,
  endY: 0
})

const selectionBoxStyle = computed(() => ({
  left: `${Math.min(selectionBox.startX, selectionBox.endX)}px`,
  top: `${Math.min(selectionBox.startY, selectionBox.endY)}px`,
  width: `${Math.abs(selectionBox.endX - selectionBox.startX)}px`,
  height: `${Math.abs(selectionBox.endY - selectionBox.startY)}px`
}))

// 右键菜单
const contextMenuStyle = ref({ display: 'none' })

// 获取组件样式
const getComponentStyle = (component: ComponentConfig) => ({
  position: 'absolute',
  left: `${component.x}px`,
  top: `${component.y}px`,
  width: `${component.width}px`,
  height: `${component.height}px`,
  zIndex: components.indexOf(component) + 1
})

// 获取组件类型
const getComponentType = (type: string) => {
  const typeMap: Record<string, any> = {
    button: CanvasButton,
    text: CanvasText,
    input: CanvasInput,
    image: CanvasImage,
    container: CanvasContainer
  }
  return typeMap[type] || 'div'
}

// 处理拖放
const handleDrop = (event: DragEvent) => {
  event.preventDefault()
  
  const componentData = JSON.parse(event.dataTransfer?.getData('application/json') || '{}')
  if (componentData.type) {
    const rect = canvasRef.value?.getBoundingClientRect()
    if (rect) {
      const x = (event.clientX - rect.left) / canvasConfig.scale
      const y = (event.clientY - rect.top - 80) / canvasConfig.scale // 减去工具栏高度
      addComponent(componentData, x, y)
    }
  }
}

const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}

// 处理画布点击
const handleCanvasClick = (event: MouseEvent) => {
  selectComponent(null)
}

// 处理鼠标按下（开始拖拽）
const handleMouseDown = (event: MouseEvent, component: ComponentConfig) => {
  event.preventDefault()
  event.stopPropagation()
  
  selectComponent(component.id)
  
  dragState.isDragging = true
  dragState.startX = event.clientX
  dragState.startY = event.clientY
  dragState.component = component
  
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
}

const handleMouseMove = (event: MouseEvent) => {
  if (dragState.isDragging && dragState.component) {
    const deltaX = (event.clientX - dragState.startX) / canvasConfig.scale
    const deltaY = (event.clientY - dragState.startY) / canvasConfig.scale
    
    const newX = dragState.component.x + deltaX
    const newY = dragState.component.y + deltaY
    
    updateComponentPosition(dragState.component.id, newX, newY)
    
    dragState.startX = event.clientX
    dragState.startY = event.clientY
  }
  
  if (resizeState.isResizing && resizeState.component) {
    handleResize(event)
  }
}

const handleMouseUp = () => {
  dragState.isDragging = false
  dragState.component = null
  resizeState.isResizing = false
  resizeState.component = null
  
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
}

// 处理调整大小
const handleResizeStart = (event: MouseEvent, direction: string, component: ComponentConfig) => {
  event.preventDefault()
  event.stopPropagation()
  
  resizeState.isResizing = true
  resizeState.direction = direction
  resizeState.startX = event.clientX
  resizeState.startY = event.clientY
  resizeState.component = component
  resizeState.startWidth = component.width
  resizeState.startHeight = component.height
  
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
}

const handleResize = (event: MouseEvent) => {
  if (!resizeState.component) return
  
  const deltaX = (event.clientX - resizeState.startX) / canvasConfig.scale
  const deltaY = (event.clientY - resizeState.startY) / canvasConfig.scale
  
  let newWidth = resizeState.startWidth
  let newHeight = resizeState.startHeight
  let newX = resizeState.component.x
  let newY = resizeState.component.y
  
  switch (resizeState.direction) {
    case 'se': // 右下
      newWidth = Math.max(20, resizeState.startWidth + deltaX)
      newHeight = Math.max(20, resizeState.startHeight + deltaY)
      break
    case 'sw': // 左下
      newWidth = Math.max(20, resizeState.startWidth - deltaX)
      newHeight = Math.max(20, resizeState.startHeight + deltaY)
      newX = resizeState.component.x + deltaX
      break
    case 'ne': // 右上
      newWidth = Math.max(20, resizeState.startWidth + deltaX)
      newHeight = Math.max(20, resizeState.startHeight - deltaY)
      newY = resizeState.component.y + deltaY
      break
    case 'nw': // 左上
      newWidth = Math.max(20, resizeState.startWidth - deltaX)
      newHeight = Math.max(20, resizeState.startHeight - deltaY)
      newX = resizeState.component.x + deltaX
      newY = resizeState.component.y + deltaY
      break
    case 'e': // 右
      newWidth = Math.max(20, resizeState.startWidth + deltaX)
      break
    case 'w': // 左
      newWidth = Math.max(20, resizeState.startWidth - deltaX)
      newX = resizeState.component.x + deltaX
      break
    case 's': // 下
      newHeight = Math.max(20, resizeState.startHeight + deltaY)
      break
    case 'n': // 上
      newHeight = Math.max(20, resizeState.startHeight - deltaY)
      newY = resizeState.component.y + deltaY
      break
  }
  
  updateComponentSize(resizeState.component.id, newWidth, newHeight)
  if (newX !== resizeState.component.x || newY !== resizeState.component.y) {
    updateComponentPosition(resizeState.component.id, newX, newY)
  }
}

// 缩放控制
const handleScaleChange = (value: number) => {
  canvasConfig.scale = value / 100
}

const zoomIn = () => {
  scaleValue.value = Math.min(200, scaleValue.value + 25)
  handleScaleChange(scaleValue.value)
}

const zoomOut = () => {
  scaleValue.value = Math.max(25, scaleValue.value - 25)
  handleScaleChange(scaleValue.value)
}

const resetZoom = () => {
  scaleValue.value = 100
  handleScaleChange(100)
}

// 删除选中组件
const deleteSelected = () => {
  if (selectedComponent.value) {
    deleteComponent(selectedComponent.value.id)
  }
}

// 复制选中组件
const copySelected = () => {
  if (selectedComponent.value) {
    copyComponent(selectedComponent.value.id)
  }
}

// 右键菜单处理
const handleContextMenu = (command: string) => {
  switch (command) {
    case 'copy':
      copySelected()
      break
    case 'delete':
      deleteSelected()
      break
    case 'bring-forward':
      // 实现向前一层逻辑
      break
    case 'send-backward':
      // 实现向后一层逻辑
      break
  }
}
</script>

<style scoped>
.design-canvas {
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
}

.canvas-toolbar {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.toolbar-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.toolbar-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.zoom-info {
  min-width: 40px;
  text-align: center;
  font-size: 12px;
  color: #606266;
}

.canvas-container {
  flex: 1;
  position: relative;
  overflow: auto;
  background: #f0f0f0;
}

.grid-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: 
    linear-gradient(to right, #e0e0e0 1px, transparent 1px),
    linear-gradient(to bottom, #e0e0e0 1px, transparent 1px);
  background-size: 20px 20px;
  pointer-events: none;
}

.canvas-content {
  position: relative;
  margin: 50px;
  border: 1px solid #ddd;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.canvas-component {
  border: 1px solid transparent;
  cursor: move;
  transition: border-color 0.2s;
}

.canvas-component:hover {
  border-color: #409eff;
}

.canvas-component.selected {
  border-color: #409eff !important;
  border-width: 2px;
}

.selection-handles {
  position: absolute;
  top: -4px;
  left: -4px;
  right: -4px;
  bottom: -4px;
  pointer-events: none;
}

.handle {
  position: absolute;
  width: 8px;
  height: 8px;
  background: #409eff;
  border: 1px solid #fff;
  border-radius: 2px;
  pointer-events: all;
  cursor: pointer;
}

.handle-nw { top: -4px; left: -4px; cursor: nw-resize; }
.handle-ne { top: -4px; right: -4px; cursor: ne-resize; }
.handle-sw { bottom: -4px; left: -4px; cursor: sw-resize; }
.handle-se { bottom: -4px; right: -4px; cursor: se-resize; }
.handle-n { top: -4px; left: 50%; transform: translateX(-50%); cursor: n-resize; }
.handle-s { bottom: -4px; left: 50%; transform: translateX(-50%); cursor: s-resize; }
.handle-w { top: 50%; left: -4px; transform: translateY(-50%); cursor: w-resize; }
.handle-e { top: 50%; right: -4px; transform: translateY(-50%); cursor: e-resize; }

.selection-box {
  position: absolute;
  border: 1px dashed #409eff;
  background: rgba(64, 158, 255, 0.1);
  pointer-events: none;
}
</style>