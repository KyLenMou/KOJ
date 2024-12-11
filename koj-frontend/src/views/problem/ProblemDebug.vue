<template>
  <div ref="tabsContainer" style="height: calc(100% - 50px)">
    <tiny-tabs v-model="activeTab" size="small" class="tabClass">
      <!-- 被选中的tab需要高亮 -->
      <tiny-tab-item
        ref="debugContainer"
        name="editCase"
        title="测试用例"
        :style="`height:` + subTabHeight + `px`"
        style="margin-left: 20px; padding-right: 20px; overflow-y: auto"
      >
        <!-- testCase栏 -->
        <div class="test-case-tabs">
          <div v-for="(item, index) in testCases" :key="index" style="position: relative">
            <!-- 每个测试用例tab -->
            <tiny-button
              :type="getVerdictModel(item.verdict)?.type"
              :plain="item.verdict === 0"
              @click="switchTab(index)"
              :loading="item.isLoading"
            >
              <div v-if="item.isLoading">Running</div>
              <div v-else>
                <div
                  v-if="getVerdictModel(item.verdict)"
                  style="display: flex; align-items: center; gap: 3px"
                >
                  <img
                    :src="`/src/assets/images/` + getVerdictModel(item.verdict)?.short + `.svg`"
                  />
                  {{ getVerdictModel(item.verdict)?.short }}
                </div>
                <div v-else>{{ `样例 #` + (index + 1) }}</div>
              </div>
            </tiny-button>
            <!-- tab关闭小按钮 -->
            <div
              v-if="item.verdict === 0"
              class="tab-close-button"
              @click.stop="closeTestCaseTab(index)"
            >
              <img src="@/assets/images/closetab.svg" />
            </div>
            <div v-else class="tab-refresh-button" @click.stop="refreshTestCaseTab(index)">
              <img src="@/assets/images/refresh.svg" />
            </div>
          </div>
          <!-- 添加testcase按钮 -->
          <tiny-button :icon="IconPlus" type="text" @click="addCase" />
        </div>
        <!-- debug数据栏 -->
        <template v-for="(item, index) in testCases" :key="index">
          <div v-show="index === activeSubTab" class="debug-container is-error">
            <tiny-alert
              v-if="getVerdictModel(item.verdict) && item.isLoading === false"
              size="large"
              :type="
                getVerdictModel(item.verdict)?.type === 'danger'
                  ? 'error'
                  : getVerdictModel(item.verdict)?.type === 'primary'
                    ? 'simple'
                    : getVerdictModel(item.verdict)?.type
              "
              style="width: 100%"
            >
              <template #title>{{ getVerdictModel(item.verdict)?.full }}</template>
              <template #description>
                <tiny-tag
                  :type="getVerdictModel(item.verdict)?.type"
                  effect="light"
                  style="padding: 0"
                  ><IconTime style="font-size: 16px" />
                  <span>运行时间 {{ item.runTime }} ms</span></tiny-tag
                >
                <tiny-tag :type="getVerdictModel(item.verdict)?.type" effect="light"
                  ><IconCode style="font-size: 16px" />
                  <span>运行内存 {{ item.runMemory }} KB</span></tiny-tag
                >
                <div>{{ getVerdictModel(item.verdict)?.description }}</div>
                <div
                  style="
                    white-space: pre-wrap;
                    font-weight: 600;
                    font-family: Consolas, Courier, monospace;
                  "
                >
                  {{ item.message }}
                </div>
              </template>
            </tiny-alert>
            <div class="debug-input">
              <tiny-tag style="margin-bottom: 5px" effect="plain">{{
                `样例 #` + (index + 1) + ` 输入`
              }}</tiny-tag>
              <tiny-input
                type="textarea"
                :disabled="item.isLoading"
                v-model="item.userInput"
                resize="none"
                :maxlength="1024"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 10 }"
              />
            </div>
            <div class="debug-input">
              <tiny-tag style="margin-bottom: 5px" effect="plain">{{
                `样例 #` + (index + 1) + ` 输出`
              }}</tiny-tag>

              <tiny-input
                type="textarea"
                :disabled="item.isLoading"
                v-model="item.expectedOutput"
                resize="none"
                :maxlength="1024"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 10 }"
              />
            </div>
            <div class="debug-input">
              <tiny-tag style="margin-bottom: 5px" effect="plain">{{
                `样例 #` + (index + 1) + ` 实际输出`
              }}</tiny-tag>
              <tiny-input
                :class="(item.verdict >= 400 && item.verdict <= 405) ? 'is-error' : ''"
                type="textarea"
                :disabled="item.isLoading"
                v-model="item.userOutput"
                resize="none"
                :maxlength="1024"
                show-word-limit
                :autosize="{ minRows: 2, maxRows: 10 }"
              />
            </div>
          </div>
        </template>
      </tiny-tab-item>
      <tiny-tab-item
        name="submissionList"
        title="提交记录"
        :style="`height:` + subTabHeight + `px`"
        style="margin-left: 20px; padding-right: 20px; overflow-y: auto"
        lazy
      >
        <problem-submisson-list ref="submissionListRef" v-model:problem-id="problemDetail.id" />
      </tiny-tab-item>
      <tiny-tab-item
        name="discussion"
        title="讨论区"
        style="margin: 0 20px"
        :style="`height:` + subTabHeight + `px`"
        lazy
      >
        帖子
      </tiny-tab-item>
    </tiny-tabs>
  </div>
  <div style="height: 50px; border-top: 1px solid #ebeef5; padding: 10px 0 10px 20px">
    <div style="display: flex">
      <tiny-button
        @click="debug([activeSubTab] as number[])"
        :disabled="isButtonLoading"
        type="success"
        ghost
        >测试样例 #{{ activeSubTab + 1 }}</tiny-button
      >
      <tiny-button @click="debugAll" :disabled="isButtonLoading" type="success">
        <img src="@/assets/images/debug.svg" />测试全部</tiny-button
      >
      <tiny-button type="primary" @click="submit" :disabled="isButtonLoading">
        <img src="@/assets/images/submit.svg" />
        提交评测</tiny-button
      >
    </div>
  </div>
