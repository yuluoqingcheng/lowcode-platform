<template>
  <div 
    class="canvas-text"
    :style="textStyle"
  >
    {{ content }}
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'

interface Props {
  content?: string
  fontSize?: number
  color?: string
  fontWeight?: string | number
  textAlign?: 'left' | 'center' | 'right'
  lineHeight?: string | number
  fontFamily?: string
}

const props = withDefaults(defineProps<Props>(), {
  content: '文本内容',
  fontSize: 14,
  color: '#333333',
  fontWeight: 'normal',
  textAlign: 'left',
  lineHeight: 1.5,
  fontFamily: 'inherit'
})

const textStyle = computed(() => ({
  fontSize: `${props.fontSize}px`,
  color: props.color,
  fontWeight: props.fontWeight,
  textAlign: props.textAlign,
  lineHeight: props.lineHeight,
  fontFamily: props.fontFamily,
  width: '100%',
  height: '100%',
  display: 'flex',
  alignItems: 'center',
  padding: '4px 8px',
  wordBreak: 'break-word'
}))
</script>

<style scoped>
.canvas-text {
  user-select: none;
  cursor: text;
}
</style>