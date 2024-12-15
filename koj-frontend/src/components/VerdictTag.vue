<template>
  <tiny-tag effect="dark" :type="getVerdictModel(verdict)?.type">
    {{ getVerdictModel(verdict)?.full }}
  </tiny-tag>
</template>

<script setup lang="ts">
import { getVerdictModel } from '@/utils'

defineProps<{ verdict: number }>()
</script>

<style scoped></style>
