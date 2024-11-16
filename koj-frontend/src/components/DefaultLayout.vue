<template>
  <div id="defaultLayout">
    <DefaultNavMenu v-model="isNavMenuInit"/>
    <tiny-layout class="content-and-footer" v-if="isNavMenuInit">
      <tiny-row id="oj-content">
        <transition name="cool-transition">
          <router-view v-slot="{ Component, route }">
            <component :is="Component" :key="route.fullPath" />
          </router-view>
        </transition>
      </tiny-row>
      <tiny-row class="layout-footer">
        <span> KOJ KCode Online Judge ©2024 Created by KyLen </span>
      </tiny-row>
    </tiny-layout>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick, watchEffect, watch } from 'vue'
const isNavMenuInit = ref(false) 

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
