<template>
  <div id="problemsetAdminView">
    <div class="space-between common-margin-bottom">
      <span class="title-header">New Problem</span>
      <el-button style="margin-left: auto" type="primary" @click="doSave(problemFormRef)">Save</el-button>
    </div>
    <el-form
      style="padding: 10px"
      ref="problemFormRef"
      :model="problemForm"
      :rules="rules"
      label-position="top"
    >
      <el-row :gutter="20">
        <el-col :span="5">
          <div class="commonBox">
            <el-form-item label="Problem Id" prop="problem.problemId">
              <el-input v-model="problemForm.problem.problemId"></el-input>
            </el-form-item>
            <el-form-item label="Title" prop="problem.title">
              <el-input v-model="problemForm.problem.title"></el-input>
            </el-form-item>
            <el-form-item label="Problem Source" prop="problem.problemSource">
              <el-input v-model="problemForm.problem.problemSource"></el-input>
            </el-form-item>
            <el-row class="space-between">
              <el-form-item label="Problem Type" prop="problem.problemType" style="width: 48%">
                <el-select v-model="problemForm.problem.problemType">
                  <el-option label="ACM" value="ACM"></el-option>
                  <el-option label="OI" value="OI"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="Auth" prop="problem.auth" style="width: 48%">
                <el-select v-model="problemForm.problem.auth">
                  <el-option label="Public" value="1"></el-option>
                  <el-option label="Private" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-row>
            <el-form-item label="Difficulty" prop="problem.difficulty">
              <el-input-number v-model="problemForm.problem.difficulty"
                               style="width: 100%"
                               :step="100"
                               step-strictly/>
            </el-form-item>
            <el-row class="space-between">
              <el-form-item label="IO Score" prop="problem.ioScore" style="width: 48%">
                <el-input-number v-model="problemForm.problem.ioScore" controls-position="right" :step="100"/>
              </el-form-item>
              <el-form-item label="CF Score" prop="problem.cfScore" style="width: 48%">
                <el-input-number v-model="problemForm.problem.cfScore" controls-position="right" :step="100"/>
              </el-form-item>
            </el-row>
            <el-form-item label="Time Limit" prop="problem.timeLimit">
              <el-input-number v-model="problemForm.problem.timeLimit" style="width: 100%" :step="1000"/>
            </el-form-item>
            <el-row class="space-between">
              <el-form-item label="Memory Limit" prop="problem.memoryLimit" style="width: 48%">
                <el-input-number v-model="problemForm.problem.memoryLimit" controls-position="right" :step="128"/>
              </el-form-item>
              <el-form-item label="Stack Limit" prop="problem.stackLimit" style="width: 48%" :step="128">
                <el-input-number v-model="problemForm.problem.stackLimit" controls-position="right"/>
              </el-form-item>
            </el-row>
            <el-form-item label="Remove End Blank" prop="problem.isRemoveEndBlank">
              <el-switch v-model="problemForm.problem.isRemoveEndBlank"></el-switch>
            </el-form-item>
            <el-form-item label="Code Share" prop="problem.codeShare">
              <el-switch v-model="problemForm.problem.codeShare"></el-switch>
            </el-form-item>
            <el-form-item label="Open Case Result" prop="problem.openCaseResult">
              <el-switch v-model="problemForm.problem.openCaseResult"></el-switch>
            </el-form-item>
            <el-form-item label="Tags" prop="tags">
              <el-select
                v-model="selectedTagIds"
                filterable
                multiple
                placeholder="Select Tags"
                @change="updateSelectedTags"
              >
                <el-option
                  v-for="(tag, index) in tagOptions"
                  :key="tag.id"
                  :label="tag.tagName"
                  :value="index"
                />
              </el-select>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="19">
          <div class="commonBox">
            <el-form-item label="Description" prop="problem.descriptionText">
              <MdEditor v-model="problemForm.problem.descriptionText"/>
            </el-form-item>
            <el-form-item label="Input" prop="problem.input">
              <MdEditor v-model="problemForm.problem.input"/>
            </el-form-item>
            <el-form-item label="Output" prop="problem.output">
              <MdEditor v-model="problemForm.problem.output"/>
            </el-form-item>
            <el-form-item label="Note" prop="problem.noteText">
              <MdEditor v-model="problemForm.problem.noteText"/>
            </el-form-item>
            <el-form-item label="Examples" prop="problem.examples">
              <el-input v-model="problemForm.problem.examples"></el-input>
            </el-form-item>
            <el-form-item label="Judge Mode" prop="problem.judgeMode">
              <el-select v-model="problemForm.problem.judgeMode">
                <el-option label="Default" value="default"></el-option>
                <el-option label="SpecialJudge" value="spj"></el-option>
                <el-option label="Interactive" value="interactive"></el-option>
              </el-select>
            </el-form-item>
            <template v-if="problemForm.problem.judgeMode === 'default'">
              <el-form-item label="Is Upload Case" prop="problem.isUploadCase">
                <el-switch v-model="problemForm.problem.isUploadCase"></el-switch>
              </el-form-item>
              <el-form-item v-if="!problemForm.problem.isUploadCase" prop="testCases" style="width: 100%">
                <template #label>
                  <div class="space-between">
                    <span>Test Cases</span>
                    <el-button @click="addTestCase" type="primary" plain>Add TestCase</el-button>
                  </div>
                </template>
                <el-row v-for="(testCase, index) in problemForm.testCases" :key="index"
                        style="width: 100%;box-shadow: rgba(0, 0, 0, 0.1) 0px 2px 12px 0px;" class="commonBox">
                  <div style="width: 100%;" class="space-between common-margin-bottom">
                    <el-tag type="info">test case {{ index + 1 }}</el-tag>
                    <div>
                      <el-tag type="success" style="margin-right: 10px">score</el-tag>
                      <el-input-number v-model="testCase.score" controls-position="right" size="small" :step="10"/>
                    </div>
                  </div>
                  <el-input :autosize="{ minRows: 5, maxRows: 5 }" style="width: 50%" type="textarea"
                            v-model="testCase.input" resize="none" placeholder="input"/>
                  <el-input :autosize="{ minRows: 5, maxRows: 5 }" style="width: 50%" type="textarea"
                            v-model="testCase.output" resize="none" placeholder="output"/>
                  <el-button @click="deleteTestCase(index)" plain type="danger" style="width: 100%">
                    Delete This TestCase
                  </el-button>
                </el-row>
              </el-form-item>
            </template>
            <template v-else-if="problemForm.problem.judgeMode ==='spj'">
              <el-form-item label="Is Upload Case" prop="problem.isUploadCase">
                <el-switch v-model="problemForm.problem.isUploadCase"></el-switch>
              </el-form-item>
              <el-form-item v-if="!problemForm.problem.isUploadCase" prop="testCases" style="width: 100%">
                <template #label>
                  <div class="space-between">
                    <span>Test Cases</span>
                    <el-button @click="addTestCase" type="primary" plain>Add TestCase</el-button>
                  </div>
                </template>
                <el-row v-for="(testCase, index) in problemForm.testCases" :key="index"
                        style="width: 100%;box-shadow: rgba(0, 0, 0, 0.1) 0px 2px 12px 0px;" class="commonBox">
                  <div style="width: 100%;" class="space-between common-margin-bottom">
                    <el-tag type="info">test case {{ index + 1 }}</el-tag>
                    <div>
                      <el-tag type="success" style="margin-right: 10px">score</el-tag>
                      <el-input-number v-model="testCase.score" controls-position="right" size="small" :step="10"/>
                    </div>
                  </div>
                  <el-input :autosize="{ minRows: 5, maxRows: 5 }" style="width: 100%" type="textarea"
                            v-model="testCase.input" resize="none" placeholder="input"/>
                  <el-button @click="deleteTestCase(index)" plain type="danger" style="width: 100%">
                    Delete This TestCase
                  </el-button>
                </el-row>
              </el-form-item>
              <el-form-item label="SPJ Language" prop="problem.spjLanguage">
                <el-input v-model="problemForm.problem.spjLanguage"></el-input>
              </el-form-item>
              <el-form-item label="SPJ Code" prop="problem.spjCode">
                <el-input v-model="problemForm.problem.spjCode"></el-input>
              </el-form-item>
            </template>
            <template v-else>
              <el-form-item label="SPJ Language" prop="problem.spjLanguage">
                <el-input v-model="problemForm.problem.spjLanguage"></el-input>
              </el-form-item>
              <el-form-item label="SPJ Code" prop="problem.spjCode">
                <el-input v-model="problemForm.problem.spjCode"></el-input>
              </el-form-item>
            </template>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import {
  AdminProblemControllerService,
  AdminTagControllerService,
  type ProblemDTO,
  type Tag
} from "@/api";

