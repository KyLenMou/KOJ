<template>
  <tiny-tag v-if="judgeMode === 'default'" size="small" :effect="effect">默认评测</tiny-tag>
  <tiny-tag v-else-if="judgeMode === 'spj'" size="small" :effect="effect" type="success"
    >特殊评测</tiny-tag
  >
  <tiny-tag v-else-if="judgeMode === 'interact'" size="small" :effect="effect" type="info"
    >交互评测</tiny-tag
  >
  <tiny-tag v-else :effect="effect" size="small" type="danger">未知评测</tiny-tag>
</template>

<script setup lang="ts">
const props = defineProps({
  judgeMode: {
    type: String,
    required: true
  },
  effect: {
    type: String,
    default: 'light'
  }
})
</script>

<style scoped></style>
