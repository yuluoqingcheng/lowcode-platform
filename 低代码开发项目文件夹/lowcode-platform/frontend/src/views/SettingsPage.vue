<template>
  <div class="settings-page">
    <div class="page-header">
      <h2>系统设置</h2>
    </div>

    <div class="page-content">
      <el-tabs v-model="activeTab" class="settings-tabs">
        <!-- 基本设置 -->
        <el-tab-pane label="基本设置" name="basic">
          <div class="settings-section">
            <h3>界面设置</h3>
            <div class="setting-item">
              <label>主题色彩</label>
              <el-color-picker v-model="settings.theme.primaryColor" />
            </div>
            <div class="setting-item">
              <label>界面语言</label>
              <el-select v-model="settings.language">
                <el-option label="简体中文" value="zh-CN" />
                <el-option label="English" value="en-US" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>显示网格</label>
              <el-switch v-model="settings.canvas.showGrid" />
            </div>
            <div class="setting-item">
              <label>自动保存</label>
              <el-switch v-model="settings.editor.autoSave" />
            </div>
            <div class="setting-item">
              <label>自动保存间隔</label>
              <el-input-number 
                v-model="settings.editor.autoSaveInterval" 
                :min="10" 
                :max="300" 
                :step="10"
                :disabled="!settings.editor.autoSave"
              />
              <span class="unit">秒</span>
            </div>
          </div>
        </el-tab-pane>

        <!-- 编辑器设置 -->
        <el-tab-pane label="编辑器" name="editor">
          <div class="settings-section">
            <h3>代码生成</h3>
            <div class="setting-item">
              <label>代码风格</label>
              <el-select v-model="settings.codeGen.style">
                <el-option label="标准风格" value="standard" />
                <el-option label="Prettier" value="prettier" />
                <el-option label="自定义" value="custom" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>缩进方式</label>
              <el-radio-group v-model="settings.codeGen.indent">
                <el-radio label="spaces">空格</el-radio>
                <el-radio label="tabs">制表符</el-radio>
              </el-radio-group>
            </div>
            <div class="setting-item">
              <label>缩进大小</label>
              <el-input-number v-model="settings.codeGen.indentSize" :min="2" :max="8" />
            </div>
            <div class="setting-item">
              <label>生成TypeScript</label>
              <el-switch v-model="settings.codeGen.typescript" />
            </div>
            <div class="setting-item">
              <label>生成单元测试</label>
              <el-switch v-model="settings.codeGen.generateTests" />
            </div>
          </div>

          <div class="settings-section">
            <h3>组件设置</h3>
            <div class="setting-item">
              <label>默认组件大小</label>
              <el-select v-model="settings.component.defaultSize">
                <el-option label="小" value="small" />
                <el-option label="中等" value="medium" />
                <el-option label="大" value="large" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>组件边框</label>
              <el-switch v-model="settings.component.showBorder" />
            </div>
            <div class="setting-item">
              <label>吸附对齐</label>
              <el-switch v-model="settings.component.snapToGrid" />
            </div>
            <div class="setting-item">
              <label>吸附距离</label>
              <el-input-number 
                v-model="settings.component.snapDistance" 
                :min="1" 
                :max="20"
                :disabled="!settings.component.snapToGrid"
              />
              <span class="unit">px</span>
            </div>
          </div>
        </el-tab-pane>

        <!-- 项目设置 -->
        <el-tab-pane label="项目" name="project">
          <div class="settings-section">
            <h3>项目管理</h3>
            <div class="setting-item">
              <label>默认项目路径</label>
              <el-input v-model="settings.project.defaultPath" />
            </div>
            <div class="setting-item">
              <label>项目模板路径</label>
              <el-input v-model="settings.project.templatePath" />
            </div>
            <div class="setting-item">
              <label>自动备份</label>
              <el-switch v-model="settings.project.autoBackup" />
            </div>
            <div class="setting-item">
              <label>备份保留天数</label>
              <el-input-number 
                v-model="settings.project.backupDays" 
                :min="1" 
                :max="365"
                :disabled="!settings.project.autoBackup"
              />
              <span class="unit">天</span>
            </div>
          </div>

          <div class="settings-section">
            <h3>构建设置</h3>
            <div class="setting-item">
              <label>包管理器</label>
              <el-select v-model="settings.build.packageManager">
                <el-option label="npm" value="npm" />
                <el-option label="yarn" value="yarn" />
                <el-option label="pnpm" value="pnpm" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>Node.js 版本</label>
              <el-select v-model="settings.build.nodeVersion">
                <el-option label="18.x" value="18" />
                <el-option label="20.x" value="20" />
                <el-option label="21.x" value="21" />
              </el-select>
            </div>
            <div class="setting-item">
              <label>构建输出目录</label>
              <el-input v-model="settings.build.outputDir" />
            </div>
          </div>
        </el-tab-pane>

        <!-- 关于 -->
        <el-tab-pane label="关于" name="about">
          <div class="about-section">
            <div class="app-info">
              <div class="app-logo">
                <el-icon size="64" color="#409eff"><Setting /></el-icon>
              </div>
              <h2>低代码开发平台</h2>
              <p class="version">版本 1.0.0</p>
              <p class="description">
                一个强大的可视化低代码开发工具，支持拖拽式页面设计、
                实时预览、代码生成等功能。
              </p>
            </div>

            <div class="system-info">
              <h3>系统信息</h3>
              <div class="info-grid">
                <div class="info-item">
                  <label>平台版本</label>
                  <span>1.0.0</span>
                </div>
                <div class="info-item">
                  <label>构建时间</label>
                  <span>2024-01-01 10:00:00</span>
                </div>
                <div class="info-item">
                  <label>Node.js</label>
                  <span>v18.17.0</span>
                </div>
                <div class="info-item">
                  <label>Vue</label>
                  <span>v3.3.8</span>
                </div>
                <div class="info-item">
                  <label>操作系统</label>
                  <span>Windows 11</span>
                </div>
                <div class="info-item">
                  <label>浏览器</label>
                  <span>Chrome 120.0.0</span>
                </div>
              </div>
            </div>

            <div class="links-section">
              <h3>相关链接</h3>
              <div class="links-grid">
                <el-button text type="primary">使用文档</el-button>
                <el-button text type="primary">API参考</el-button>
                <el-button text type="primary">问题反馈</el-button>
                <el-button text type="primary">更新日志</el-button>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>

      <!-- 保存按钮 -->
      <div class="actions">
        <el-button @click="resetSettings">重置</el-button>
        <el-button type="primary" @click="saveSettings">保存设置</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Setting } from '@element-plus/icons-vue'

