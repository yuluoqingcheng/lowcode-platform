import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'
import { v4 as uuidv4 } from 'uuid'

// 组件配置接口
export interface ComponentConfig {
  id: string
  type: string
  name: string
  props: Record<string, any>
  style: Record<string, any>
  children?: ComponentConfig[]
  parentId?: string
  x: number
  y: number
  width: number
  height: number
}

// 画布配置接口
export interface CanvasConfig {
  width: number
  height: number
  backgroundColor: string
  backgroundImage?: string
  scale: number
  gridVisible: boolean
}

export const useComponentStore = defineStore('component', () => {
  // 组件列表
  const components = ref<ComponentConfig[]>([])
  
  // 选中的组件ID
  const selectedComponentId = ref<string | null>(null)
  
  // 画布配置
  const canvasConfig = reactive<CanvasConfig>({
    width: 1920,
    height: 1080,
    backgroundColor: '#ffffff',
    scale: 1,
    gridVisible: true
  })

  // 历史记录用于撤销重做
  const history = ref<ComponentConfig[][]>([])
  const historyIndex = ref(-1)

  // 添加组件到画布
  const addComponent = (componentData: any, x: number, y: number) => {
    const newComponent: ComponentConfig = {
      id: uuidv4(),
      type: componentData.type,
      name: componentData.name,
      props: getDefaultProps(componentData.type),
      style: getDefaultStyle(componentData.type),
      x,
      y,
      width: 120,
      height: 40
    }

    components.value.push(newComponent)
    selectComponent(newComponent.id)
    saveToHistory()
  }

  // 获取默认属性
  const getDefaultProps = (type: string) => {
    const defaultProps: Record<string, any> = {
      button: { text: '按钮', type: 'primary' },
      text: { content: '文本内容', fontSize: 14, color: '#333' },
      input: { placeholder: '请输入', value: '' },
      image: { src: '', alt: '图片' },
      container: { padding: 16 },
      card: { title: '卡片标题', shadow: 'always' }
    }
    return defaultProps[type] || {}
  }

  // 获取默认样式
  const getDefaultStyle = (type: string) => {
    const defaultStyles: Record<string, any> = {
      button: { 
        backgroundColor: '#409eff', 
        color: '#fff', 
        border: 'none', 
        borderRadius: '4px',
        padding: '8px 16px'
      },
      text: { 
        color: '#333', 
        fontSize: '14px',
        lineHeight: '1.5'
      },
      input: { 
        border: '1px solid #ddd', 
        borderRadius: '4px',
        padding: '8px 12px'
      },
      container: {
        border: '1px dashed #ddd',
        minHeight: '100px',
        backgroundColor: 'transparent'
      }
    }
    return defaultStyles[type] || {}
  }

  // 选中组件
  const selectComponent = (id: string | null) => {
    selectedComponentId.value = id
  }

  // 获取选中的组件
  const getSelectedComponent = () => {
    if (!selectedComponentId.value) return null
    return components.value.find(comp => comp.id === selectedComponentId.value) || null
  }

  // 更新组件属性
  const updateComponentProps = (id: string, props: Record<string, any>) => {
    const component = components.value.find(comp => comp.id === id)
    if (component) {
      component.props = { ...component.props, ...props }
      saveToHistory()
    }
  }

  // 更新组件样式
  const updateComponentStyle = (id: string, style: Record<string, any>) => {
    const component = components.value.find(comp => comp.id === id)
    if (component) {
      component.style = { ...component.style, ...style }
      saveToHistory()
    }
  }

  // 更新组件位置
  const updateComponentPosition = (id: string, x: number, y: number) => {
    const component = components.value.find(comp => comp.id === id)
    if (component) {
      component.x = x
      component.y = y
    }
  }

  // 更新组件尺寸
  const updateComponentSize = (id: string, width: number, height: number) => {
    const component = components.value.find(comp => comp.id === id)
    if (component) {
      component.width = width
      component.height = height
    }
  }

  // 删除组件
  const deleteComponent = (id: string) => {
    const index = components.value.findIndex(comp => comp.id === id)
    if (index > -1) {
      components.value.splice(index, 1)
      if (selectedComponentId.value === id) {
        selectedComponentId.value = null
      }
      saveToHistory()
    }
  }

  // 复制组件
  const copyComponent = (id: string) => {
    const component = components.value.find(comp => comp.id === id)
    if (component) {
      const newComponent: ComponentConfig = {
        ...component,
        id: uuidv4(),
        x: component.x + 20,
        y: component.y + 20
      }
      components.value.push(newComponent)
      selectComponent(newComponent.id)
      saveToHistory()
    }
  }

  // 保存历史记录
  const saveToHistory = () => {
    const currentState = JSON.parse(JSON.stringify(components.value))
    history.value = history.value.slice(0, historyIndex.value + 1)
    history.value.push(currentState)
    historyIndex.value++
    
    // 限制历史记录数量
    if (history.value.length > 50) {
      history.value.shift()
      historyIndex.value--
    }
  }

  // 撤销
  const undo = () => {
    if (historyIndex.value > 0) {
      historyIndex.value--
      components.value = JSON.parse(JSON.stringify(history.value[historyIndex.value]))
    }
  }

  // 重做
  const redo = () => {
    if (historyIndex.value < history.value.length - 1) {
      historyIndex.value++
      components.value = JSON.parse(JSON.stringify(history.value[historyIndex.value]))
    }
  }

  // 清空画布
  const clearCanvas = () => {
    components.value = []
    selectedComponentId.value = null
    saveToHistory()
  }

  // 更新画布配置
  const updateCanvasConfig = (config: Partial<CanvasConfig>) => {
    Object.assign(canvasConfig, config)
  }

  // 导出配置
  const exportConfig = () => {
    return {
      components: components.value,
      canvas: canvasConfig
    }
  }

  // 导入配置
  const importConfig = (config: any) => {
    components.value = config.components || []
    Object.assign(canvasConfig, config.canvas || {})
    selectedComponentId.value = null
    saveToHistory()
  }

  return {
    // 状态
    components,
    selectedComponentId,
    canvasConfig,
    
    // 方法
    addComponent,
    selectComponent,
    getSelectedComponent,
    updateComponentProps,
    updateComponentStyle,
    updateComponentPosition,
    updateComponentSize,
    deleteComponent,
    copyComponent,
    undo,
    redo,
    clearCanvas,
    updateCanvasConfig,
    exportConfig,
    importConfig
  }
})