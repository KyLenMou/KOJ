<template>
  <div id="problemsetView">
    <div class="commonBox space-between" style="display: flex">
      <el-switch
        v-model="showTagsVisible"
        active-text="Show Tags"
        inactive-text="Hide Tags"
      />
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"/>
    </div>
    <common-table table-head="Problemset" :table-data="problemList" style="width: 100%" :is-loading="isLoading">
      <template #tableContent>
        <el-table-column prop="problemId" label="#" min-width="100"></el-table-column>
        <el-table-column prop="title" label="Name" min-width="200"></el-table-column>
        <el-table-column v-if="showTagsVisible" prop="tags" label="Tags" min-width="200">
          <template #default="{ row }">
            <el-tag v-for="tag in row.tags" :key="tag.id" type="info" size="small" style="margin-right: 10px">{{ tag.tagName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="difficulty" label="Difficulty" min-width="75"></el-table-column>
        <el-table-column label="Operation" width="200">
          <template #default="{ row }">
            <el-button type="text" @click="goToProblem(row.problemId)">Detail</el-button>
          </template>
        </el-table-column>
      </template>
    </common-table>
  </div>
</template>

<script setup lang="ts">
import { AdminProblemControllerService, ProblemControllerService } from "@/api";
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { formatTimeColumn } from "@/util";

const router = useRouter();
const problemList = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const isLoading = ref(false);
const showTagsVisible = ref(false)
const getProblemList = async () => {
  isLoading.value = true;
  try {
    const res = await ProblemControllerService.listProblemsetVoByPageUsingPost({
      current: currentPage.value,
      pageSize: pageSize.value
    });
    problemList.value = res.data.records;
    total.value = res.data.total;
  } finally {
    isLoading.value = false;
  }
};

const handleSizeChange = async (val: number) => {
  pageSize.value = val;
  await getProblemList();
};

const handleCurrentChange = async (val: number) => {
  currentPage.value = val;
  await getProblemList();
};

onMounted(async () => {
  await getProblemList();
});

const goToProblem = (problemId: number) => {
  router.push(`/problem/${problemId}`);
};
</script>

<style scoped>
#problemsetView {
  margin-top: 20px;
}
</style>
