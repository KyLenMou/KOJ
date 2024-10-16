<template>
  <div id="problemsetAdminView">
    <div class="commonBox space-between" style="display: flex">
      <el-button type="primary" @click="goToAddProblem">Add Problem</el-button>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 30, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"/>
    </div>
    <common-table table-head="Problemset" :table-data="problemList" style="width: 100%" :is-loading="isLoading">
      <template #tableContent>
        <el-table-column prop="id" label="ID" min-width="75"/>
        <el-table-column prop="problemDisplayId" label="Problem ID" min-width="100"></el-table-column>
        <el-table-column prop="title" label="Title" min-width="200"></el-table-column>
        <el-table-column prop="difficulty" label="Difficulty" min-width="75"></el-table-column>
        <el-table-column prop="tags" label="Tags" width="200">
          <template #default="{ row }">
            <el-tag v-for="tag in row.tags" :key="tag.id" type="info" style="margin-right: 10px">{{ tag.tagName }} </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" :formatter="formatTimeColumn"/>
        <el-table-column prop="updateTime" label="Update Time" :formatter="formatTimeColumn"/>
        <el-table-column label="Operation" width="200">
<!--          <template #default="{ row }">-->
<!--            <el-button type="text" @click="goToEditProblem(row.problemId)">Edit</el-button>-->
<!--            <el-button type="text" @click="deleteProblem(row.problemId)">Delete</el-button>-->
<!--          </template>-->
        </el-table-column>
      </template>
    </common-table>

  </div>
</template>

<script setup lang="ts">
import { AdminProblemControllerService } from "@/api";
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { formatTimeColumn } from "@/util";

const router = useRouter();
const problemList = ref([]);
const current = ref(1);
const pageSize = ref(10);
const total = ref(0);
const isLoading = ref(false);
const goToAddProblem = () => {
  router.push('/admin/problem');
};

const getProblemList = async () => {
  isLoading.value = true;
  try {
    const res = await AdminProblemControllerService.listProblemVoByPageUsingPost({
      current: current.value,
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
  current.value = val;
  await getProblemList();
};

onMounted(async () => {
  await getProblemList();
});
</script>

<style scoped>
#problemsetAdminView {

}
</style>
