<template>
  <div id="mdEditor">
    <mavon-editor
      style="max-height: 500px"
      v-model="localValue"
      :toolbars="toolbars"
      :language="language"
      font-size="10px"
    />
  </div>
</template>

<script setup lang="ts">
const language = ref("en");

// 定义 modelValue prop
const props = defineProps({
  modelValue: String
});

// 创建一个本地的响应式属性
const localValue = ref(props.modelValue);

// 当 modelValue prop 更新时，更新本地的值
watch(() => props.modelValue, (newVal) => {
  localValue.value = newVal;
});

// 定义一个 emit 函数，用于触发自定义事件
const emit = defineEmits(['update:modelValue']);

// 当本地的值更新时，触发 'update:modelValue' 事件
watch(() => localValue.value, (newVal) => {
  emit('update:modelValue', newVal);
});


const toolbars = {
  bold: true, // 粗体
  italic: true, // 斜体
  header: false, // 标题
  underline: true, // 下划线
  strikethrough: true, // 中划线
  mark: true, // 标记
  superscript: true, // 上角标
  subscript: true, // 下角标
  quote: false, // 引用
  ol: false, // 有序列表
  ul: false, // 无序列表
  link: true, // 链接
  imagelink: false, // 图片链接
  code: true, // code
  table: false, // 表格
  fullscreen: false, // 全屏编辑
  readmodel: false, // 沉浸式阅读
  htmlcode: true, // 展示html源码
  help: false, // 帮助
  /* 1.3.5 */
  undo: true, // 上一步
  redo: true, // 下一步
  trash: false, // 清空
  save: false, // 保存（触发events中的save事件）
  /* 1.4.2 */
  navigation: true, // 导航目录
  /* 2.1.8 */
  alignleft: true, // 左对齐
  aligncenter: true, // 居中
  alignright: true, // 右对齐
  /* 2.2.1 */
  subfield: false, // 单双栏模式
  preview: true, // 预览
}
</script>

<style scoped>
#mdEditor {
  width: 100%;
}
</style>
