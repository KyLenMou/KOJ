<template>
  <tiny-tag
    :color="[getDiffcultyTagColor(difficulty), getDiffcultyColor(difficulty)]"
    class="difficulty-div"
    >{{ '*' + difficulty }}</tiny-tag
  >
</template>

<script setup lang="ts">
import { getDiffcultyTagColor, getDiffcultyColor } from '@/utils'
defineProps<{ difficulty: number }>()
</script>

<style scoped>
.difficulty-div {
  font-weight: bold;
  font-family: verdana, arial, sans-serif;
}
</style>
