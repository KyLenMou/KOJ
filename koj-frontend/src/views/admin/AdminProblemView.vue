<template>
  <div>
    <tiny-form ref="ruleFormRef" :model="problemForm" label-position="top">
      <tiny-form-item>
        <template #label>
          <div style="display: flex">
            <div class="card-title">编辑题目</div>
            <tiny-popconfirm
              title="当前操作不会保存，确认返回？"
              type="warning"
              trigger="click"
              @confirm="backToProblemset"
              style="margin-left: auto"
            >
              <template #reference>
                <tiny-button>返回列表</tiny-button>
              </template>
            </tiny-popconfirm>
            <tiny-button @click="submitEdit" type="primary" style="margin-left: 10px"
              >完成编辑</tiny-button
            >
          </div>
        </template>
      </tiny-form-item>
      <tiny-row>
        <tiny-col :lg="2" :md="3">
          <tiny-form-item label="展示ID" prop="displayId">
            <tiny-input v-model="problemForm.problem.displayId" placeholder="题目展示ID" />
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="3">
          <tiny-form-item label="标题" prop="title">
            <tiny-input v-model="problemForm.problem.title" placeholder="题目标题" />
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="3">
          <tiny-form-item label="标签" prop="tags">
            <tiny-select v-model="problemForm.tagIds" :show-alloption="false" multiple filterable>
              <tiny-option
                v-for="option in tagOptions"
                :key="option.value"
                :label="option.label"
                :value="option.value"
              />
            </tiny-select>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="1" :md="2">
          <tiny-form-item label="是否可见" prop="visible">
            <tiny-switch v-model="problemForm.problem.visible" :true-value="1" :false-value="0">
            </tiny-switch>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="1" :md="2">
          <tiny-form-item label="开放评测结果" prop="openCaseResult">
            <tiny-switch
              v-model="problemForm.problem.openCaseResult"
              :true-value="1"
              :false-value="0"
            >
            </tiny-switch>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="1" :md="2">
          <tiny-form-item label="去除行末换行符" prop="isRemoveEndBlank">
            <tiny-switch
              v-model="problemForm.problem.isRemoveEndBlank"
              :true-value="1"
              :false-value="0"
            >
            </tiny-switch>
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <tiny-row>
        <tiny-col :lg="2" :md="4">
          <tiny-form-item label="时间限制(ms)" prop="timeLimit">
            <tiny-numeric v-model="problemForm.problem.timeLimit" :step="100"></tiny-numeric>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="4">
          <tiny-form-item label="内存限制(MB)" prop="memoryLimit">
            <tiny-numeric v-model="problemForm.problem.memoryLimit" :step="100"></tiny-numeric>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="4">
          <tiny-form-item label="栈限制(MB)" prop="stackLimit">
            <tiny-numeric v-model="problemForm.problem.stackLimit" :step="100"></tiny-numeric>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="4">
          <tiny-form-item label="难度" prop="difficulty">
            <tiny-numeric v-model="problemForm.problem.difficulty" :step="100"></tiny-numeric>
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <tiny-form-item>
        <template #label>
          <div class="card-title">编辑题面</div>
        </template>
      </tiny-form-item>
      <tiny-row>
        <tiny-col :span="12">
          <tiny-form-item>
            <tiny-tabs v-model="tabActiveName">
              <tiny-tab-item title="题目描述" name="1">
                <md-editor v-model="problemForm.problem.description" />
              </tiny-tab-item>
              <tiny-tab-item title="输入描述" name="2">
                <md-editor v-model="problemForm.problem.input" />
              </tiny-tab-item>
              <tiny-tab-item title="输出描述" name="3">
                <md-editor v-model="problemForm.problem.output" />
              </tiny-tab-item>
              <tiny-tab-item title="提示" name="4">
                <md-editor v-model="problemForm.problem.note" />
              </tiny-tab-item>
              <tiny-tab-item title="样例" name="5">
                <div v-for="(obj, index) in exampleJsonObjects" :key="index">
                  <tiny-tag type="info" closable @close="deleteExampleJson(index)">{{
                    `样例` + (index + 1)
                  }}</tiny-tag>
                  <tiny-split style="height: 200px; width: 100%" trigger-simple>
                    <template #left>
                      <tiny-input
                        type="textarea"
                        v-model="obj.input"
                        :placeholder="`请输入样例` + (index + 1) + `的输入`"
                        resize="none"
                      />
                    </template>
                    <template #right>
                      <tiny-input
                        type="textarea"
                        v-model="obj.output"
                        :placeholder="`请输入样例` + (index + 1) + `的输出`"
                        resize="none"
                      />
                    </template>
                  </tiny-split>
                </div>

                <tiny-button
                  type="success"
                  plain
                  :reset-time="0"
                  style="border-radius: 6px; width: 100%; margin-top: 10px"
                  @click="addExampleJson"
                  >添加样例</tiny-button
                >
              </tiny-tab-item>
            </tiny-tabs>
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <tiny-form-item>
        <template #label>
          <div class="card-title">编辑评测</div>
        </template>
      </tiny-form-item>
      <tiny-row>
        <tiny-col :lg="2" :md="3">
          <tiny-form-item label="评测模式">
            <tiny-select v-model="problemForm.problem.judgeMode">
              <tiny-option label="默认评测" value="default" />
              <tiny-option label="特殊评测" value="spj" />
              <tiny-option label="交互评测" value="interact" />
            </tiny-select>
          </tiny-form-item>
        </tiny-col>
        <tiny-col v-if="problemForm.problem.judgeMode !== 'default'" :lg="2" :md="3">
          <tiny-form-item label="特殊/交互评测语言">
            <tiny-select v-model="problemForm.problem.spjLanguage">
              <tiny-option label="C++" value="cpp" />
            </tiny-select>
          </tiny-form-item>
        </tiny-col>
        <tiny-col :lg="2" :md="3">
          <tiny-form-item label="是否上传文件">
            <tiny-switch v-model="problemForm.isUploadCase" :true-value="1" :false-value="0" />
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <tiny-row v-if="problemForm.problem.judgeMode !== 'default'">
        <tiny-col>
          <tiny-form-item label="特殊/交互评测代码">
            <tiny-input
              type="textarea"
              v-model="problemForm.problem.spjCode"
              placeholder="请输入特殊/交互评测代码"
              resize="none"
            />
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <tiny-row>
        <tiny-col :span="12">
          <tiny-form-item v-for="(testCase, index) in problemForm.testCases" :key="index">
            <template #label>
              <tiny-tag type="info" closable @close="deleteTestCase(index)">{{
                `测试用例` + (index + 1)
              }}</tiny-tag>
            </template>
            <tiny-split style="height: 200px; width: 100%" trigger-simple>
              <template #left>
                <tiny-input
                  type="textarea"
                  v-model="testCase.input"
                  :placeholder="`请输入测试用例` + (index + 1) + `的输入`"
                  resize="none"
                />
              </template>
              <template #right>
                <tiny-input
                  type="textarea"
                  v-model="testCase.output"
                  :placeholder="`请输入测试用例` + (index + 1) + `的输出`"
                  resize="none"
                />
              </template>
            </tiny-split>
          </tiny-form-item>
          <tiny-form-item>
            <tiny-button
              type="success"
              plain
              :reset-time="0"
              style="border-radius: 6px; width: 100%"
              @click="addTestCase"
              >添加测试用例</tiny-button
            >
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
    </tiny-form>
  </div>
