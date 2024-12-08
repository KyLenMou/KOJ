<template>
  <div id="problemLayout">
    <div id="problem-view-header">
      <div style="display: flex; align-items: center">
        <tiny-image
          fit="fill"
          :src="getImg()"
          style="height: 40px; cursor: pointer"
          @click="goToHome"
        />
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
          <tiny-tag type="success" effect="plain">{{
            `栈限制：` + problemDetail.stackLimit + `MB`
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
        <tiny-button style="margin-right: 20px" size="small" @click="goToHome"
          >返回首页</tiny-button
        >
        <user-button style="margin-right: 20px" />
      </div>
    </div>
    <div id="problem-view-body">
      <!-- 时髦布局 -->
      <tiny-split
        v-if="splitLayout === 'fashion'"
        left-top-min="900px"
        right-bottom-min="300px"
        v-model="splitFashion1"
        trigger-simple
        collapse-right-bottom
      >
        <template #left>
          <tiny-split
            left-top-min="300px"
            right-bottom-min="600px"
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
              <code-editor
                @update:language="handleLanguageUpdate"
                :code="problemSubmitDTO.code"
                :handleChange="handleCodeChange"
              />
            </template>
          </tiny-split>
        </template>
        <template #right>
          <problem-debug
            v-if="isMounted"
            v-model:problemSubmitDTO="problemSubmitDTO"
            v-model:problemDetail="problemDetail"
          />
        </template>
      </tiny-split>
      <!-- 传统布局 -->
      <tiny-split
        v-else
        v-model="splitClassic1"
        trigger-simple
        left-top-min="400px"
        right-bottom-min="400px"
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
          <tiny-split
            v-model="splitClassic2"
            mode="vertical"
            trigger-simple
            left-top-min="200px"
            right-bottom-min="200px"
          >
            <template #top>
              <code-editor
                @update:language="handleLanguageUpdate"
                :code="problemSubmitDTO.code"
                :handleChange="handleCodeChange"
              />
            </template>
            <template #bottom>
                <!-- todo testcase需要父组件管理，否则切换布局之后数据会消失 -->
              <problem-debug
                v-if="isMounted"
                v-model:problemSubmitDTO="problemSubmitDTO"
                v-model:problemDetail="problemDetail"
              />
            </template>
          </tiny-split>
        </template>
      </tiny-split>
    </div>
  </div>
</template>

<script setup lang="ts">
import ProblemInfo from './ProblemInfo.vue'
import ProblemDebug from './ProblemDebug.vue'
import { ProblemControllerService, type ProblemDetailVO, type SubmissionDTO } from '@/api'
import { onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { iconPushpin } from '@opentiny/vue-icon'
import { TinyModal } from '@opentiny/vue'
const IconPushpin = iconPushpin()
const router = useRouter()
const goToHome = () => {
  router.push('/')
}
// 获得logo图片
const getImg = (): string => {
  return new URL(`@/assets/logo.png`, import.meta.url).href
}

const handleCodeChange = (code: string) => {
  problemSubmitDTO.value.code = code
}

const handleLanguageUpdate = (newLanguage: string) => {
  problemSubmitDTO.value.language = newLanguage
}

const splitLayout = ref('fashion')

const splitFashion1 = ref(0.8)
const splitFashion2 = ref(0.45)

const splitClassic1 = ref(0.49)
const splitClassic2 = ref(0.7)

const problemSubmitDTO = ref<SubmissionDTO>({
  code: '',
  language: 'cpp',
  problemId: 0
})

watch(
  () => problemSubmitDTO.value.language,
  (newVal: any) => {
    console.log(newVal)
  }
)
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
const isMounted = ref(false)
onMounted(async () => {
  // 获取problem/:id的id
  const problemDisplayId = route.params.id
  const { code, data } = await ProblemControllerService.getProblemDetailVoUsingGet(
    problemDisplayId as string
  )
  if (code) return
  problemDetail.value = data as ProblemDetailVO
  isMounted.value = true
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
.tiny-button.tiny-button--primary {
  fill: #000;
}

:deep(.tabClass > .tiny-tabs__header .tiny-tabs__nav) {
  margin-left: 20px;
}
:deep(#tab-0 > .tiny-tabs__icon-close) {
  transform: rotate(45deg);
}

:deep(.tiny-tabs.tiny-tabs--button-card .tiny-tabs__item__title) {
  padding: 0 10px;
}
:deep(.tiny-tabs.tiny-tabs--button-card .tiny-tabs__item .tiny-tabs__icon-close) {
  margin-left: -5px;
  margin-right: 10px;
}
</style>
