<template>
  <div :style="`color:` + getDiffcultyColor(difficulty)" class="difficulty-div">
    {{ difficulty }}
  </div>
</template>

<script setup lang="ts">
import { getDiffcultyColor } from '@/utils'
defineProps<{ difficulty: number }>()
</script>

<style scoped>
</style>
