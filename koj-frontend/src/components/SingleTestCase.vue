<template>
  <tiny-tooltip placement="top">
    <template #content>
        <div style="text-align: center;">
      <div>{{ '评测点：#' + id }}</div>
      <div> 评测机输出： {{judgeOutput?.length ? judgeOutput : '无' }}</div>
    </div>
    </template>
    <div class="single-test-case" :style="'background-color:' + getVerdictModel(verdict)?.color">
      <div class="badge">
        <span style="font-size: 2em">#</span>
        <span style="font-size: 4em">{{ id }}</span>
      </div>
      <div class="detail">
        <div style="font-weight: bold; font-size: 1.2em">
          {{ getVerdictModel(verdict)?.short }}
        </div>
        <div>
          {{ time + 'ms' }}
        </div>
        <div>
          {{ memory + 'KB' }}
        </div>
      </div>
    </div>
  </tiny-tooltip>
</template>

<script setup lang="ts">
import { getVerdictModel } from '@/utils'

defineProps<{
  judgeOutput?: string
  verdict?: number | any
  time?: number
  memory?: number
  id?: number
}>()
</script>

<style scoped>
.single-test-case {
  width: 100%;
  height: 100px;
  margin-top: 10px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  text-align: center;
}

.badge {
  position: absolute;
  right: 5px;
  bottom: -5px;
  color: rgba(255, 255, 255, 0.25);
  user-select: none;
  z-index: 0;
}
.detail {
  z-index: 1;
  color: #fff;
}
</style>
