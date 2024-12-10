<template>
  <div v-loading="isLoading" tiny-loading__background="rgba(0,0,0,0.2)">
    <div style="display: flex; gap: 10px">
      <!-- 只看我的 -->
      <tiny-base-select v-model="submissonVerdictQuery.onlyMine" style="width: 120px">
        <tiny-option label="只看我的" :value="true" />
        <tiny-option label="查看所有" :value="false" />
      </tiny-base-select>
      <!-- 编程语言 -->
      <tiny-base-select
        v-model="submissonVerdictQuery.language"
        clearable
        placeholder="编程语言"
        style="width: 120px"
      >
        <tiny-option
          v-for="(item, index) in LanguageList"
          :key="index"
          :label="item.name"
          :value="item.value"
        />
      </tiny-base-select>
      <!-- 评测状态 -->
      <tiny-base-select
        v-model="submissonVerdictQuery.verdict"
        clearable
        placeholder="评测状态"
        style="width: 210px"
      >
        <tiny-option
          v-for="(item, index) in VerdictList"
          :key="index"
          :label="item.name"
          :value="item.verdict"
        />
      </tiny-base-select>
      <tiny-button :icon="IconConmentRefresh" @click="getSubmissionList" />
    </div>
    <div v-for="(submission, index) in submissionList" :key="index" class="submisson-box">
      <div style="display: flex; justify-content: space-between">
        <div style="margin: 5px">
          <div style="font-size: 15px; font-weight: 600; margin-bottom: 5px">
            {{ submission?.username }}
          </div>
          <div style="color: gray">
            {{
              getLanguageByShortName(submission.language) +
              ' • ' +
              fromNow(submission.submitTime as any)
            }}
          </div>
        </div>
        <!-- 非最终状态，显示进度条 -->
        <div
          v-if="submission.verdict !== undefined && [1, 100, 200, 201].includes(submission.verdict)"
          style="flex: 1"
        >
          <div style="font-size: 15px; font-weight: 600; margin: 5px; text-align: center">
            <div
              style="display: flex; justify-content: center; gap: 5px"
              :style="
                'color:' +
                getProgressModel(submission.verdict).color +
                '; fill: ' +
                getProgressModel(submission.verdict).color
              "
            >
              {{ getProgressModel(submission.verdict).text }}
              <IconLoadingShadow />
            </div>
            <tiny-progress
              style="margin: 10px"
              :stroke-width="6"
              :percentage="getProgressModel(submission.verdict).percentage"
              :color="progressColors"
              :show-text="false"
            />
            <!-- todo 渐变效果，不要直接消失进度条 -->
          </div>
        </div>
        <!-- 最终状态，显示tag -->
        <div
          v-else-if="submission.verdict !== undefined"
          style="flex: 1; display: flex; flex-direction: column"
        >
          <div style="margin-left: auto; font-size: 15px; font-weight: 600; margin-bottom: 5px">
            <verdict-div v-model:verdict="submission.verdict" />
          </div>
          <div style="margin-left: auto; color: gray">
            {{ submission.runTime + 'ms • ' + submission.runMemory + 'KB' }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { SubmissionControllerService, type SubmissionListVO, type SubmissionVerdictVO } from '@/api'
import VerdictDiv from '@/components/VerdictDiv.vue'
import { LanguageList, VerdictList } from '@/common/CommonConstant'
import { iconConmentRefresh, iconLoadingShadow } from '@opentiny/vue-icon'
import { onMounted, ref } from 'vue'
import { getLanguageByShortName, fromNow } from '@/utils'
import { TinyLoading } from '@opentiny/vue'
const vLoading = TinyLoading.directive
const IconConmentRefresh = iconConmentRefresh()
const IconLoadingShadow = iconLoadingShadow()
const problemId = defineModel<number>('problemId')
const progressValue = ref(60)
const progressColors = ref([
  { color: '#1376FF', percentage: 21 },
  { color: '#FF8800', percentage: 41 },
  { color: '#5CB300', percentage: 61 },
  { color: '#6E51E0', percentage: 81 },
  { color: '#191919', percentage: 101 }
])
const getProgressModel = (verdict: number) => {
  switch (verdict) {
    case 1:
      return {
        text: 'In Queue',
        percentage: 20,
        color: '#1376FF'
      }
    case 100:
      return {
        text: 'Compiling',
        percentage: 40,
        color: '#FF8800'
      }
    case 200:
      return {
        text: 'Running',
        percentage: 60,
        color: '#5CB300'
      }
    case 201:
      return {
        text: 'Judging',
        percentage: 80,
        color: '#6E51E0'
      }
    default:
      return {
        text: '',
        percentage: 100,
        color: '#191919'
      }
  }
}
// const getSubmissionVerdict = async () => {
//   // 目前认为60秒内一定会有结果，只查询60秒
//   for (let i = 1; i < 60; i++) {
//     // 一秒获取一次
//     await new Promise((resolve) => setTimeout(resolve, 1000))
//     const { code, data } = await SubmissionControllerService.getSubmissionVerdictUsingGet(
//       submissionId.value as number
//     )
//     if (code) return
//     const currentVerdict = data?.verdict
//     switch (currentVerdict) {
//       case 1:
//         // in queue
//         progressValue.value = 20
//         break
//       case 100:
//         // compiling
//         progressValue.value = 40
//         break
//       case 200:
//         // running
//         progressValue.value = 60
//         break
//       case 201:
//         // judging
//         progressValue.value = 80
//         break
//       default:
//         submissionId.value = 0
//         break
//     }
//     if (submissionId.value) submissionVerdict.value = data
//   }
// }
const currentSize = ref(10)
const currentPage = ref(1)
const submissionList = ref<SubmissionListVO[]>()
const submissonVerdictQuery = ref({
  onlyMine: true,
  verdict: undefined,
  language: undefined
  // todo 无限滚动
})
const isLoading = ref(false)
const getSubmissionList = async () => {
  isLoading.value = true
  try {
    const { code, data } = await SubmissionControllerService.listSubmissionByPageUsingGet(
      currentPage.value,
      currentSize.value,
      submissonVerdictQuery.value.language,
      submissonVerdictQuery.value.onlyMine,
      undefined,
      undefined,
      undefined,
      undefined,
      submissonVerdictQuery.value.verdict
    )
    if (code) return
    submissionList.value = data?.records
  } finally {
    isLoading.value = false
  }
}
onMounted(async () => {
  await getSubmissionList()
})
</script>

<style scoped>
.submisson-box {
  border: 1px solid #c4c4c4;
  border-radius: 6px;
  margin-top: 10px;
  height: 55px;
  padding: 5px 5px 0 5px;
}
</style>
