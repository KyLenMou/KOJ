<template>
  <div v-loading="isLoading" tiny-loading__background="rgba(0,0,0,0.2)">
    <!-- 表单栏 -->
    <div style="display: flex; gap: 10px">
      <!-- 编程语言 -->
      <tiny-base-select
        v-model="submissonVerdictQuery.language"
        clearable
        placeholder="语言"
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
      <!-- 只看我的 -->
      <tiny-checkbox-button
        v-model="submissonVerdictQuery.onlyMine"
        label="只看我的"
        style="margin: 0"
      />
      <!-- 刷新 -->
      <tiny-button :icon="IconConmentRefresh" @click="getSubmissionList" />
    </div>
    <!-- 提交列表栏 -->
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
          v-if="submission.verdict !== undefined && isSubmissionRunning(submission.verdict)"
          style="flex: 1"
        >
          <div style="font-size: 15px; font-weight: 600; margin: 5px; text-align: center">
            <div
              style="display: flex; justify-content: center; gap: 5px"
              :style="
                'color:' +
                getVerdictProgressModel(submission.verdict).color +
                '; fill: ' +
                getVerdictProgressModel(submission.verdict).color
              "
            >
              {{ getVerdictProgressModel(submission.verdict).text }}
              <IconLoadingShadow />
            </div>
            <tiny-progress
              style="margin: 10px"
              :stroke-width="6"
              :percentage="getVerdictProgressModel(submission.verdict).percentage"
              :color="VerdictProgressColors"
              :show-text="false"
            />
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
import { LanguageList, VerdictList, VerdictProgressColors } from '@/common/CommonConstant'
import { iconConmentRefresh, iconLoadingShadow } from '@opentiny/vue-icon'
import { onMounted, ref, watch } from 'vue'
import {
  getLanguageByShortName,
  fromNow,
  getVerdictProgressModel,
  isSubmissionRunning
} from '@/utils'
import { TinyLoading } from '@opentiny/vue'
const vLoading = TinyLoading.directive
const IconConmentRefresh = iconConmentRefresh()
const IconLoadingShadow = iconLoadingShadow()
const problemId = defineModel<number>('problemId')
const currentSize = ref(10)
const currentPage = ref(1)
const submissionList = ref<SubmissionListVO[] | any>()
const submissonVerdictQuery = ref({
  onlyMine: true,
  verdict: undefined,
  language: undefined
  // todo 无限滚动
})
const isLoading = ref(false)
const runningSubmissionList = ref<number[]>([])
let isRunning = false
let askTimes = 30
const getSubmissionList = async () => {
  isLoading.value = true
  try {
    const { code, data } = await SubmissionControllerService.listSubmissionByPageUsingGet(
      currentPage.value,
      currentSize.value,
      submissonVerdictQuery.value.language,
      submissonVerdictQuery.value.onlyMine,
      undefined,
      problemId.value,
      undefined,
      undefined,
      submissonVerdictQuery.value.verdict
    )
    if (code) return
    submissionList.value = data?.records
    for (const submission of submissionList.value) {
      if (isSubmissionRunning(submission.verdict)) {
        runningSubmissionList.value.push(submission.id as number)
      }
    }
    if (runningSubmissionList.value.length) {
      if (!isRunning) {
        getRunningSubmissionVerdict()
      } else {
        askTimes = 30
      }
    }
  } finally {
    isLoading.value = false
  }
}
const getRunningSubmissionVerdict = async () => {
  isRunning = true
  try {
    while (askTimes-- > 0) {
      // 如果询问列表空了，就不用询问了
      if (!runningSubmissionList.value.length) return
      // 2秒获取一次
      await new Promise((resolve) => setTimeout(resolve, 2000))
      const { code, data } = await SubmissionControllerService.getSubmissionVerdictListUsingGet(
        runningSubmissionList.value
      )
      if (code || !data) return
      let tempList = [] as number[]
      for (const resultSubmission of data) {
        // 获取resultSubmission的submissionId
        const submissionId = resultSubmission.submissionId as number
        // 更新submissionList中的submission的状态为resultSubmission的状态
        for (const submission of submissionList.value) {
          if (submission.id === submissionId) {
            submission.verdict = resultSubmission.verdict
            submission.runTime = resultSubmission.runTime
            submission.runMemory = resultSubmission.runMemory
            break
          }
        }
        // 如果resultSubmission的状态是running状态，继续询问了
        if (isSubmissionRunning(resultSubmission.verdict)) {
          tempList.push(submissionId)
        }
      }
      runningSubmissionList.value = tempList
    }
  } finally {
    isRunning = false
  }
}
onMounted(async () => {
  await getSubmissionList()
})
// 暴露方法给父组件
defineExpose({
  getSubmissionList
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
:deep(.tiny-loading) {
  border-radius: 6px !important;
}
:deep(.tiny-checkbox-button__inner) {
  padding: 0 10px;
}
</style>
