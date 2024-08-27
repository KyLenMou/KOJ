<template>
  <div id="tagAdminView">
    <div class="operationBox">
      <el-button type="primary" @click="newTagDialogVisible = true">New Tag</el-button>
<!--      <el-button type="success" >New Tag</el-button>-->
<!--      <el-button type="danger" >New Tag</el-button>-->
    </div>
    <common-table table-head="Tag List" :table-data="tagList">
      <template #tableContent>
        <el-table-column
          v-for="column in tagColumns"
          :key="column"
          :prop="column"
          :label="column"
          :formatter="formatTimeColumn"
        ></el-table-column>
        <el-table-column
          label="Operation"
        >

        </el-table-column>
      </template>
    </common-table>
    <el-dialog
      v-model="newTagDialogVisible"
      width="300"
      title="Enter New Tag Name"
    >
      <el-input v-model="newTagName" placeholder="Tag Name" />
      <template #footer>
        <el-button type="primary" @click="doAddTag">Confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { AdminTagControllerService, type Tag } from "@/api";
import { ElMessage } from "element-plus";
import { formatTimeColumn, getColumnNames } from "@/util";

const tagList = ref<Tag[]>([])
const newTagDialogVisible = ref(false)
const newTagName = ref<string>('')
const tagColumns = ref<string[]>([])

onMounted( async () => {
  await doListTag()
});

const doAddTag = async () => {
  const tag:Tag = {
    tagName: newTagName.value
  }
  await AdminTagControllerService.addTagUsingPost(tag)
  newTagDialogVisible.value = false
  ElMessage.success('Add Tag Success')
  newTagDialogVisible.value = false
  newTagName.value = ''
  await doListTag()
}

const doListTag = async () => {
  const res = await AdminTagControllerService.listTagByPageUsingPost({current:1,pageSize:10})
  tagList.value = res.data.records
  if (tagList.value) {
    tagColumns.value = getColumnNames(tagList.value[0])
  }
}

</script>

<style scoped>
#tagAdminView {

}
</style>