const router = useRouter()

const problemFormRef = ref<FormInstance>()

const problemForm = ref<ProblemDTO | any>({
  problem: {
    problemId: '',
    problemSource: '',
    problemType: 'ACM',
    title: '',
    stackLimit: 128,
    memoryLimit: 128,
    timeLimit: 1000,
    descriptionText: '',
    input: '',
    output: '',
    noteText: '',
    isRemoveEndBlank: true,
    difficulty: 800,
    ioScore: 100,
    cfScore: 500,
    auth: '1',
    codeShare: false,
    examples: '',
    isGroup: false,
    isPublic: 1,
    isUploadCase: false,
    judgeMode: 'default',
    openCaseResult: false,
    spjCode: '',
    spjLanguage: '',
  },
  uploadTestcaseDir: '',
  tags: [],
  testCases: [
    {
      input: '',
      output: '',
      score: 100
    }
  ]
});

// 标签选择
const tagOptions = ref<Tag[] | any>([])
const selectedTagIds = ref<number[]>([]);
const selectedTags = computed(() => {
  return selectedTagIds.value.map(index => tagOptions.value[index]);
});
const updateSelectedTags = () => {
  problemForm.value.tags = selectedTags.value;
  console.log(problemForm.value.tags)
};

// 添加testCase
const addTestCase = () => {
  problemForm.value.testCases.push({
    input: '',
    output: '',
    score: 100
  })
  console.log(problemForm.value.testCases)
}

