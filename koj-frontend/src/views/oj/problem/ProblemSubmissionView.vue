<template>
  <div id="problemSubmitView">
    <div v-if="!isFullScreen">
      <el-row :gutter="20">
        <el-col :span="18" style="display: flex; flex-direction: column">
          <div style="text-align: center">
            <h2>{{ problem.problemId }}. {{ problem.title }}</h2>
            <div>TimeLimit: {{ problem.timeLimit }} ms</div>
            <div>MemoryLimit: {{ problem.memoryLimit }} MB</div>
            <div>StackLimit: {{ problem.stackLimit }} MB</div>
          </div>
          <h3>Description</h3>
          <md-preview :model-value="problem.descriptionText"/>
          <h3>Input</h3>
          <md-preview :model-value="problem.input"/>
          <h3>Output</h3>
          <md-preview :model-value="problem.output"/>
          <h3>Example</h3>
          <md-preview :model-value="problem.examples"/>
          <h3>Note</h3>
          <md-preview :model-value="problem.noteText"/>
        </el-col>
        <el-col :span="6">
          <el-button style="width: 100%; margin-bottom: 20px" type="success" @click="isFullScreen = true">沉浸式做题</el-button>
          <common-card head="Problem Tags">
            <template #content>
              <el-tag v-for="tag in problem.tags" :key="tag.id" type="info" style="margin-right: 5px">{{ tag.tagName
                                                                                                      }}
              </el-tag>
              <el-tag>*{{ problem.difficulty }}</el-tag>
            </template>
          </common-card>
          <common-card head="Last Submissions">
            <template #content>
              <el-tag v-for="tag in problem.tags" :key="tag.id" type="info" style="margin-right: 5px">{{ tag.tagName
                                                                                                      }}
              </el-tag>
              <el-tag>*{{ problem.difficulty }}</el-tag>
            </template>
          </common-card>
        </el-col>
      </el-row>
    </div>
    <div v-else class="full-screen">
<!--     todo 全屏dialog-->
      <div style="height: 65px; border-bottom: 1px solid #999999;" class="space-between">
        <el-image
          style="height: 100%;margin-left: 10px"
          :src="getImg()"/>
        <el-button type="success" @click="isFullScreen = false" style="margin-right: 20px">Exit Full Screen</el-button>
      </div>
      <el-row style="height: calc(100vh - 70px);">
        <el-col :span="12" style="height: calc(100vh - 70px);overflow:auto;padding: 10px;">
          <div style="text-align: center">
            <h2>{{ problem.problemId }}. {{ problem.title }}</h2>
            <div>TimeLimit: {{ problem.timeLimit }} ms</div>
            <div>MemoryLimit: {{ problem.memoryLimit }} MB</div>
            <div>StackLimit: {{ problem.stackLimit }} MB</div>
          </div>
          <h3>Description</h3>
          <md-preview :model-value="problem.descriptionText"/>
          <h3>Input</h3>
          <md-preview :model-value="problem.input"/>
          <h3>Output</h3>
          <md-preview :model-value="problem.output"/>
          <h3>Example</h3>
          <md-preview :model-value="problem.examples"/>
          <h3>Note</h3>
          <md-preview :model-value="problem.noteText"/>
        </el-col>
        <el-col :span="12">
          <code-editor/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import { ProblemControllerService, type ProblemInfoVO, type TagVO } from "@/api";
const isFullScreen = ref(false);
const route = useRoute();
const menuList = ['problem', 'submissions']

function getImg() {
  return new URL(`../../assets/logo.png`, import.meta.url).href;
}

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
  problemId: '',
  problemSource: '',
  problemType: '',
  stackLimit: 128,
  tags: [],
  timeLimit: 1000,
  title: '',
})

onMounted(async () => {
  const problemId = route.params.problemId as string;
  const res = await ProblemControllerService.getProblemDetailUsingGet(problemId);
  problem.value = res.data;
});

// 题目详情
const str = ref("#123\n- 123\n - 321")

</script>

<style scoped>
#problemSubmitView {
}

.full-screen {
  position: absolute;
  background-color: white;
  top: 0;
  left: 0;
  right: 0;
  width: 100vw;
  height: 100vh;
}

</style>
