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
        <tiny-button style="margin-right: 20px" size="small" @click="goToHome">返回首页</tiny-button>
        <user-button style="margin-right: 20px" />
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
              <code-editor
                @update:language="handleLanguageUpdate"
                :code="problemSubmitDTO.code"
                :handleChange="handleCodeChange"
              />
            </template>
          </tiny-split>
        </template>
        <template #right>
          <div style="height: calc(100% - 90px); overflow-y: auto">
            <tiny-tabs v-model="activeTab" size="small" class="tabClass">
              <tiny-tab-item name="editCase" title="测试用例" style="margin: 0 20px">
                <tiny-tabs
                  v-model="activeSubTab"
                  tab-style="button-card"
                  :with-close="true"
                  :before-close="true"
                  @close="handleDeleteCase"
                >
                  <tiny-tab-item
                    v-for="(item, index) in testCases"
                    :key="index"
                    :title="`样例 #` + (index + 1)"
                    :name="String(index + 1)"
                  >
                    <tiny-row>
                      <tiny-tag style="margin: 5px 0" effect="dark">样例输入</tiny-tag>
                      <tiny-input
                        type="textarea"
                        v-model="item.input"
                        resize="none"
                        :autosize="{ minRows: 2, maxRows: 10 }"
                      />
                    </tiny-row>
                    <tiny-row>
                      <tiny-tag style="margin: 5px 0" effect="dark">预计输出</tiny-tag>

                      <tiny-input
                        type="textarea"
                        v-model="item.output"
                        resize="none"
                        :autosize="{ minRows: 2, maxRows: 10 }"
                      />
                    </tiny-row>
                    <tiny-row>
                      <tiny-tag style="margin: 5px 0" effect="dark">实际输出</tiny-tag>
                      <tiny-input
                        type="textarea"
                        v-model="item.input"
                        resize="none"
                        :autosize="{ minRows: 2, maxRows: 10 }"
                      />
                    </tiny-row>
                  </tiny-tab-item>
                  <tiny-tab-item
                    class="add-tab-item"
                    title="添加样例"
                    name="0"
                    :with-close="false"
                  />
                </tiny-tabs>
              </tiny-tab-item>
              <tiny-tab-item name="debugCase" title="评测结果" style="margin: 0 20px"
                >123</tiny-tab-item
              >
            </tiny-tabs>
          </div>
          <div style="height: 90px; border-top: 1px solid #ebeef5; padding: 10px">
            <tiny-row :flex="true" align="middle" style="margin-bottom: 10px;">
              <tiny-col :span="7">
                <tiny-button style="width: 100%;">测试样例 #{{ activeSubTab }}</tiny-button>
              </tiny-col>
              <tiny-col :span="5">
                <tiny-button style="width: 100%;">测试全部</tiny-button>
              </tiny-col>
            </tiny-row>
            <tiny-row :flex="true" align="middle">
              <tiny-col :span="12">
                <tiny-button type="primary" style="width: 100%;">提交评测</tiny-button>
              </tiny-col>
            </tiny-row>
          </div>
        </template>
      </tiny-split>

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
              <div style="height: calc(100% - 50px); overflow-y: auto">
                <tiny-tabs v-model="activeTab" size="small" class="tabClass">
                  <tiny-tab-item name="editCase" title="测试用例" style="margin: 0 20px">
                    <tiny-tabs
                      v-model="activeSubTab"
                      tab-style="button-card"
                      :with-close="true"
                      :before-close="true"
                      @close="handleDeleteCase"
                    >
                      <tiny-tab-item
                        v-for="(item, index) in testCases"
                        :key="index"
                        :title="`样例 #` + (index + 1)"
                        :name="String(index + 1)"
                      >
                        <tiny-row>
                          <tiny-col :lg="4" :md="12">
                            <tiny-tag style="margin-bottom: 5px" effect="dark">样例输入</tiny-tag>
                            <tiny-input
                              type="textarea"
                              v-model="item.input"
                              resize="none"
                              :autosize="{ minRows: 2, maxRows: 10 }"
                            />
                          </tiny-col>
                          <tiny-col :lg="4" :md="12">
                            <tiny-tag style="margin-bottom: 5px" effect="dark">预计输出</tiny-tag>

                            <tiny-input
                              type="textarea"
                              v-model="item.output"
                              resize="none"
                              :autosize="{ minRows: 2, maxRows: 10 }"
                            />
                          </tiny-col>
                          <tiny-col :lg="4" :md="12">
                            <tiny-tag style="margin-bottom: 5px" effect="dark">实际输出</tiny-tag>
                            <tiny-input
                              type="textarea"
                              v-model="item.input"
                              resize="none"
                              :autosize="{ minRows: 2, maxRows: 10 }"
                            />
                          </tiny-col>
                        </tiny-row>
                      </tiny-tab-item>
                      <tiny-tab-item
                        class="add-tab-item"
                        title="添加样例"
                        name="0"
                        :with-close="false"
                      />
                    </tiny-tabs>
                  </tiny-tab-item>
                  <tiny-tab-item name="debugCase" title="评测结果" style="margin: 0 20px"
                    >123</tiny-tab-item
                  >
                </tiny-tabs>
              </div>
              <div style="height: 50px; border-top: 1px solid #ebeef5; padding: 10px">
                <tiny-row :flex="true" align="middle">
                  <tiny-col>
                    <tiny-button>测试样例 #{{ activeSubTab }}</tiny-button>
                    <tiny-button>测试全部</tiny-button>
                    <tiny-button type="primary">提交评测</tiny-button>
                  </tiny-col>
                </tiny-row>
              </div>
            </template>
          </tiny-split>
        </template>
      </tiny-split>
    </div>
  </div>