const activeTab = ref('basic')

// 设置数据
const settings = reactive({
  theme: {
    primaryColor: '#409eff'
  },
  language: 'zh-CN',
  canvas: {
    showGrid: true
  },
  editor: {
    autoSave: true,
    autoSaveInterval: 30
  },
  codeGen: {
    style: 'prettier',
    indent: 'spaces',
    indentSize: 2,
    typescript: true,
    generateTests: false
  },
  component: {
    defaultSize: 'medium',
    showBorder: true,
    snapToGrid: true,
    snapDistance: 10
  },
  project: {
    defaultPath: './projects',
    templatePath: './templates',
    autoBackup: true,
    backupDays: 30
  },
  build: {
    packageManager: 'npm',
    nodeVersion: '18',
    outputDir: 'dist'
  }
})

// 保存设置
const saveSettings = () => {
  // 这里应该调用API保存设置
  localStorage.setItem('lowcode-settings', JSON.stringify(settings))
  ElMessage.success('设置保存成功！')
}

// 重置设置
const resetSettings = () => {
  // 重置为默认值
  Object.assign(settings, {
    theme: { primaryColor: '#409eff' },
    language: 'zh-CN',
    canvas: { showGrid: true },
    editor: { autoSave: true, autoSaveInterval: 30 },
    codeGen: { style: 'prettier', indent: 'spaces', indentSize: 2, typescript: true, generateTests: false },
    component: { defaultSize: 'medium', showBorder: true, snapToGrid: true, snapDistance: 10 },
    project: { defaultPath: './projects', templatePath: './templates', autoBackup: true, backupDays: 30 },
    build: { packageManager: 'npm', nodeVersion: '18', outputDir: 'dist' }
  })
  ElMessage.info('设置已重置为默认值')
}

// 初始化时加载设置
const loadSettings = () => {
  const saved = localStorage.getItem('lowcode-settings')
  if (saved) {
    try {
      Object.assign(settings, JSON.parse(saved))
    } catch (e) {
      console.warn('Failed to load settings:', e)
    }
  }
}

loadSettings()
</script>

<style scoped>
.settings-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
}

.page-header {
  height: 60px;
  display: flex;
  align-items: center;
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

.settings-tabs {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  padding: 24px;
  margin-bottom: 24px;
}

.settings-section {
  margin-bottom: 32px;
}

.settings-section h3 {
  margin: 0 0 20px 0;
  color: #303133;
  font-size: 16px;
  font-weight: 500;
  padding-bottom: 8px;
  border-bottom: 1px solid #f0f2f5;
}

.setting-item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  gap: 16px;
}

.setting-item label {
  min-width: 120px;
  color: #606266;
  font-size: 14px;
}

.setting-item .unit {
  color: #909399;
  font-size: 12px;
  margin-left: 8px;
}

.about-section {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.app-info {
  text-align: center;
}

.app-logo {
  margin-bottom: 16px;
}

.app-info h2 {
  margin: 0 0 8px 0;
  color: #303133;
  font-size: 24px;
}

.version {
  margin: 0 0 16px 0;
  color: #909399;
  font-size: 14px;
}

.description {
  margin: 0;
  color: #606266;
  line-height: 1.6;
  max-width: 500px;
  margin: 0 auto;
}

.system-info h3,
.links-section h3 {
  margin: 0 0 16px 0;
  color: #303133;
  font-size: 16px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 12px 16px;
  background: #f8f9fa;
  border-radius: 6px;
}

.info-item label {
  color: #606266;
  font-size: 14px;
}

.info-item span {
  color: #303133;
  font-size: 14px;
  font-weight: 500;
}

.links-grid {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 16px 24px;
  background: #fff;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
}

:deep(.el-tabs__content) {
  padding-top: 20px;
}

:deep(.el-input-number) {
  width: 120px;
}

:deep(.el-select) {
  width: 160px;
}
</style>