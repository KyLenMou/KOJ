<template>
  <div>
    <tiny-card style="width: 100%" class="problemsetCard">
      <template #title>
        <h2>题目集</h2>
      </template>
      <tiny-layout style="margin-bottom: 15px">
        <tiny-row>
          <tiny-search style="width: 300px" clearable placeholder="请输入关键词" />
        </tiny-row>
      </tiny-layout>
      <tiny-layout :col="12">
        <tiny-row>
          <tiny-col :span="8">
            <tiny-grid
              :fetch-data="getProblemset"
              :pager="pagerConfig"
              :loading="tableLoading"
              :auto-resize="true"
              :auto-load="true"
              :stripe="true"
            >
              <tiny-grid-column field="displayId" title="题目ID" align="center" width="15%">
                <template #default="{ row }">
                  <tiny-link type="primary">{{ row.displayId }}</tiny-link>
                </template>
              </tiny-grid-column>
              <tiny-grid-column field="title" title="标题" align="center">
                <template #default="{ row }">
                  <tiny-link type="primary">{{ row.title }}</tiny-link>
                </template>
              </tiny-grid-column>
              <tiny-grid-column field="difficulty" title="难度" width="15%" align="center">
                <template #default="{ row }">
                  <difficulty-div :difficulty="row.difficulty" />
                </template>
              </tiny-grid-column>
            </tiny-grid>
          </tiny-col>
          <tiny-col :span="4">
            <tiny-card custom-class="card-boarder" :auto-width="true">
              <div style="display: flex; justify-content: space-between">
                <div>
                    <div>
                        <span class="oj-card-title">{{ problemInfo.title }}</span>
                        <span>{{ ' #' + problemInfo.id}}</span>
                    </div>
                   <span> {{ problemInfo.displayId }}</span>
                </div>
                <div>
                  <judge-mode-tag :judgeMode="problemInfo.judgeMode" effect="dark" />
                </div>
              </div>
              <md-preview v-model="problemInfo.description" />
              <div class="oj-card-subtitle">标签</div>
              <difficulty-tag :difficulty="problemInfo.difficulty" style="margin-right: 5px" />
              <tiny-tag
                v-for="tag in problemInfo.tags"
                :key="tag.id"
                style="margin-right: 5px; font-weight: bolder"
                color="grey"
                effect="light"
                type="info"
                >{{ tag.tagName }}
              </tiny-tag>
              <div style="margin-top: 10px">
                <tiny-tag effect="plain" style="margin-right: 5px"
                  >提交数
                  <tiny-divider direction="vertical" />
                  {{ problemInfo.submitCount }}
                </tiny-tag>
                <tiny-tag effect="plain" style="margin-right: 5px" type="success"
                  >通过数
                  <tiny-divider direction="vertical" />
                  {{ problemInfo.acceptCount }}
                </tiny-tag>
                <tiny-tag effect="plain" type="info"
                  >通过率
                  <tiny-divider direction="vertical" />
                  {{ problemInfo.acceptRate }}
                </tiny-tag>
              </div>
              <div style="margin-top: 10px; display: flex;align-items: center;">
                <tiny-button style="border-radius: 6px;flex-grow: 1;" type="primary"
                  ><TinyIconEdit/> 查看题目
                </tiny-button>
                <tiny-button type="warning" :icon="TinyIconStarO"></tiny-button>
              </div>
            </tiny-card>
          </tiny-col>
        </tiny-row>
      </tiny-layout>
    </tiny-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import {
  TinyCard,
  TinyLayout,
  TinyRow,
  TinyCol,
  TinySearch,
  TinyGrid,
  TinyGridColumn
} from '@opentiny/vue'
import { AutoTip } from '@opentiny/vue-directive'
import { iconEdit,iconStarO } from '@opentiny/vue-icon'
import { ProblemControllerService, type ProblemsetVO } from '@/api'
const tableLoading = ref(false)
const searchText = ref('')
const TinyIconEdit = iconEdit()
const TinyIconStarO = iconStarO()
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
const problemInfo = ref({
  title: 'Slim走迷宫',
  displayId: 'USTS-10Z',
  id: 1001,
  judgeMode: 'default',
  difficulty: 1900,
  description:
    '$Slim$是一个机器人，他有一个$100000^{2}$迷宫地图，地图上有一些障碍物，$Slim$只能向上下左右四个方向移动，他想要从起点移动到终点，但是他不知道怎么走，你是一位优秀的程序员，你能使用你的算法帮助$Slim$找到一条从起点到终点的最短路径吗？如果你能，那么你就是一个优秀的程序员！',
  tags: [
    {
      id: 1,
      tagName: 'DFS'
    },
    {
      id: 2,
      tagName: 'BFS'
    }
  ],
  acceptCount: 0,
  submitCount: 0,
  acceptRate: '64%'
})
const getProblemset = reactive({
  api: async ({ page }: any) => {
    tableLoading.value = true
    const { currentPage, pageSize } = page
    const { data } = await ProblemControllerService.listProblemsetVoByPageUsingGet(
      currentPage,
      pageSize,
      searchText.value
    )
    tableLoading.value = false
    return {
      result: data?.records as ProblemsetVO[],
      page: { total: data?.total }
    }
  }
})

const titleClass = ({ rowIndex, columnIndex }: { rowIndex: number; columnIndex: number }) => {
  if (rowIndex & 1 && !(columnIndex & 1)) {
    return 'col-orange'
  }
}
</script>

<style scoped>
.card-boarder {
  border: 1px solid #dbdbdb;
  border-radius: 5px;
}
:deep(.v-show-content) {
  padding: 10px 0 0 0 !important;
}
:deep(button path) {
  fill: #ffffff;
}
</style>
