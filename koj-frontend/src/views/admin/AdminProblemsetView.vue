<template>
  <div>
    <div class="admin-card-title">题目列表</div>
    <tiny-grid
      ref="problemGrid"
      size="mini"
      :fetch-data="getProblems"
      :pager="pagerConfig"
      :loading="tableLoading"
      :auto-resize="true"
      :auto-load="true"
      :stripe="true"
    >
      <template #toolbar>
        <tiny-grid-toolbar refresh>
          <div style="display: flex">
            <tiny-button type="primary" @click="goToAddProblem">新增题目</tiny-button>
            <tiny-button type="info" @click="showTagModal = true">管理标签</tiny-button>
            <tiny-search
              style="width: 300px; margin-left: 10px"
              placeholder="请输入题目ID、展示ID或题目名称"
            />
          </div>
        </tiny-grid-toolbar>
      </template>
      <tiny-grid-column field="id" title="#" align="center" width="5%" />
      <tiny-grid-column field="displayId" title="展示ID" align="center" width="8%" />
      <tiny-grid-column field="title" title="题目名称" align="center" width="8%" />
      <tiny-grid-column field="tags" title="标签" align="center" width="15%">
        <template #default="{ row }">
          <tiny-tag
            v-for="tag in row.tags"
            :key="tag.id"
            style="margin-right: 5px"
            size="small"
            color="grey"
            >{{ tag.tagName }}
          </tiny-tag>
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="judgeMode" title="评测模式" align="center" width="6%">
        <template #default="{ row }">
          <judge-mode-tag :judgeMode="row.judgeMode" />
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="difficulty" title="难度" align="center" width="6%">
        <template #default="{ row }">
          <difficulty-div :difficulty="row.difficulty" />
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="visible" title="是否可见" align="center" width="6%">
        <template #default="{ row }">
          <tiny-switch v-model="row.visible" :true-value="1" :false-value="0" />
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="authorUsername" title="作者" align="center" width="7%" />
      <tiny-grid-column field="modifiedUsername" title="最近修改者" align="center" width="7%" />
      <tiny-grid-column field="createTime" title="创建时间" align="center" width="9%">
        <template #default="{ row }">{{ new Date(row.createTime).toLocaleString() }}</template>
      </tiny-grid-column>
      <tiny-grid-column field="updateTime" title="更新时间" align="center" width="9%">
        <template #default="{ row }">{{ new Date(row.updateTime).toLocaleString() }}</template>
      </tiny-grid-column>
      <tiny-grid-column title="操作" align="center" width="14%">
        <template #default="{ row }">
          <tiny-button type="info" size="mini" @click="goToEditProblem(row.id)"> 修改 </tiny-button>
          <tiny-button type="danger" size="mini" @click="deleteProblem(row.id)"> 删除 </tiny-button>
        </template>
      </tiny-grid-column>
    </tiny-grid>
    <tiny-modal
      v-model="showTagModal"
      title="标签管理"
      status="info"
      show-footer
      width="470px"
      :resize="false"
    >
      <tiny-row :gutter="10">
        <tiny-col :span="9">
          <tiny-input v-model="newTagName" placeholder="新增标签" style="margin-bottom: 10px" />
        </tiny-col>
        <tiny-col :span="3">
          <tiny-button type="info" style="border-radius: 6px" @click="addTag">添加</tiny-button>
        </tiny-col>
      </tiny-row>
      <div style="display: flex; flex-wrap: wrap; gap: 10px">
        <tiny-tag
          v-for="tag in tags"
          size="medium"
          :key="tag.id"
          closable
          type="info"
          @close="handleClose(tag)"
          :before-delete="beforeDeleteTag"
          >{{ tag.tagName }}</tiny-tag
        >
      </div>
    </tiny-modal>
  </div>
</template>

<script setup lang="ts">
import {
  AdminProblemControllerService,
  AdminTagControllerService,
  type AdminProblemVO,
  type Tag
} from '@/api'
import { TinyModal } from '@opentiny/vue'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const tableLoading = ref(false)
const problemGrid = ref()
const showTagModal = ref(true)
const tags = ref<Tag[] | any>([])
const newTagName = ref('')
// tiny-grid 分页配置
const pagerConfig = ref({
  attrs: {
    currentPage: 1,
    pageSize: 10,
    pageSizes: [10, 30, 50],
    total: 0,
    align: 'right',
    layout: 'total, sizes, prev, pager, next, jumper'
  }
})
// tiny-grid 获取题目列表
const getProblems = ref({
  api: async ({ page }: any) => {
    tableLoading.value = true
    const { currentPage, pageSize } = page
    const { data } = await AdminProblemControllerService.listProblemByPageUsingGet(
      currentPage,
      pageSize
    )
    console.log(data)
    tableLoading.value = false
    return {
      result: data?.records as AdminProblemVO[],
      page: { total: data?.total }
    }
  }
})
// 新增题目
const goToAddProblem = () => {
  router.push({ name: 'AdminProblem' })
}
// 更新题目
const goToEditProblem = (problemId: number) => {
  router.push({ name: 'AdminProblem', query: { problemId: problemId } })
}
// 删除题目
const deleteProblem = async (problemId: number) => {
  const res = await TinyModal.confirm(
    '您确定要删除吗？删除后，该题目所有测试用例和提交等关联数据都会被删除！'
  )
  if (res === 'confirm') {
    const { code } = await AdminProblemControllerService.deleteProblemUsingDelete(problemId)
    if (code) return
    // 刷新表格
    problemGrid.value.handleFetch()
  }
}
const addTag = async () => {
  const { code } = await AdminTagControllerService.addTagUsingPost(newTagName.value)
  if (code) return
  newTagName.value = ''
  await getAllTags()
}
const handleClose = async (tag: any) => {
  const { code } = await AdminTagControllerService.deleteTagUsingDelete(tag.id)
  if (code) return
  await getAllTags()
}

const beforeDeleteTag = async (done: any) => {
  TinyModal.confirm({
    status: 'warning',
    message:
      '确定删除该标签吗？所有包含该标签的题目将不再包含该标签！如果需要重新添加，则需要对每个题目重新添加该标签！'
  }).then((res: any) => {
    res === 'confirm' && done()
  })
}
const getAllTags = async () => {
  const { code, data } = await AdminTagControllerService.listAllTagUsingGet()
  if (code) return
  tags.value = data
}
onMounted(async () => {
  await getAllTags()
})
</script>

<style scoped></style>
