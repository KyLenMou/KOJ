<template>
  <div>
    <tiny-card style="width: 100%" class="problemsetCard">
      <template #title>
        <span class="oj-card-title" style="margin: 0 2px 10px 0">题目集</span>
        <tiny-popover
          trigger="hover"
          width="250"
          content="点击列表内题目查看详情信息，点击题目ID或标题进入题目详情页面"
        >
          <template #reference>
            <IconHelpQuery />
          </template>
        </tiny-popover>
      </template>
      <tiny-layout :col="12">
        <tiny-row>
          <tiny-col :xl="8" :lg="8" :sm="12">
            <tiny-layout :cols="24" style="margin-bottom: 10px">
              <tiny-row :gutter="10">
                <tiny-col :span="8">
                  <tiny-search clearable placeholder="请输入关键词" />
                </tiny-col>
                <tiny-col :span="8">
                  <tiny-select
                    v-model="searchTagIds"
                    :show-alloption="false"
                    clearable
                    multiple
                    filterable
                    placeholder="标签"
                  >
                    <template #prefix>
                      <IconPushpin />
                    </template>
                    <tiny-option
                      v-for="option in tagOptions"
                      :key="option.id"
                      :label="option.tagName"
                      :value="option.id"
                    />
                  </tiny-select>
                </tiny-col>
                <tiny-col :span="7">
                  <tiny-select
                    v-model="searchDifficulty"
                    :show-alloption="false"
                    clearable
                    multiple
                    placeholder="难度"
                    collapse-tags
                  >
                    <template #prefix>
                      <IconGrade />
                    </template>
                    <tiny-option
                      v-for="d in difficultyTagArray"
                      :key="d.difficulty"
                      :label="d.difficulty"
                      :value="d.difficulty"
                      :style="`color:` + d.color"
                      class="difficulty-div"
                    />
                  </tiny-select>
                </tiny-col>
                <tiny-col :span="1">
                  <tiny-popover width="400" trigger="click">
                    <template #reference>
                      <div class="white-svg-button">
                        <tiny-button type="primary" :icon="IconBefilter"></tiny-button>
                      </div>
                    </template>
                    <template #default>
                      <tiny-form label-position="top" style="padding: 5px">
                        <tiny-form-item label="关于我的">
                          <tiny-radio-group v-model="searchAboutMe">
                            <tiny-radio-button label="1">我做过的</tiny-radio-button>
                            <tiny-radio-button label="2">我通过的</tiny-radio-button>
                            <tiny-radio-button label="3">未通过的</tiny-radio-button>
                          </tiny-radio-group>
                          <tiny-button
                            type="text"
                            :icon="IconConmentRefresh"
                            size="mini"
                            @click="searchAboutMe = ''"
                          />
                        </tiny-form-item>
                        <tiny-form-item label="评测模式">
                          <tiny-checkbox-group v-model="searchJudgeMode">
                            <tiny-checkbox-button label="default">默认评测</tiny-checkbox-button>
                            <tiny-checkbox-button label="spj">特殊评测</tiny-checkbox-button>
                            <tiny-checkbox-button label="interact">交互评测</tiny-checkbox-button>
                          </tiny-checkbox-group>
                        </tiny-form-item>
                        <tiny-form-item label="难度范围">
                          <tiny-slider
                            v-model="searchDifficultyRange"
                            :min="800"
                            :max="3000"
                            :step="100"
                          ></tiny-slider
                        ></tiny-form-item>
                        <tiny-form-item label="通过率">
                          <tiny-slider v-model="searchAcceptRate" :min="0" :max="100"></tiny-slider
                        ></tiny-form-item>
                      </tiny-form>
                    </template>
                  </tiny-popover>
                </tiny-col>
              </tiny-row>
            </tiny-layout>
            <tiny-row>
              <tiny-grid
                highlight-current-row
                @current-change="handleCurrentRow"
                :fetch-data="getProblemset"
                :pager="pagerConfig"
                :loading="problemsetLoading"
                :auto-resize="true"
                :resizable="false"
                :auto-load="true"
              >
                <tiny-grid-column field="displayId" title="题目ID" align="center" width="15%">
                  <template #default="{ row }">
                    <tiny-link type="primary" @click="goToProblem(row.displayId)">{{
                      row.displayId
                    }}</tiny-link>
                  </template>
                </tiny-grid-column>
                <tiny-grid-column field="title" title="标题" align="center">
                  <template #default="{ row }">
                    <tiny-link
                      type="primary"
                      style="font-weight: bold; font-size: 1.1em"
                      @click="goToProblem(row.displayId)"
                      >{{ row.title }}
                    </tiny-link>
                  </template>
                </tiny-grid-column>
                <tiny-grid-column field="difficulty" title="难度" width="15%" align="center">
                  <template #default="{ row }">
                    <difficulty-div :difficulty="row.difficulty" />
                  </template>
                </tiny-grid-column>
              </tiny-grid>
            </tiny-row>
          </tiny-col>
          <tiny-col :xl="4" :lg="4" :sm="12">
            <tiny-card id="problem-info-card" custom-class="card-boarder" :auto-width="true">
              <div style="display: flex; justify-content: space-between">
                <div>
                  <div>
                    <span class="oj-card-title">{{ problemInfo.title }}</span>
                    <span>{{ ' #' + problemInfo.id }}</span>
                  </div>
                  <span> {{ problemInfo.displayId }}</span>
                </div>
                <div>
                  <judge-mode-tag :judgeMode="problemInfo.judgeMode" effect="dark" />
                </div>
              </div>
              <md-preview :text="problemInfo.description" />
              <div class="oj-card-subtitle">标签</div>
              <difficulty-tag :difficulty="problemInfo.difficulty" style="margin-right: 5px" />
              <tiny-tag
                v-for="tagId in problemInfo.tags"
                :key="tagId"
                style="margin-right: 5px; font-weight: bolder"
                color="grey"
                effect="light"
                type="info"
                >{{ getTagName(tagId) }}
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
              <div
                class="white-svg-button"
                style="margin-top: 10px; display: flex; align-items: center"
              >
                <tiny-button
                  style="border-radius: 6px; flex-grow: 1"
                  type="primary"
                  @click="goToProblem(problemInfo.displayId)"
                >
                  <TinyIconEdit /> 查看题目
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
import { difficultyTagArray } from '@/utils'
import { onMounted, reactive, ref } from 'vue'
import {
  TinyCard,
  TinyLayout,
  TinyRow,
  TinyCol,
  TinySearch,
  TinyGrid,
  TinyGridColumn,
  TinyLoading
} from '@opentiny/vue'
import { iconBefilter, iconPushpin, iconGrade, iconConmentRefresh } from '@opentiny/vue-icon'
import { iconEdit, iconStarO, iconHelpQuery } from '@opentiny/vue-icon'
import {
  ProblemControllerService,
  TagControllerService,
  type ProblemInfoCardVO,
  type ProblemsetVO,
  type Tag
} from '@/api'
import router from '@/router'
const IconBefilter = iconBefilter()
const IconPushpin = iconPushpin()
const IconGrade = iconGrade()
const IconHelpQuery = iconHelpQuery()
const IconConmentRefresh = iconConmentRefresh()
const searchText = ref('')
const searchTagIds = ref([])
const searchDifficulty = ref([])
const searchJudgeMode = ref(['default', 'spj', 'interact'])
const searchAboutMe = ref('')
const searchDifficultyRange = ref([800, 3000])
const searchAcceptRate = ref([0, 100])
const TinyIconEdit = iconEdit()
const TinyIconStarO = iconStarO()
const problemInfoCardLoading = ref()
const problemsetLoading = ref(false)

