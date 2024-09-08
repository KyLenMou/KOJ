<template>
  <div id="mdPreview">
    <mavon-editor
      style="min-height: 0;"
      :default-open="'preview'"
      v-model="localValue"
      :language="language"
      :subfield="false"
      :editable="false"
      :toolbars-flag="false"
      :ishljs="true"
      box-shadow-style="0 0 0 0 rgba(0,0,0,0)"
      preview-background="transparent"
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
</script>

<style scoped>
#mdPreview {

}
:deep(.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html) {
  padding: 10px;
}
</style>
