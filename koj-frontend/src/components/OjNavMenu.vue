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
        <user-button/>
      </template>
    </tiny-nav-menu>
  </div>
</template>

<script setup lang="ts">
import { iconPublicNotice } from '@opentiny/vue-icon'
import UserButton from './UserButton.vue'
import { ref, watchEffect, defineEmits } from 'vue'

const IconPublicNotice = iconPublicNotice()


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
defineProps([
  'modelValue'
])
const emit = defineEmits(['update:modelValue'])
</script>

<style scoped>
.black-title {
  font-size: 1.2em;
  font-weight: bolder;
  color: black;
}
:deep(.slot-logo) {
  margin-top: 5px;
}
</style>
