<template>
  <div id="tagAdminView">
    <div class="commonBox">
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
          <template #default="{ row }">
            <el-popover
              placement="top"
              width="200"
              trigger="click"
            >
              <el-input v-model="renameTagName" placeholder="Rename Tag"/>
              <el-button type="primary" @click="doRenameTag(row.id)" style="width: 100%;margin-top: 10px" size="small">Confirm</el-button>
              <template #reference>
                <el-button type="primary" plain>Rename</el-button>
              </template>
            </el-popover>
            <el-popconfirm title="Are you sure to delete this tag?" @confirm="doDeleteTag(row.id)">
              <template #reference>
                <el-button type="danger" plain>Delete</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </template>
    </common-table>
    <el-dialog
      v-model="newTagDialogVisible"
      width="300"
      title="Enter New Tag Name"
    >
      <el-input v-model="newTagName" placeholder="Tag Name"/>
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

const tagList = ref<Tag[] | any>([])
const newTagDialogVisible = ref(false)
const newTagName = ref<string>('')
const tagColumns = ref<string[]>([])

const renameTagName = ref<string>('')


onMounted(async () => {
  await doListTag()
});

const doAddTag = async () => {
  const tag: Tag = {
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
  const res = await AdminTagControllerService.listAllTagUsingGet()
  tagList.value = res.data
  if (tagList.value) {
    tagColumns.value = getColumnNames(tagList.value[0])
  }
}

const doDeleteTag = async (id:number) => {
  await AdminTagControllerService.deleteTagUsingDelete(id)
  ElMessage.success('Delete Tag Success')
  await doListTag()
}

const doRenameTag = async (id: number) => {
  if (!renameTagName.value.length) {
    ElMessage.error('Tag Name Cannot Be Empty')
    return
  }
  const tag: Tag = {
    id: id,
    tagName: renameTagName.value
  }
  await AdminTagControllerService.updateTagUsingPut(tag)
  ElMessage.success('Rename Tag Success')
  renameTagName.value = ''
  await doListTag()
}
</script>

<style scoped>
#tagAdminView {

}
</style>
