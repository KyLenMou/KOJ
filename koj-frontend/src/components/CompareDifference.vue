<template>
  <div ref="container" style="height: 250px; width: 100%"></div>
</template>

<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onMounted, nextTick, ref } from 'vue'
let originalModel
let modifiedModel
let monacoDiffInstance
const container = ref()
interface Props {
  old: string
  new: string
  height: number
  width: number
}
const props = withDefaults(defineProps<Props>(), {
  old: () => '',
  new: () => ''
})
onMounted(() => {
  defineDiff()
})

const defineDiff = () => {
  monacoDiffInstance = monaco.editor.createDiffEditor(container.value, {
    theme: 'vs',
    automaticLayout: true,
    minimap: {
      enabled: false
    },
    scrollbar: {
      verticalScrollbarSize: 4,
      horizontalScrollbarSize: 4
    },
    lineNumbers: 'off',
    fontSize: 14,
    readOnly: true,
  })
  originalModel = monaco.editor.createModel(props.old)
  modifiedModel = monaco.editor.createModel(props.new)

  monacoDiffInstance.setModel({
    original: originalModel,
    modified: modifiedModel
  })
}
</script>
