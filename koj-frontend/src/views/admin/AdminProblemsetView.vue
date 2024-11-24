<template>
  <div>
    <div class="admin-card-title">题目列表</div>
    <tiny-grid
      :fetch-data="getProblems"
      :pager="pagerConfig"
      :loading="tableLoading"
      size="mini"
      :auto-resize="true"
      :auto-load="true"
      :stripe="true"
    >
      <template #toolbar>
        <tiny-grid-toolbar refresh>
          <div style="display: flex; gap: 20px">
            <tiny-button type="primary" @click="goToAddProblem">新增题目</tiny-button>
            <tiny-search style="width: 300px" placeholder="请输入题目ID、展示ID或题目名称" />
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
          <tiny-tag v-if="row.judgeMode === 'default'" size="small" effect="plain"
            >默认评测</tiny-tag
          >
          <tiny-tag v-else-if="row.judgeMode === 'spj'" size="small" effect="plain" type="success"
            >特殊评测</tiny-tag
          >
          <tiny-tag v-else-if="row.judgeMode === 'interact'" size="small" effect="plain" type="info"
            >交互评测</tiny-tag
          >
          <tiny-tag v-else effect="dark" size="small" type="danger">未知评测</tiny-tag>
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="difficulty" title="难度" align="center" width="6%" />
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
  </div>
</template>

<script setup lang="ts">
import { AdminProblemControllerService, type AdminProblemVO, type Problem } from '@/api'
import { TinyModal, TinyNotify } from '@opentiny/vue'
import type { TinyGrid } from '@opentiny/vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const tableLoading = ref(false)
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
  api: async ({ page }: { page: { currentPage: number; pageSize: number } }) => {
    console.log(page)
    tableLoading.value = true
    const { currentPage, pageSize } = page
    const { data } = await AdminProblemControllerService.listProblemByPageUsingGet(
      currentPage,
      pageSize
    )
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
    // todo 刷新表格
  }
}
</script>

<style scoped></style>
