<template>
  <div :style="`color:` + getDiffcultyColor(difficulty)" class="difficulty-div">
    {{ difficulty }}
  </div>
</template>

<script setup lang="ts">
import { getDiffcultyColor } from '@/utils'
const props = defineProps({
  difficulty: {
    type: Number,
    required: true
  }
})
</script>

<style scoped>
</style>
