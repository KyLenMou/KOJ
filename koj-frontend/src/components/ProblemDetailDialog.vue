<template>
  <tiny-dialog-box title="提交详情" v-model:visible="showDetailDialog" width="70%" top="50">
    <template #default>
      <div class="submission-description">
        <div style="display: flex">
          <div>
            <span class="oj-card-subtitle"
              >{{ submissionDetail?.problemDisplayId + '. ' + submissionDetail?.problemTitle }}
            </span>
            <span> {{ ' #' + submissionDetail?.problemId }}</span>
          </div>
        </div>
        <div style="display: flex; gap: 20px; margin-top: 5px; margin-bottom: 5px">
          <div>
            <span>作者：</span>
            {{ submissionDetail?.username }}
          </div>
          <div>
            <span>提交ID：</span>
            {{ submissionDetail?.submissionId }}
          </div>
          <div>
            <span>提交时间：</span>
            {{ formatTime(submissionDetail?.date as string) }}
          </div>
        </div>
        <div style="display: flex; gap: 20px">
          <div>
            <span>代码语言：</span>
            {{ getLanguageByShortName(submissionDetail?.language) }}
          </div>
          <div>
            <span>运行时间：</span>
            {{ submissionDetail?.runTime + 'ms' }}
          </div>
          <div>
            <span>运行内存：</span>
            {{ submissionDetail?.runMemory + 'KB' }}
          </div>
          <div>
            <span>评测状态：</span>
            <verdict-tag :verdict="submissionDetail?.verdict as number" />
          </div>
        </div>
      </div>
      <div style="display: flex; gap: 20px">
        <div class="common-box" style="width: 30%">
          <span class="oj-card-subtitle">测试用例</span>
          <tiny-row :gutter="10">
            <tiny-col
              v-for="(testcase, index) in submissionDetail?.submissionCaseList"
              :key="index"
              :xl="3"
              :lg="4"
              :md="6"
              :xs="12"
            >
              <single-test-case
                :judgeOutput="testcase.judgeOutput"
                :verdict="testcase.verdict"
                :time="testcase.time"
                :memory="testcase.memory"
                :id="index + 1"
              />
            </tiny-col>
          </tiny-row>
        </div>
        <div class="common-box" style="width: 70%">
          <span class="oj-card-subtitle">代码</span>
          <pre>{{ submissionDetail?.code }}</pre>
        </div>
      </div>
    </template>
  </tiny-dialog-box>
</template>

<script setup lang="ts">
import SingleTestCase from './SingleTestCase.vue'
import type { SubmissionDetailVO } from '@/api'
import VerdictTag from './VerdictTag.vue'
import { formatTime, getLanguageByShortName } from '@/utils'

const showDetailDialog = defineModel<boolean>('showDetailDialog')
const submissionDetail = defineModel<SubmissionDetailVO>('submissionDetail')
</script>

<style scoped>
.submission-description {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
.common-box {
  padding: 10px;
  border: 1px solid #c4c4c4;
  border-radius: 6px;
  margin-bottom: 20px;
}
</style>
