<template>
  <div class="codeEditorToolBar">
    <tiny-select v-model="language" style="width: 100px; margin: auto 5px" size="mini">
      <tiny-option label="C" value="c"></tiny-option>
      <tiny-option label="C++" value="cpp"></tiny-option>
      <tiny-option label="Java" value="java"></tiny-option>
      <tiny-option label="Python" value="python"></tiny-option>
      <tiny-option label="GoLang" value="go"></tiny-option>
    </tiny-select>
    <tiny-switch
      v-model="isDark"
      :true-value="true"
      :false-value="false"
      style="margin: auto 20px auto auto"
    >
      <template #active-icon>
        <img src="@/assets/images/dark.svg" style="width: 14px; user-select: none" />
      </template>
      <template #inactive-icon>
        <img src="@/assets/images/light.svg" style="width: 14px; user-select: none" />
      </template>
    </tiny-switch>
    <tiny-popconfirm
      title="清空代码？"
      type="warning"
      trigger="click"
      @confirm="cleanCode"
      style="margin: auto 10px auto 0"
    >
      <template #reference>
        <tiny-button type="text" :icon="TinyIconDel" size="small"> </tiny-button>
      </template>
    </tiny-popconfirm>
    <tiny-popover trigger="click" style="margin: auto 10px auto 0" width="300">
      <template #default>
        <tiny-form label-position="top">
              <tiny-form-item label="字体大小">
                <tiny-numeric
                  v-model="fontSize"
                  placeholder="字体大小"
                  :min="10"
                  :max="30"
                  circulate
                ></tiny-numeric></tiny-form-item
            >
          <tiny-row>
            <tiny-col :span="6">
                <tiny-form-item label="显示行号">
                    <tiny-switch v-model="lineNumbers" size="small" true-value="on" false-value="off"></tiny-switch>
                </tiny-form-item>
            </tiny-col>
            <tiny-col :span="6">
              <tiny-form-item label="Tab大小">
                <tiny-select v-model="tabSize">
                  <tiny-option :value="2"></tiny-option>
                  <tiny-option :value="4"></tiny-option>
                </tiny-select> </tiny-form-item
            ></tiny-col>
          </tiny-row>
        </tiny-form>
      </template>
      <template #reference>
        <tiny-button type="text" :icon="TinyIconSetting" size="small"> </tiny-button>
      </template>
    </tiny-popover>
  </div>
  <div ref="codeEditorRef" style="height: calc(100% - 35px)" />
</template>

<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onMounted, ref, toRaw, withDefaults, defineProps, watch } from 'vue'

import { IconDel, IconSetting } from '@opentiny/vue-icon'

const TinyIconDel = IconDel()
const TinyIconSetting = IconSetting()
const language = ref('cpp')
const isDark = ref(false)
const fontSize = ref(16)
const tabSize = ref(4)
const lineNumbers = ref('on')
watch(lineNumbers, (v) => {
  codeEditor.value.updateOptions({ lineNumbers: v })
})
watch(fontSize, (v) => {
  codeEditor.value.updateOptions({ fontSize: v })
})
watch(tabSize, (v) => {
  codeEditor.value.updateOptions({ tabSize: v })
})
watch(language, (v) => {
  monaco.editor.setModelLanguage(toRaw(codeEditor.value).getModel(), v)
})
watch(isDark, (v) => {
  if (v) {
    monaco.editor.setTheme('vs-dark')
  } else {
    monaco.editor.setTheme('vs')
  }
})
const cleanCode = () => {
  toRaw(codeEditor.value).setValue('')
}
/**
 * 定义组件属性类型
 */
interface Props {
  code: string
  handleChange: (v: string) => void
}
/**
 * 给组件指定初始值
 */
const props = withDefaults(defineProps<Props>(), {
  code: () => '#include<bits/stdc++.h>',
  handleChange: (v: string) => {
    console.log(toRaw(v))
  }
})

const codeEditorRef = ref()
const codeEditor = ref()

onMounted(() => {
  if (!codeEditorRef.value) {
    return
  }
  // Hover on each property to see its docs!
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.code,
    language: language.value,
    automaticLayout: true,
    colorDecorators: true,
    foldingStrategy: 'indentation', // 代码可分小段折叠
    minimap: {
      enabled: false
    },
　　lineNumbers: lineNumbers.value,
    fontSize: fontSize.value,
    tabSize: tabSize.value,
    readOnly: false,
    theme: 'vs',
    roundedSelection: false,
    scrollBeyondLastLine: false
  })
  codeEditor.value.onDidChangeModelContent(() => {
    props.handleChange(toRaw(codeEditor.value).getValue())
  })
})
</script>

<style scoped>
.codeEditorToolBar {
  width: 100%;
  height: 35px;
  border-bottom: 1px solid #e8e8e8;
  display: flex;
}
.tiny-switch.tiny-switch-checked {
  background-color: #000;
}
</style>
