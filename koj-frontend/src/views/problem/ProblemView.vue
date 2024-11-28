<template>
  <div id="problemLayout">
    <div id="problem-view-header">
      <div style="display: flex; align-items: center">
        <tiny-image fit="fill" :src="getImg()" style="height: 40px" />
        <div style="margin-right: 10px">
          <span class="oj-card-subtitle">{{ problemDetail.displayId + `. ` }}</span>
          <span class="oj-card-title">{{ problemDetail.title + ` ` }}</span>
        </div>
        <div style="display: flex; gap: 10px">
          <tiny-tag type="info" effect="plain">{{
            `时间限制：` + problemDetail.timeLimit + `ms`
          }}</tiny-tag>
          <tiny-tag type="warning" effect="plain">{{
            `内存限制：` + problemDetail.memoryLimit + `MB`
          }}</tiny-tag>
          <difficulty-tag :difficulty="problemDetail.difficulty" />
          <tiny-popover trigger="click">
            <template #default>
              <tiny-tag
                v-for="tag in problemDetail.tags"
                :key="tag"
                type="info"
                style="margin-right: 5px"
              >
                {{ tag.tagName }}
              </tiny-tag>
            </template>
            <template #reference>
              <tiny-button
                size="mini"
                style="border-radius: 5px"
                type="primary"
                plain
                :icon="IconPushpin"
              >
                查看标签</tiny-button
              >
            </template>
          </tiny-popover>
        </div>
        <tiny-button
          style="margin-left: auto; margin-right: 10px"
          size="small"
          @click="splitLayout = splitLayout === 'fashion' ? 'classic' : 'fashion'"
          >切换布局</tiny-button
        >
        <tiny-button style="margin-right: 10px" size="small">返回</tiny-button>
        <user-button style="margin-right: 10px" />
      </div>
    </div>
    <div id="problem-view-body">
      <tiny-split
        v-if="splitLayout === 'fashion'"
        left-top-min="400px"
        right-top-min="400px"
        v-model="splitFashion1"
        trigger-simple
        collapse-right-bottom
        three-areas
      >
        <template #left>
          <tiny-split
            left-top-min="400px"
            right-top-min="400px"
            v-model="splitFashion2"
            trigger-simple
            collapse-left-top
            three-areas
          >
            <template #left>
              <div class="split-content description-content">
                <problem-info
                  :description="problemDetail.description"
                  :input="problemDetail.input"
                  :output="problemDetail.output"
                  :examples="problemDetail.examples"
                  :note="problemDetail.note"
                />
              </div>
            </template>
            <template #right>
              <code-editor />
            </template>
          </tiny-split>
        </template>
        <template #right>
          <div class="split-content">内容C区</div>
        </template>
      </tiny-split>

      <tiny-split v-else v-model="splitClassic1" trigger-simple>
        <template #left>
          <div class="split-content description-content">
            <problem-info
              :description="problemDetail.description"
              :input="problemDetail.input"
              :output="problemDetail.output"
              :examples="problemDetail.examples"
              :note="problemDetail.note"
            />
          </div>
        </template>
        <template #right>
          <tiny-split v-model="splitClassic2" mode="vertical">
            <template #top>
              <code-editor />
            </template>
            <template #bottom>
              <div class="demo-split-pane">下面板</div>
            </template>
          </tiny-split>
        </template>
      </tiny-split>
    </div>
  </div>
</template>

<script setup lang="ts">
import ProblemInfo from './ProblemInfo.vue'
import { ProblemControllerService, type ProblemDetailVO } from '@/api'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { iconPushpin } from '@opentiny/vue-icon'
const IconPushpin = iconPushpin()
// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo.png`, import.meta.url).href
}
// classic fashion
const splitLayout = ref('classic')

const splitFashion1 = ref(0.85)
const splitFashion2 = ref(0.4)

const splitClassic1 = ref(0.5)
const splitClassic2 = ref(0.8)

const problemDetail = ref<ProblemDetailVO>({
  authorUserId: '',
  authorUsername: '',
  description: '题目描述',
  difficulty: 800,
  displayId: 'P1000',
  examples: '[{"input":"input","output":"output"}]',
  id: 1000,
  input: '输入描述',
  judgeMode: 'default',
  memoryLimit: 128,
  note: '提示',
  output: '输出描述',
  stackLimit: 128,
  tags: [],
  timeLimit: 1000,
  title: '标题'
})
const route = useRoute()
onMounted(async () => {
  // 获取problem/:id的id
  const problemDisplayId = route.params.id
  const { code, data } = await ProblemControllerService.getProblemDetailVoUsingGet(
    problemDisplayId as string
  )
  if (code) return
  problemDetail.value = data as ProblemDetailVO
})
</script>

<style scoped>
#problemLayout {
  height: 100vh;
}
#problem-view-header {
  height: 50px;
}
#problem-view-body {
  height: calc(100vh - 50px);
  display: flex;
}
:deep(.tiny-split-wrapper) {
  border-radius: 0;
}
:deep(.tiny-image) {
  margin: 5px 20px;
}
.split-content {
  padding: 15px;
}
.description-content {
  overflow-y: auto;
  max-height: 100%;
}

.description-content::-webkit-scrollbar {
  display: none;
}
</style>