</template>

<script setup lang="ts">
import { AdminTagControllerService, type ProblemDTO, type Tag } from '@/api'
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
const tabActiveName = ref('1')
const router = useRouter()
const submitEdit = () => {
  problemForm.value.problem.examples = JSON.stringify(exampleJsonObjects.value)
  console.log(problemForm.value)
}
const backToProblemset = () => {
  router.push({ name: 'AdminProblemset' })
}
const deleteTestCase = (index: number) => {
  problemForm.value.testCases.splice(index, 1)
}
const deleteExampleJson = (index: number) => {
  exampleJsonObjects.value.splice(index, 1)
}
const addExampleJson = () => {
  exampleJsonObjects.value.push({
    input: '',
    output: ''
  })
}
const addTestCase = () => {
  problemForm.value.testCases.push({
    input: '',
    output: ''
  })
}
interface exampleJson {
  input: string
  output: string
}
const exampleJsonObjects = ref<exampleJson[]>([
  {
    input: '',
    output: ''
  }
])
const problemForm = ref<ProblemDTO | any>({
  problem: {
    description: '',
    difficulty: 800,
    displayId: '',
    examples: '',
    input: '',
    isRemoveEndBlank: 1,
    isUploadCase: false,
    judgeMode: 'default',
    memoryLimit: 128, // mb
    note: '',
    openCaseResult: 1,
    output: '',
    spjCode: '',
    spjLanguage: '',
    stackLimit: 128,
    timeLimit: 1000, // ms
    title: '',
    visible: 1
  },
  tagIds: [],
  uploadTestcaseDir: '',
  testCases: [
    {
      input: '',
      output: ''
    }
  ]
})
const tagOptions = ref<any>([])
onMounted(async () => {
  const { data } = await AdminTagControllerService.listAllTagUsingGet()
  tagOptions.value = data?.map((tag: Tag) => {
    return {
      value: tag.id,
      label: tag.tagName
    }
  })
})
</script>

<style scoped>
.card-title {
  font-size: 1.8em;
  font-weight: bolder;
  color: black;
}
.tiny-tabs {
  border-bottom: 1px solid #ebeef5;
}
:deep(.tiny-input__inner) {
  border: 1px solid #c2c2c2;
  background-color: #ffffff;
}
.tiny-textarea {
  --tv-Textarea-border-color: none;
}
:deep(.tiny-textarea__inner) {
  height: 200px;
}
</style>
