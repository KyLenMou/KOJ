<template>
  <tiny-tag v-if="judgeMode === 'default'" :effect="effect">默认评测</tiny-tag>
  <tiny-tag v-else-if="judgeMode === 'spj'" :effect="effect" type="success"
    >特殊评测</tiny-tag
  >
  <tiny-tag v-else-if="judgeMode === 'interact'" :effect="effect" type="info"
    >交互评测</tiny-tag
  >
  <tiny-tag v-else :effect="effect" type="danger">未知评测</tiny-tag>
</template>

<script setup lang="ts">
defineProps<{ judgeMode: string; effect: string }>()
</script>

<style scoped></style>
