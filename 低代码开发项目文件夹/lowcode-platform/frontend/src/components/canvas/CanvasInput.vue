<template>
  <el-input
    v-model="inputValue"
    :placeholder="placeholder"
    :type="inputType"
    :disabled="disabled"
    :readonly="readonly"
    :clearable="clearable"
    :show-password="showPassword"
    :size="size"
    @input="handleInput"
    @change="handleChange"
    @focus="handleFocus"
    @blur="handleBlur"
  />
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'

interface Props {
  value?: string
  placeholder?: string
  inputType?: 'text' | 'password' | 'email' | 'number' | 'tel' | 'url'
  disabled?: boolean
  readonly?: boolean
  clearable?: boolean
  showPassword?: boolean
  size?: 'large' | 'default' | 'small'
  maxlength?: number
}

const props = withDefaults(defineProps<Props>(), {
  value: '',
  placeholder: '请输入',
  inputType: 'text',
  disabled: false,
  readonly: false,
  clearable: false,
  showPassword: false,
  size: 'default'
})

const emit = defineEmits<{
  input: [value: string]
  change: [value: string]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
}>()

const inputValue = ref(props.value)

watch(() => props.value, (newValue) => {
  inputValue.value = newValue
})

const handleInput = (value: string) => {
  emit('input', value)
}

const handleChange = (value: string) => {
  emit('change', value)
}

const handleFocus = (event: FocusEvent) => {
  emit('focus', event)
}

const handleBlur = (event: FocusEvent) => {
  emit('blur', event)
}
</script>