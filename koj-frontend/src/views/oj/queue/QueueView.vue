<template>
  <div id="queueView">
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
      table-head="Submission Queue"
    >
      <template #tableContent>
        <el-table-column prop="id" label="#"/>
        <el-table-column prop="submitTime" label="When" :formatter="formatTimeColumn" width="120"/>
        <el-table-column prop="username" label="Who"/>
        <el-table-column prop="title" label="Problem"/>
        <el-table-column prop="language" label="Lang"/>
        <el-table-column prop="verdict" label="Verdict">
          <template #default="{ row }">
            <VerdictText :verdict="row.verdict"/>
          </template>
        </el-table-column>
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
// todo submit长度length校验
const submissionList = ref<any>([]);

onMounted(async () => {
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
#queueView {
  margin-top: 20px;
}
</style>
