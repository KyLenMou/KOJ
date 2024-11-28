<template>
  <tiny-tag
    :color="[getDiffcultyTagColor(difficulty), getDiffcultyColor(difficulty)]"
    class="difficulty-div"
    >{{ '*'+difficulty }}</tiny-tag
  >
</template>

<script setup lang="ts">
import { getDiffcultyTagColor, getDiffcultyColor } from '@/utils'
const props = defineProps({
  difficulty: {
    type: Number,
    required: true
  }
})
</script>

<style scoped>
.difficulty-div {
  font-weight: bold;
  font-family: verdana, arial, sans-serif;
}
</style>
