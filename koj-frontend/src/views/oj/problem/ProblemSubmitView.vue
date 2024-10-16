<template>
  <div id="problemSubmissionView">
    <el-row :gutter="20">
      <el-col :span="18" style="display: flex; flex-direction: column">
        <div style="width: 75%; margin: 0 auto">
          <h2 style="text-align: center;margin-bottom: 30px">Submit Solution</h2>
          <el-form
            label-position="right"
            class="common-font"
            label-width="100"
          >
            <el-form-item label="Problem:">
              {{ problem.problemDisplayId }} - {{ problem.title }}
            </el-form-item>
            <el-form-item label="Language:">
              <el-select v-model="submission.language" style="width: 200px">
                <el-option label="C++ 17" value="C++ 17"></el-option>
                <el-option label="Java" value="java"></el-option>
                <el-option label="Python" value="python"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Code:" label-width="100">
              <div style="height: 400px; width: 100%;border: 1px solid rgba(0, 0, 0, 0.1)">
                <code-editor
                  :value="submission.code"
                  :only-show="true"
                  :language="submission.language" :handle-change="handleChange"/>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="margin: 0 auto" @click="doSubmitCode">Submit</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="6">
        <common-card head="Problem Tags">
          <template #content>
            <el-tag v-for="tag in problem.tags" :key="tag.id" type="info" style="margin-right: 5px">{{ tag.tagName }}
            </el-tag>
            <el-tag>*{{ problem.difficulty }}</el-tag>
          </template>
        </common-card>
        <common-card head="My Submissions">
          <template #content>
            <el-tag v-for="tag in problem.tags" :key="tag.id" type="info" style="margin-right: 5px">
              {{ tag.tagName }}
            </el-tag>
            <el-tag>*{{ problem.difficulty }}</el-tag>
          </template>
        </common-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import {
  ProblemControllerService,
  type ProblemInfoVO,
  type SubmissionDTO,
  SubmitControllerService,
  type TagVO
} from "@/api";
import { ElMessage } from "element-plus";
import { storeToRefs } from "pinia";
import { useCurrentUserStore } from "@/stores/currentUser";
import { isLogin } from "@/util";

const router = useRouter();

const {currentUser} = storeToRefs(useCurrentUserStore());

const submission = ref<SubmissionDTO | any>({
  code: '',
  language: 'C++ 17',
  problemDisplayId: '',
  problemId: -1,
  userId: '',
})
const route = useRoute();
const problem = ref<ProblemInfoVO | any>({
  authorUserId: '',
  descriptionText: '',
  difficulty: '',
  examples: '',
  id: '',
  input: '',
  ioScore: '',
  judgeMode: '',
  memoryLimit: 128,
  noteText: '',
  output: '',
  problemDisplayId: '',
  problemSource: '',
  problemType: '',
  stackLimit: 128,
  tags: [],
  timeLimit: 1000,
  title: '',
})

onMounted(async () => {
  if (currentUser.value.id?.length === 0) {
    ElMessage.error('Please login first');
    return
  }
  submission.value.userId = currentUser.value.id;
  const problemDisplayId = route.params.problemDisplayId as string;
  const res = await ProblemControllerService.getProblemDetailUsingGet(problemDisplayId);
  problem.value = res.data;
  // todo 非管理端只是用problemDisplayId
  submission.value.problemId = problem.value.id;
  submission.value.problemDisplayId = problem.value.problemDisplayId;
});

const handleChange = (v: string) => {
  submission.value.code = v;
}

const doSubmitCode = async () => {
  if (!isLogin()) {
    ElMessage.error('Not Login');
    return
  }
  if (submission.value.problemId === -1) {
    ElMessage.error('Problem ID is not set');
    return
  }
  if (submission.value.code.length === 0) {
    ElMessage.error('Code is empty');
    return
  }
  const res = await SubmitControllerService.submitUsingPost(submission.value);
  // todo 提交成功后跳转该题的到submission页面
  ElMessage.success('Submit Success');
  console.log(res);
  await router.push({name: 'problem-submission'});
}
</script>

<style scoped>
#problemSubmissionView {
}

</style>
