<template>
  <div 
    class="canvas-container"
    :style="containerStyle"
    @drop="handleDrop"
    @dragover="handleDragOver"
  >
    <slot>
      <div class="container-placeholder">
        <el-icon size="24" color="#ccc">
          <Box />
        </el-icon>
        <span>拖拽组件到此处</span>
      </div>
    </slot>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import { Box } from '@element-plus/icons-vue'

interface Props {
  padding?: number
  backgroundColor?: string
  borderColor?: string
  borderWidth?: number
  borderStyle?: 'solid' | 'dashed' | 'dotted'
  borderRadius?: number
  minHeight?: number
}

const props = withDefaults(defineProps<Props>(), {
  padding: 16,
  backgroundColor: 'transparent',
  borderColor: '#dcdfe6',
  borderWidth: 1,
  borderStyle: 'dashed',
  borderRadius: 4,
  minHeight: 100
})

const containerStyle = computed(() => ({
  padding: `${props.padding}px`,
  backgroundColor: props.backgroundColor,
  border: `${props.borderWidth}px ${props.borderStyle} ${props.borderColor}`,
  borderRadius: `${props.borderRadius}px`,
  minHeight: `${props.minHeight}px`,
  width: '100%',
  height: '100%',
  position: 'relative'
}))

const handleDrop = (event: DragEvent) => {
  event.preventDefault()
  // 容器接收拖拽的处理逻辑
}

const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}
</script>

<style scoped>
.canvas-container {
  box-sizing: border-box;
}

.container-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #909399;
  font-size: 14px;
  user-select: none;
  gap: 8px;
}
</style>