const goToProblem = (displayId: string | any) => {
  // 跳转到/problem/:id
  router.push({ name: 'Problem', params: { id: displayId } })
}

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

const problemInfo = ref<ProblemInfoCardVO>({
  title: '题目标题',
  displayId: '题目ID',
  id: 0,
  judgeMode: 'default',
  difficulty: 1200,
  description: '',
  tags: [0],
  acceptCount: 0,
  submitCount: 0,
  acceptRate: '0%'
})
const handleCurrentRow = async ({ row }: any) => {
  await getProblemInfoCard(row.id)
}
// 根据tagId在problemTags里面找
const getTagName = (tagId: number) => {
  return problemTagMap.value.get(tagId) || '未知标签'
}

const problemTagMap = ref<Map<number, string>>(new Map())

const getProblemset = reactive({
  api: async ({ page }: any) => {
    problemsetLoading.value = true
    const { currentPage, pageSize } = page
    const { data } = await ProblemControllerService.listProblemsetVoByPageUsingGet(
      currentPage,
      pageSize,
      undefined,
      undefined,
      undefined,
      undefined
    )
    problemsetLoading.value = false
    if (data?.records?.length && data.records[0].id !== undefined) {
      getProblemInfoCard(data.records[0].id.toString())
    }
    return {
      result: data?.records as ProblemsetVO[],
      page: { total: data?.total }
    }
  }
})

const getProblemInfoCard = async (problemId: string) => {
  problemInfoCardLoading.value = TinyLoading.service({
    target: document.getElementById('problem-info-card'),
    size: 'medium',
    background: 'rgba(0, 0, 0, 0.2)'
  })
  const { data, code } = await ProblemControllerService.getProblemInfoCardUsingGet(problemId)
  if (code) {
    problemInfoCardLoading.value.close()
    return
  }
  problemInfo.value = data as ProblemInfoCardVO
  problemInfoCardLoading.value.close()
}

onMounted(async () => {
  await getTagList()
})
const tagOptions = ref<Tag[]>([])
const getTagList = async () => {
  const { code, data } = await TagControllerService.getTagListUsingGet()
  if (code) return
  tagOptions.value = data as Tag[]
  data?.forEach((tag: Tag) => {
    problemTagMap.value.set(tag.id as number, tag.tagName as string)
  })
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
:deep(.white-svg-button button path) {
  fill: #ffffff;
}
:deep(.tiny-grid-body__row) {
  cursor: pointer;
}

:deep(.tiny-input__inner) {
  border: 1px solid #c2c2c2;
  background-color: #ffffff;
}
.tiny-textarea {
  --tv-Textarea-border-color: none;
}
:deep(.tiny-filter-box) {
  height: 30px;
}
:deep(.tiny-checkbox-button.is-checked .tiny-checkbox-button__inner) {
  color: #000;
  border-color: #000;
}
:deep(.tiny-slider__range) {
  background-color: #7c7c7c;
}
:deep(.tiny-radio-button__orig-radio:checked + .tiny-radio-button__inner) {
  background-color: #000;
}
:deep(.tiny-checkbox-button.is-checked:after) {
  border-right: 20px solid #000;
}
</style>
