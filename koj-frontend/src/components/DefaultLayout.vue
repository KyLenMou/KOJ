<template>
  <div id="defaultLayout">
    <tiny-nav-menu ref="navMenu" :data="menuData" style="height: 50px">
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
        <tiny-user-head type="icon" round min></tiny-user-head>
      </template>
    </tiny-nav-menu>
    <tiny-layout class="content-and-footer">
      <tiny-row id="oj-content">
        <!-- <router-view v-slot="{ Component }" v-if="!isNavMenuLoading"> -->
          <transition name="cool-transition">
            <router-view/>
            <!-- <component :is="Component" /> -->
          </transition>
        <!-- </router-view> -->
      </tiny-row>
      <tiny-row class="layout-footer">
        <span> KOJ KCode Online Judge ©2024 Created by KyLen </span>
      </tiny-row>
    </tiny-layout>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick, watchEffect } from 'vue'
import { iconPublicNotice } from '@opentiny/vue-icon'
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

// 等待菜单加载完成后再显示内容
const navMenu = ref(null)
const isNavMenuLoading = ref(true)
watchEffect(() => {
  if (navMenu.value) {
    isNavMenuLoading.value = false
  }
})
</script>
<style scoped>
#defaultLayout {
  background-color: #f7f7f7;
  display: flex;
  flex-direction: column;
  height: 100vh; /* 设置布局高度为视口高度 */
}
#oj-content {
  padding: 1em 15%;
  overflow-y: auto; /* 仅内容区域可垂直滚动 */
  min-height: 90vh; /* 防止因最小高度导致的溢出 */
}
:deep(.tiny-nav-menu) {
  z-index: 1000;
}
.cool-transition-enter-active,
.cool-transition-leave-active {
  transition:
    opacity 0.3s cubic-bezier(0.68, -0.55, 0.27, 1.55),
    transform 0.5s cubic-bezier(0.68, -0.55, 0.27, 1.55);
}
.cool-transition-enter-from,
.cool-transition-leave-to {
  opacity: 0;
  transform: translateY(10px) scale(0.95);
}
.cool-transition-enter-to,
.cool-transition-leave-from {
  opacity: 1;
  transform: translateY(0) scale(1);
}
:deep(.slot-toolbar) {
  display: flex;
  align-items: center;
  height: 100%;
}

:deep(.tiny-nav-menu) {
  width: 100%;
  margin: 0 auto;
  padding: 0 15%;
}

:deep(.slot-logo) {
  margin-top: 5px;
}

.content-and-footer {
  overflow-y: auto;
}

.layout-footer {
  margin-top: auto;
  padding: 1rem 2.5rem;
  font-weight: 400;
  font-size: 1rem;
  line-height: 1;
  color: #6e6e6e;
  background-color: #ffffff;
  text-align: center;
  box-shadow: 0 -1px 12px rgba(0, 0, 0, 0.1);
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: #bdbdbd;
}

::-webkit-scrollbar-thumb:hover {
  border-radius: 4px;
  background-color: #939393;
}
</style>
