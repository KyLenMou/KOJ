<template>
    <tiny-tag
        effect="dark"
        :type="getVerdictModel(verdict)?.type"
    >
        {{ getVerdictModel(verdict)?.full }}
    </tiny-tag>
</template>

<script setup lang="ts">
import { getVerdictModel } from '@/utils';

const verdict = defineModel<number | any>('verdict')
</script>

<style scoped>
</style>