</template>

<script setup lang="ts">
import { SubmitControllerService, type DebugDTO } from '@/api'
import ProblemSubmissonList from './ProblemSubmissonList.vue'
import { getVerdictModel } from '@/utils'
import { TinyModal } from '@opentiny/vue'
import { iconPlus, iconTime, iconCode } from '@opentiny/vue-icon'
import { onMounted, onUnmounted, ref, watch } from 'vue'
const IconPlus = iconPlus()
const IconTime = iconTime()
const IconCode = iconCode()
const isButtonLoading = ref(false)
const activeTab = ref('editCase')
const activeSubTab = ref<number>(0)
const problemSubmitDTO = defineModel<any>('problemSubmitDTO')
const problemDetail = defineModel<any>('problemDetail')
const testCases = ref<any>([])
// 初始化测试用例
try {
  const problemTestCaseTemp = JSON.parse(problemDetail.value.examples as string)
  console.log(problemTestCaseTemp)
  problemTestCaseTemp.forEach((item: any) => {
    testCases.value.push({
      userInput: item.input,
      expectedOutput: item.output,
      userOutput: '',
      isLoading: false,
      message: '',
      runTime: 0,
      runMemory: 0,
      verdict: 0
    })
  })
} catch (e) {
  TinyModal.message({ message: '测试样例出现了问题，请自行填充数据', status: 'error' })
  testCases.value = []
}
// 测试全部
const debugAll = async () => {
  const ids: number[] = []
  for (let i = 0; i < testCases.value.length; i++) {
    ids.push(i)
  }
  debug(ids)
}
// 测试列表中的测试用例
const debug = async (ids: number[]) => {
  if (problemSubmitDTO.value.code.length === 0) {
    TinyModal.message({ message: '请编写代码后再测试', status: 'warning' })
    return
  }
  if (testCases.value.length === 0 || ids.length === 0) {
    TinyModal.message({ message: '请选择测试用例', status: 'warning' })
    return
  }
  isButtonLoading.value = true
  const debugDTO: DebugDTO = {
    expectedOutputList: [],
    userInputList: [],
    code: problemSubmitDTO.value.code,
    judgeMode: problemDetail.value.judgeMode,
    language: problemSubmitDTO.value.language,
    memoryLimit: problemDetail.value.memoryLimit,
    stackLimit: problemDetail.value.stackLimit,
    timeLimit: problemDetail.value.timeLimit
  }
  ids.forEach((id: number) => {
    debugDTO.userInputList?.push(testCases.value[id].userInput)
    debugDTO.expectedOutputList?.push(testCases.value[id].expectedOutput)
  })

  const { code, data, message } = await SubmitControllerService.debugUsingPost(debugDTO)
  if (code) {
    if (message.length === 0)
      TinyModal.message({ message: '获取评测结果失败，请稍后重试', status: 'error' })
    isButtonLoading.value = false
    return
  }
  try {
    await getDebugResult(data, ids)
  } finally {
    isButtonLoading.value = false
  }
}
// 获取Debug结果
const getDebugResult = async (debugId: string, ids: number[]) => {
  // 开始等待结果，测试点显示loading
  ids.forEach((id: number) => {
    testCases.value[id].isLoading = true
  })
  for (let i = 1; i < 60; i++) {
    // 每隔1s获取一次结果
    await new Promise((resolve) => setTimeout(resolve, 1000))
    const { code, data } = await SubmitControllerService.getDebugResultUsingGet(debugId)
    if (code) break
    if (data?.debugId !== debugId && data) {
      // 评测完成
      let idx = 0
      // 更新测试点信息
      ids.forEach((id: number) => {
        testCases.value[id].isLoading = false
        testCases.value[id].verdict = data.verdict?.[idx]
        testCases.value[id].runTime = data.runTime?.[idx]
        testCases.value[id].runMemory = data.runMemory?.[idx]
        testCases.value[id].message = data.message?.[idx]
        testCases.value[id].userInput = data.userInputList?.[idx]
        testCases.value[id].userOutput = data.userOutputList?.[idx]
        testCases.value[id].expectedOutput = data.expectedOutputList?.[idx]
        idx++
      })
      break
    }
  }
  ids.forEach((id: number) => {
    testCases.value[id].isLoading = false
  })
}

