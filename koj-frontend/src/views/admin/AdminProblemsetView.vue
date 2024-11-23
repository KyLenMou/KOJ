<template>
  <div>
    <div style="font-size: 1.8em; font-weight: bolder; color: black">题目列表</div>
    <tiny-grid
      :fetch-data="fetchDataOption"
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
            <tiny-button type="primary" @click="goToEditProblem">新增题目</tiny-button>
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
          <tiny-button type="info" size="mini"> 修改 </tiny-button>
          <tiny-button type="danger" size="mini"> 删除 </tiny-button>
        </template>
      </tiny-grid-column>
    </tiny-grid>
  </div>
</template>

<script setup lang="ts">
import { AdminProblemControllerService, type AdminProblem, type Problem } from '@/api'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const tableLoading = ref(false)
const pagerConfig = ref({
  attrs: {
    currentPage: 1,
    pageSize: 10,
    pageSizes: [10, 30, 50],
    total: 0,
    align: 'right', // 可选值：['left', 'center', 'right']
    layout: 'total, sizes, prev, pager, next, jumper'
  }
})
const fetchDataOption = ref({
  api: async ({ page }: { page: { currentPage: number; pageSize: number } }) => {
    tableLoading.value = true
    const { currentPage, pageSize } = page
    const { data } = await AdminProblemControllerService.listProblemByPageUsingGet(
      currentPage,
      pageSize
    )
    tableLoading.value = false
    return {
      result: data?.records as AdminProblem[],
      page: { total: data?.total }
    }
  }
})
const goToEditProblem = () => {
  router.push({ name: 'AdminProblem' })
}
</script>

<style scoped></style>
