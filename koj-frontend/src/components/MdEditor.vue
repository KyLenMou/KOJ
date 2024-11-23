<template>
  <mavon-editor
    class="md-editor"
    v-model="localValue"
    :language="language"
    :toolbarsFlag="false"
    placeholder="请输入..."
  />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

const language = ref('en')

// 定义 modelValue prop
const props = defineProps({
  modelValue: String
})

// 创建一个本地的响应式属性
const localValue = ref(props.modelValue)

// 当 modelValue prop 更新时，更新本地的值
watch(
  () => props.modelValue,
  (newVal) => {
    localValue.value = newVal
  }
)

// 定义一个 emit 函数，用于触发自定义事件
const emit = defineEmits(['update:modelValue'])

// 当本地的值更新时，触发 'update:modelValue' 事件
watch(
  () => localValue.value,
  (newVal) => {
    emit('update:modelValue', newVal)
  }
)
</script>

<style scoped>
</style>
