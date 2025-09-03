<template>
  <div class="property-panel">
    <div class="panel-header">
      <h3>属性面板</h3>
    </div>

    <div class="panel-content" v-if="selectedComponent">
      <!-- 基本信息 -->
      <div class="property-group">
        <div class="group-title">基本信息</div>
        <div class="property-item">
          <label>组件名称</label>
          <el-input
            v-model="selectedComponent.name"
            size="small"
            @blur="updateComponent"
          />
        </div>
        <div class="property-item">
          <label>组件类型</label>
          <el-input
            :value="selectedComponent.type"
            size="small"
            disabled
          />
        </div>
      </div>

      <!-- 位置和尺寸 -->
      <div class="property-group">
        <div class="group-title">位置和尺寸</div>
        <div class="property-row">
          <div class="property-item">
            <label>X坐标</label>
            <el-input-number
              v-model="selectedComponent.x"
              size="small"
              :step="1"
              @change="updatePosition"
            />
          </div>
          <div class="property-item">
            <label>Y坐标</label>
            <el-input-number
              v-model="selectedComponent.y"
              size="small"
              :step="1"
              @change="updatePosition"
            />
          </div>
        </div>
        <div class="property-row">
          <div class="property-item">
            <label>宽度</label>
            <el-input-number
              v-model="selectedComponent.width"
              size="small"
              :min="20"
              :step="1"
              @change="updateSize"
            />
          </div>
          <div class="property-item">
            <label>高度</label>
            <el-input-number
              v-model="selectedComponent.height"
              size="small"
              :min="20"
              :step="1"
              @change="updateSize"
            />
          </div>
        </div>
      </div>

      <!-- 组件属性 -->
      <div class="property-group">
        <div class="group-title">组件属性</div>
        
        <!-- 按钮属性 -->
        <template v-if="selectedComponent.type === 'button'">
          <div class="property-item">
            <label>按钮文本</label>
            <el-input
              v-model="selectedComponent.props.text"
              size="small"
              @blur="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>按钮类型</label>
            <el-select
              v-model="selectedComponent.props.type"
              size="small"
              @change="updateComponent"
            >
              <el-option label="主要按钮" value="primary" />
              <el-option label="成功按钮" value="success" />
              <el-option label="警告按钮" value="warning" />
              <el-option label="危险按钮" value="danger" />
              <el-option label="信息按钮" value="info" />
              <el-option label="文本按钮" value="text" />
            </el-select>
          </div>
          <div class="property-item">
            <label>按钮尺寸</label>
            <el-select
              v-model="selectedComponent.props.size"
              size="small"
              @change="updateComponent"
            >
              <el-option label="大" value="large" />
              <el-option label="默认" value="default" />
              <el-option label="小" value="small" />
            </el-select>
          </div>
          <div class="property-item">
            <label>禁用状态</label>
            <el-switch
              v-model="selectedComponent.props.disabled"
              @change="updateComponent"
            />
          </div>
        </template>

        <!-- 文本属性 -->
        <template v-if="selectedComponent.type === 'text'">
          <div class="property-item">
            <label>文本内容</label>
            <el-input
              v-model="selectedComponent.props.content"
              type="textarea"
              :rows="3"
              size="small"
              @blur="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>字体大小</label>
            <el-input-number
              v-model="selectedComponent.props.fontSize"
              size="small"
              :min="12"
              :max="72"
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>文字颜色</label>
            <el-color-picker
              v-model="selectedComponent.props.color"
              size="small"
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>文字对齐</label>
            <el-select
              v-model="selectedComponent.props.textAlign"
              size="small"
              @change="updateComponent"
            >
              <el-option label="左对齐" value="left" />
              <el-option label="居中" value="center" />
              <el-option label="右对齐" value="right" />
            </el-select>
          </div>
          <div class="property-item">
            <label>字体粗细</label>
            <el-select
              v-model="selectedComponent.props.fontWeight"
              size="small"
              @change="updateComponent"
            >
              <el-option label="正常" value="normal" />
              <el-option label="粗体" value="bold" />
              <el-option label="100" value="100" />
              <el-option label="300" value="300" />
              <el-option label="500" value="500" />
              <el-option label="700" value="700" />
              <el-option label="900" value="900" />
            </el-select>
          </div>
        </template>

        <!-- 输入框属性 -->
        <template v-if="selectedComponent.type === 'input'">
          <div class="property-item">
            <label>占位符</label>
            <el-input
              v-model="selectedComponent.props.placeholder"
              size="small"
              @blur="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>输入类型</label>
            <el-select
              v-model="selectedComponent.props.inputType"
              size="small"
              @change="updateComponent"
            >
              <el-option label="文本" value="text" />
              <el-option label="密码" value="password" />
              <el-option label="邮箱" value="email" />
              <el-option label="数字" value="number" />
              <el-option label="电话" value="tel" />
              <el-option label="网址" value="url" />
            </el-select>
          </div>
          <div class="property-item">
            <label>可清空</label>
            <el-switch
              v-model="selectedComponent.props.clearable"
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>禁用状态</label>
            <el-switch
              v-model="selectedComponent.props.disabled"
              @change="updateComponent"
            />
          </div>
        </template>

        <!-- 图片属性 -->
        <template v-if="selectedComponent.type === 'image'">
          <div class="property-item">
            <label>图片地址</label>
            <el-input
              v-model="selectedComponent.props.src"
              size="small"
              placeholder="请输入图片URL"
              @blur="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>替代文本</label>
            <el-input
              v-model="selectedComponent.props.alt"
              size="small"
              @blur="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>适应方式</label>
            <el-select
              v-model="selectedComponent.props.fit"
              size="small"
              @change="updateComponent"
            >
              <el-option label="填充" value="fill" />
              <el-option label="包含" value="contain" />
              <el-option label="覆盖" value="cover" />
              <el-option label="原始" value="none" />
              <el-option label="缩放" value="scale-down" />
            </el-select>
          </div>
          <div class="property-item">
            <label>圆角</label>
            <el-input-number
              v-model="selectedComponent.props.borderRadius"
              size="small"
              :min="0"
              @change="updateComponent"
            />
          </div>
        </template>

        <!-- 容器属性 -->
        <template v-if="selectedComponent.type === 'container'">
          <div class="property-item">
            <label>内边距</label>
            <el-input-number
              v-model="selectedComponent.props.padding"
              size="small"
              :min="0"
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>背景颜色</label>
            <el-color-picker
              v-model="selectedComponent.props.backgroundColor"
              size="small"
              show-alpha
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>边框颜色</label>
            <el-color-picker
              v-model="selectedComponent.props.borderColor"
              size="small"
              @change="updateComponent"
            />
          </div>
          <div class="property-item">
            <label>边框样式</label>
            <el-select
              v-model="selectedComponent.props.borderStyle"
              size="small"
              @change="updateComponent"
            >
              <el-option label="实线" value="solid" />
              <el-option label="虚线" value="dashed" />
              <el-option label="点线" value="dotted" />
            </el-select>
          </div>
          <div class="property-item">
            <label>边框粗细</label>
            <el-input-number
              v-model="selectedComponent.props.borderWidth"
              size="small"
              :min="0"
              @change="updateComponent"
            />
          </div>
        </template>
      </div>

      <!-- 样式设置 -->
      <div class="property-group">
        <div class="group-title">样式设置</div>
        <div class="property-item">
          <label>背景颜色</label>
          <el-color-picker
            v-model="selectedComponent.style.backgroundColor"
            size="small"
            show-alpha
            @change="updateStyle"
          />
        </div>
        <div class="property-item">
          <label>边框</label>
          <el-input
            v-model="selectedComponent.style.border"
            size="small"
            placeholder="1px solid #ddd"
            @blur="updateStyle"
          />
        </div>
        <div class="property-item">
          <label>圆角</label>
          <el-input
            v-model="selectedComponent.style.borderRadius"
            size="small"
            placeholder="4px"
            @blur="updateStyle"
          />
        </div>
        <div class="property-item">
          <label>阴影</label>
          <el-input
            v-model="selectedComponent.style.boxShadow"
            size="small"
            placeholder="0 2px 4px rgba(0,0,0,0.1)"
            @blur="updateStyle"
          />
        </div>
      </div>
    </div>

    <!-- 未选中组件时的提示 -->
    <div class="panel-content" v-else>
      <div class="empty-state">
        <el-icon size="48" color="#ccc">
          <Select />
        </el-icon>
        <p>请选择一个组件来编辑属性</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useComponentStore } from '@/stores/component'