</template>

<script setup lang="ts">
import ProblemInfo from './ProblemInfo.vue'
import { ProblemControllerService, type ProblemDetailVO, type SubmissionDTO } from '@/api'
import { onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { iconPushpin } from '@opentiny/vue-icon'
import { TinyModal, TinyNotify } from '@opentiny/vue'
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
// todo 最多10个测试用例
const handleAddCase = () => {
  testCases.value.push({
    input: '1',
    output: '2'
  })
}
const handleDeleteCase = (index: string) => {
  if (index !== '0') {
    testCases.value.splice(Number(index) - 1, 1)
  } else {
    handleAddCase()
  }
}
const splitLayout = ref('fashion')

const splitFashion1 = ref(0.8)
const splitFashion2 = ref(0.45)

const splitClassic1 = ref(0.49)
const splitClassic2 = ref(0.7)

const activeTab = ref('editCase')
const activeSubTab = ref<String>('1')

const testCases = ref<any>([])
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
onMounted(async () => {
  // 获取problem/:id的id
  const problemDisplayId = route.params.id
  const { code, data } = await ProblemControllerService.getProblemDetailVoUsingGet(
    problemDisplayId as string
  )
  if (code) return
  problemDetail.value = data as ProblemDetailVO
  try {
    testCases.value = JSON.parse(problemDetail.value.examples as string)
  } catch (e) {
    TinyModal.message({ message: '测试样例出现了问题，请自行填充数据', status: 'error' })
    testCases.value = []
    problemDetail.value.examples = ''
  }
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

:deep(.tabClass > .tiny-tabs__header .tiny-tabs__nav) {
  margin-left: 20px;
}
:deep(#tab-0 > .tiny-tabs__icon-close) {
  transform: rotate(45deg);
}

:deep(.tiny-textarea) {
  --tv-Textarea-border-radius: 0;
}
:deep(.tiny-tabs.tiny-tabs--button-card .tiny-tabs__item__title) {
  padding: 0 10px;
}
:deep(.tiny-tabs.tiny-tabs--button-card .tiny-tabs__item .tiny-tabs__icon-close) {
  margin-left: -5px;
  margin-right: 10px;
}
</style>
