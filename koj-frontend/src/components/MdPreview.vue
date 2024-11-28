<template>
  <div id="mdPreview">
    <mavon-editor
      style="min-height: 0;"
      :default-open="'preview'"
      :modelValue="text"
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
import { ref, watch } from 'vue';

const language = ref("en");
const props = defineProps({
  text: String
})
</script>

<style scoped>
#mdPreview {

}
:deep(.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html) {
  padding: 10px;
}
:deep(.markdown-body) {
  font-size: 1em;
}
</style>
