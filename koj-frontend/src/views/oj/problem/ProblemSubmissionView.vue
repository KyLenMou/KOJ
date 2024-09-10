<template>
  <div id="problemSubmissionView">
    <div class="commonBox space-between" style="display: flex">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"/>
    </div>
    <common-table
      :is-loading="isLoading"
      :table-data="submissionList"
      table-head="Submission List"
      >
      <template #tableContent>
        <el-table-column prop="id" label="#"/>
        <el-table-column prop="submitTime" label="When" :formatter="formatTimeColumn"/>
        <el-table-column prop="username" label="Who"/>
        <el-table-column prop="title" label="Problem"/>
        <el-table-column prop="language" label="Lang"/>
        <el-table-column prop="verdict" label="Verdict"/>
        <el-table-column prop="time" label="Time">
          <template #default="{ row }">
            {{ row.time ? row.time : '-' }} ms
          </template>
        </el-table-column>
        <el-table-column prop="memory" label="Memory">
          <template #default="{ row }">
            {{ row.memory ? row.memory : '-' }} KB
          </template>
        </el-table-column>
        <el-table-column prop="length" label="Length"/>
      </template>
    </common-table>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import {
  ProblemControllerService,
  type ProblemInfoVO,
  SubmissionControllerService,
  type SubmissionListVO,
  type TagVO
} from "@/api";
import { ElMessage } from "element-plus";
import { formatTimeColumn } from "@/util";

const route = useRoute();

const isLoading = ref(false);
const current = ref(1);
const pageSize = ref(10);
const total = ref(0);

const pageQuery = ref({
  language: '',
  problemDisplayId: '',
  userId: '',
  username: '',
})

const submissionList = ref<[]>([]);

onMounted(async () => {
  pageQuery.value.problemDisplayId = route.params.problemId as string
  if (pageQuery.value.problemDisplayId === '') {
    ElMessage.error('Problem id is empty');
  }
  await getSubmissionListPage();
});

const getSubmissionListPage = async () => {
  isLoading.value = true;
  try {
    const res = await SubmissionControllerService.listSubmissionByPageUsingGet(
      current.value,
      pageSize.value,
      pageQuery.value.language,
      pageQuery.value.problemDisplayId,
      undefined,
      pageQuery.value.userId,
      pageQuery.value.username,
    );
    total.value = res.data?.total as number;
    submissionList.value = res.data?.records;
  } finally {
    isLoading.value = false;
  }
}

const handleSizeChange = async (val: number) => {
  pageSize.value = val;
  await getSubmissionListPage();
};

const handleCurrentChange = async (val: number) => {
  current.value = val;
  await getSubmissionListPage();
};

</script>

<style scoped>
#problemSubmissionView {
}


</style>