// 删除testCase
const deleteTestCase = (index: number) => {
  problemForm.value.testCases.splice(index, 1)
}

// 题目表单规则
const rules = reactive<FormRules<typeof problemForm>>({
  'problem.problemId': [
    {required: true, message: 'Please input problem id', trigger: 'blur'},
    {min: 1, max: 32, message: 'Length should be 1 to 32', trigger: 'blur'}
  ],
  'problem.problemSource': [
    {min: 0, max: 65535, message: 'Length should be 0 to 65535', trigger: 'blur'}
  ],
  'problem.title': [
    {required: true, message: 'Please input title', trigger: 'blur'},
    {min: 1, max: 256, message: 'Length should be 1 to 256', trigger: 'blur'}
  ],
});

onMounted(async () => {
  const tagRes = await AdminTagControllerService.listAllTagUsingGet()
  if (tagRes.data) {
    tagOptions.value = tagRes.data
  }
})

// 保存题目
const doSave = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  // 校验
  try {
    await new Promise((resolve, reject) => {
      formEl.validate((valid) => {
        if (!valid) {
          reject(new Error('Validation failed'))
        } else {
          resolve(null)
        }
      })
    })
  } catch (e) {
    return
  }
  console.log(problemForm.value)
  const res = await AdminProblemControllerService.saveProblemUsingPost(problemForm.value)
  ElMessage.success('Save Success')
  await router.push('/admin/problemset')
}

</script>

<style scoped>
#problemsetAdminView {

}

</style>
