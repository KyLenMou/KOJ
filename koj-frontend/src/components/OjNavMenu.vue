<template>
  <div>
    <tiny-nav-menu :data="menuData" style="height: 50px" ref="navMenu">
      <!-- logo -->
      <template #logo>
        <tiny-image fit="fill" :src="getImg()" style="height: 40px" />
      </template>
      <!-- toolbar -->
      <template #toolbar>
        <tiny-layout style="width: 200px">
          <tiny-search mini placeholder="请输入关键词"></tiny-search>
        </tiny-layout>
        <tiny-divider direction="vertical"></tiny-divider>
        <tiny-badge is-dot :hidden="true">
          <template #default>
            <tiny-button :icon="IconPublicNotice" circle></tiny-button>
          </template>
        </tiny-badge>
        <tiny-divider direction="vertical"></tiny-divider>
        <tiny-popover
          v-if="user.id?.length"
          width="300"
          trigger="click"
          :visible-arrow="false"
          :offset="-50"
        >
          <template #default>
            <tiny-layout class="avatar-menu-container">
              <tiny-row :flex="true" align="top">
                <tiny-image
                  :src="user.avatar"
                  fit="cover"
                  alt="头像"
                  style="width: 50px; height: 50px; border-radius: 50%; margin-right: 10px"
                />
                <div class="black-title">
                  {{ user.username }}
                </div>
              </tiny-row>
              <tiny-row>
                <div style="display: flex">
                  <span>过题数</span>
                  <span style="margin-left: auto">57257/114514</span>
                </div>
                <tiny-progress
                  :stroke-width="8"
                  percentage="50"
                  :show-text="false"
                  color="black"
                ></tiny-progress>
              </tiny-row>
              <tiny-row>
                <div style="display: flex; justify-content: space-between">
                  <div class="avatar-inner-box">
                    <div class="black-title">114514</div>
                    <span>提交</span>
                  </div>
                  <div class="avatar-inner-box">
                    <div class="black-title">1919810</div>
                    <span>收藏</span>
                  </div>
                </div>
              </tiny-row>
              <tiny-row>
                <tiny-button class="avatar-inner-btn" @click="goTo('user')">
                  <img src="@/assets/images/userhome.svg" width="18" /> 我的主页
                </tiny-button>
              </tiny-row>
              <tiny-row>
                <tiny-button class="avatar-inner-btn" @click="goTo('user/settings')">
                  <img src="@/assets/images/usersetting.svg" width="18" /> 我的设置
                </tiny-button>
              </tiny-row>
              <tiny-row>
                <tiny-button class="avatar-inner-btn" @click="goTo('admin')">
                  <img src="@/assets/images/admin.svg" width="18" /> 后台管理
                </tiny-button>
              </tiny-row>
              <tiny-row>
                <tiny-button class="avatar-inner-btn" @click="logout">
                  <img src="@/assets/images/logout.svg" width="18" /> 退出登录
                </tiny-button>
              </tiny-row>
            </tiny-layout>
          </template>
          <template #reference>
            <tiny-user-head type="icon" round min style="cursor: pointer" />
          </template>
        </tiny-popover>
        <tiny-button v-else @click="openLoginForm"> 登录/注册 </tiny-button>
      </template>
    </tiny-nav-menu>
  </div>
</template>

<script setup lang="ts">
import { useDialogStore } from '@/stores/dialog'
import useUserStore from '@/stores/user'
import { iconPublicNotice } from '@opentiny/vue-icon'
import { storeToRefs } from 'pinia'
const IconPublicNotice = iconPublicNotice()
import { onMounted, ref, watchEffect, defineEmits } from 'vue'
import { useRouter } from 'vue-router';

// 用户数据
const userStore = useUserStore()
const { user } = storeToRefs(userStore)
// 菜单跳转
const router = useRouter()
const goTo = (url: string) => {
  router.push(`/${url}`)
}

// 退出登录
const logout = async () => {
  // 注意，后台会返回提示信息并且显示，不需要额外处理
  await userStore.logout()
}

// 登录对话框
const dialogStore = useDialogStore()
const openLoginForm = () => {
  dialogStore.showPassportDialogVisible()
}

// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo-koj.png`, import.meta.url).href
}

// 菜单数据
const menuData = ref([
  {
    title: '首页',
    url: '/home'
  },
  {
    title: '题目集',
    url: '/problemset'
  },
  {
    title: '在线调试',
    url: '/debug'
  },
  {
    title: '评测队列',
    url: '/queue'
  }
])

// 先让导航栏加载完成
const navMenu = ref(null)
watchEffect(() => {
  if (navMenu.value) {
    emit('update:modelValue', true)
  }
})
const props = defineProps([
  'modelValue' // 接收父组件使用 v-model 传进来的值，必须用 modelValue 这个名字来接收
])
const emit = defineEmits(['update:modelValue']) // 必须用 update:modelValue 这个名字来通知父组件修改值
</script>

<style scoped>
.black-title {
  font-size: 1.2em;
  font-weight: bolder;
  color: black;
}
.avatar-menu-container > .tiny-row {
  margin-bottom: 10px;
}
.avatar-inner-box {
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
  justify-items: center;
  width: 40%;
}
.avatar-inner-btn {
  width: 100%;
  border-radius: 6px;
  border-radius: 6px;
  border-color: #e6e6e6 !important;
}
</style>
