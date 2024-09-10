<template>
  <div id="problemSubmissionView">
    <div v-if="!isFullScreen">
      <menu-component :menu-list="menuList" :selected-index="selectedIndex"/>
      <router-view/>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { routes } from "@/router/routes";
import type { RouteRecordRaw } from "vue-router";
const isFullScreen = ref(false);
const router = useRouter();

const updateMenuItems = () => {
  menuList.value = routes[1].children?.filter(r => r.meta?.isVisible) as RouteRecordRaw[];
}
onMounted(() => {
  updateMenuItems();
  // 默认跳转到第一个
  router.push({ name: menuList.value[0].name });
})

const menuList = ref<RouteRecordRaw[]>([]);
const selectedIndex = ref(0);

</script>

<style scoped>
#problemSubmissionView {
}

</style>