import { Select } from '@element-plus/icons-vue'

const componentStore = useComponentStore()

const selectedComponent = computed(() => componentStore.getSelectedComponent())

// 更新组件属性
const updateComponent = () => {
  if (selectedComponent.value) {
    componentStore.updateComponentProps(selectedComponent.value.id, selectedComponent.value.props)
  }
}

// 更新组件样式
const updateStyle = () => {
  if (selectedComponent.value) {
    componentStore.updateComponentStyle(selectedComponent.value.id, selectedComponent.value.style)
  }
}

// 更新组件位置
const updatePosition = () => {
  if (selectedComponent.value) {
    componentStore.updateComponentPosition(
      selectedComponent.value.id,
      selectedComponent.value.x,
      selectedComponent.value.y
    )
  }
}

// 更新组件尺寸
const updateSize = () => {
  if (selectedComponent.value) {
    componentStore.updateComponentSize(
      selectedComponent.value.id,
      selectedComponent.value.width,
      selectedComponent.value.height
    )
  }
}
</script>

<style scoped>
.property-panel {
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
  margin: 0;
  color: #303133;
  font-size: 16px;
}

.panel-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.property-group {
  margin-bottom: 24px;
  background: #fff;
  border-radius: 6px;
  padding: 16px;
  border: 1px solid #e4e7ed;
}

.group-title {
  font-weight: 500;
  color: #303133;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 1px solid #f0f2f5;
}

.property-item {
  margin-bottom: 16px;
}

.property-item:last-child {
  margin-bottom: 0;
}

.property-item label {
  display: block;
  margin-bottom: 6px;
  font-size: 13px;
  color: #606266;
  font-weight: 500;
}

.property-row {
  display: flex;
  gap: 12px;
}

.property-row .property-item {
  flex: 1;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #909399;
}

.empty-state p {
  margin: 16px 0 0 0;
  font-size: 14px;
}

:deep(.el-input-number) {
  width: 100%;
}

:deep(.el-select) {
  width: 100%;
}

:deep(.el-color-picker) {
  width: 100%;
}
</style>