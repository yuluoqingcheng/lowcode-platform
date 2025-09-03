<template>
  <div class="canvas-image">
    <img 
      v-if="src"
      :src="src"
      :alt="alt"
      :style="imageStyle"
      @load="handleLoad"
      @error="handleError"
    />
    <div 
      v-else 
      class="image-placeholder"
      :style="placeholderStyle"
    >
      <el-icon size="48" color="#ccc">
        <Picture />
      </el-icon>
      <span>图片</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps, defineEmits } from 'vue'
import { Picture } from '@element-plus/icons-vue'

interface Props {
  src?: string
  alt?: string
  fit?: 'fill' | 'contain' | 'cover' | 'none' | 'scale-down'
  borderRadius?: number
}

const props = withDefaults(defineProps<Props>(), {
  src: '',
  alt: '图片',
  fit: 'cover',
  borderRadius: 0
})

const emit = defineEmits<{
  load: [event: Event]
  error: [event: Event]
}>()

const imageStyle = computed(() => ({
  width: '100%',
  height: '100%',
  objectFit: props.fit,
  borderRadius: `${props.borderRadius}px`,
  display: 'block'
}))

const placeholderStyle = computed(() => ({
  width: '100%',
  height: '100%',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  backgroundColor: '#f5f7fa',
  border: '1px dashed #dcdfe6',
  borderRadius: `${props.borderRadius}px`,
  color: '#909399',
  fontSize: '14px'
}))

const handleLoad = (event: Event) => {
  emit('load', event)
}

const handleError = (event: Event) => {
  emit('error', event)
}
</script>

<style scoped>
.canvas-image {
  width: 100%;
  height: 100%;
}

.image-placeholder {
  user-select: none;
}
</style>