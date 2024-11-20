<template>
  <div id="defaultNavMenu">
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
        <tiny-dropdown v-if="user.id?.length">
          <tiny-user-head type="icon" round min></tiny-user-head>
          <template #dropdown>
            <tiny-dropdown-menu>
              <tiny-dropdown-item @click="logout">退出登录</tiny-dropdown-item>
            </tiny-dropdown-menu>
          </template>
        </tiny-dropdown>
        <tiny-button v-else @click="openLoginForm"> 登录/注册 </tiny-button>
      </template>
    </tiny-nav-menu>
  </div>
</template>

<script setup lang="ts">
import { useDialogStore } from '@/stores/dialog'
import useUserStore from '@/stores/user'
import { TinyModal } from '@opentiny/vue'
import { iconPublicNotice } from '@opentiny/vue-icon'
import { storeToRefs } from 'pinia'
const IconPublicNotice = iconPublicNotice()
import { onMounted, ref, watchEffect, defineEmits } from 'vue'

// 用户数据
const userStore = useUserStore()
const { user } = storeToRefs(userStore)

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
#navMenu {
}
</style>
