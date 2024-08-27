<template>
  <div id="problemsetAdminView">
    <div class="operationBox">
      <el-button type="primary" @click="goToAddProblem">Add Problem</el-button>
    </div>
    <common-table/>
  </div>
</template>

<script setup lang="ts">
const router = useRouter()
const goToAddProblem = () => {
  router.push('/admin/problem')
}
</script>

<style scoped>
#problemsetAdminView {

}
</style>