// 子组件的获取评测的方法
const submissionListRef = ref<any>([])

// 提交代码
const submit = async () => {
  const { code, data } = await SubmitControllerService.submitUsingPost(problemSubmitDTO.value)
  if (code) return
  // 跳转到提交记录
  activeTab.value = 'submissionList'
  // 刷新提交记录
  submissionListRef.value.getSubmissionList(true)
}
// 切换测试用例
const switchTab = (index: any) => {
  if (index === activeSubTab.value) return
  else activeSubTab.value = index
}
// 关闭一个测试用例
const closeTestCaseTab = (index: number) => {
  testCases.value.splice(index, 1)
}
// 清空一个测试用例的状态
const refreshTestCaseTab = (index: number) => {
  testCases.value[index].verdict = 0
}
// 添加测试用例
const addCase = () => {
  if (testCases.value.length >= 5) {
    // todo 后端校验
    TinyModal.message({ message: '最多同时运行5个测试用例', status: 'warning' })
    return
  }
  testCases.value.push({
    userInput: '',
    expectedOutput: '',
    userOutput: '',
    isLoading: false,
    message: '',
    runTime: 0,
    runMemory: 0,
    verdict: 0
  })
}
// 限制子tab的高度
const tabsContainer = ref()
const subTabHeight = ref(0)
onMounted(() => {
  const observer = new ResizeObserver(() => {
    if (tabsContainer.value) {
      subTabHeight.value = tabsContainer.value.offsetHeight - 52
    }
  })

  if (tabsContainer.value) {
    observer.observe(tabsContainer.value)
  }
  // 清理监听器
  onUnmounted(() => {
    if (tabsContainer.value) {
      observer.unobserve(tabsContainer.value)
    }
  })
})
</script>

<style scoped>
.debug-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  container-type: inline-size;
}

.debug-input {
  flex: 1 1 calc(33.333% - 10px);
  box-sizing: border-box;
}
:deep(.is-error > .tiny-textarea-display-only > .tiny-textarea__inner) {
  border: 1px solid #f23030;
  box-shadow: 0 1px 4px 0 #f23030;
}

@container (max-width: 600px) {
  .debug-input {
    flex: 1 1 100%;
  }
}

.tab-close-button {
  position: absolute;
  top: -5px;
  right: -5px;
  background: gray;
  border-radius: 50%;
  width: 14px;
  height: 14px;
  display: none;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.tab-refresh-button {
  position: absolute;
  top: -5px;
  right: -5px;
  background: black;
  border-radius: 50%;
  width: 16px;
  height: 16px;
  display: none;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

div:hover > .tab-close-button {
  display: flex;
}
div:hover > .tab-refresh-button {
  display: flex;
}
.tab-close-button:hover {
  background: gainsboro;
}
.tab-refresh-button:hover {
  background: gray;
}
.test-case-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
  margin-top: 5px;
  overflow-x: auto;
  padding: 5px 0;
}

.test-case-tabs::-webkit-scrollbar {
  height: 3px;
}
:deep(.tiny-alert__content) {
  width: 100%;
}
</style>
