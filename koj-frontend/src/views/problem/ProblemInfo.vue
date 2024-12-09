<template>
  <div>
    <div style="display: flex; gap: 10px;margin-bottom: 10px;">
      <tiny-tag type="info" effect="plain">{{ `时间限制：` + timeLimit + `ms` }}</tiny-tag>
      <tiny-tag type="warning" effect="plain">{{ `内存限制：` + memoryLimit + `MB` }}</tiny-tag>
      <tiny-tag type="success" effect="plain">{{ `栈限制：` + stackLimit + `MB` }}</tiny-tag>
    </div>
    <div class="info-title">题目描述</div>
    <md-preview :text="description" />
    <div class="info-title">输入</div>
    <md-preview :text="input" />
    <div class="info-title">输出</div>
    <md-preview :text="output" />
    <div class="info-title">样例</div>
    <!-- todo 使用tinygrid -->
    <table class="example-table">
      <thead>
        <tr>
          <th style="text-align: center; padding: 0">标准输入</th>
          <th style="text-align: center; padding: 0">标准输出</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(example, index) in parsedExamples" :key="index">
          <td
            style="
              white-space: pre-wrap;
              vertical-align: top;
              font-family: 'Courier New', Courier, monospace;
            "
          >
            {{ example.input }}
          </td>
          <td
            style="
              white-space: pre-wrap;
              vertical-align: top;
              font-family: 'Courier New', Courier, monospace;
            "
          >
            {{ example.output }}
          </td>
        </tr>
      </tbody>
    </table>
    <div class="info-title">注释</div>
    <md-preview :text="note" />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps({
  description: String,
  input: String,
  output: String,
  examples: String,
  note: String,
  timeLimit: Number,
  memoryLimit: Number,
  stackLimit: Number
})

const parsedExamples = computed(() => {
  try {
    return JSON.parse(props.examples as string)
  } catch (e) {
    return []
  }
})
</script>

<style scoped>
.example-table {
  margin: 10px;
  min-width: 75%;
  border-collapse: collapse;
}

.example-table th,
.example-table td {
  border: 1px solid black;
  width: 50%;
  padding: 5px;
  text-align: left;
}
.info-title {
  font-size: 20px;
  font-weight: bold;
}
</style>
