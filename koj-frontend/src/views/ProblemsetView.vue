<template>
  <div id="problemsetView">
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
              ref="taskGrid"
              style="max-width: 100%"
              :fetch-data="fetchDataOption"
              :pager="pagerConfig"
              :loading="tableLoading"
              size="medium"
              :auto-resize="true"
              :auto-load="true"
            >
              <tiny-grid-column
                field="problem_id"
                title="ID"
                align="center"
                width="15%"
              ></tiny-grid-column>
              <tiny-grid-column field="title" title="标题" align="center"> </tiny-grid-column>
              <tiny-grid-column
                field="difficulty"
                title="难度"
                width="15%"
                align="center"
              ></tiny-grid-column>
              <tiny-grid-column
                field="acNumber"
                title="已通过"
                width="15%"
                align="center"
              ></tiny-grid-column>
            </tiny-grid>
          </tiny-col>
          <tiny-col :span="4">
            <tiny-card title="题目详情" custom-class="problem-detail" :auto-width="true">
                    123
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
  TinyGridColumn,
  TinySticky,
} from '@opentiny/vue'

const tableLoading = ref(false)

const pagerConfig = ref({
  attrs: {
    currentPage: 1,
    pageSize: 5,
    pageSizes: [5, 10],
    total: 0,
    align: 'right', // 可选值：['left', 'center', 'right']
    layout: 'total, sizes, prev, pager, next, jumper'
  }
})
const data = ref([
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  },
  {
    problem_id: 1,
    title: 'test',
    difficulty: 100,
    acNumber: 0,
    status: '0'
  }
])
const fetchDataOption = reactive({
  api: () => {
    tableLoading.value = true
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(data.value)
        tableLoading.value = false
      }, 1000)
    })
  }
})
const handleDelete = (id: number) => {
  console.log(id)
}
</script>

<style scoped>
#problemsetView {
}
.problem-detail {
  border: 1px solid #dbdbdb;
  border-radius: 5px;
}
</style>
