<template>
  <div id="adminView">
    <menu-component :selected-index="selectedIndex" :menu-list="menuList" />
    <router-view />
  </div>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import type { RouteRecordRaw } from "vue-router";
const router = useRouter();
const updateMenuItems = () => {
  menuList.value = routes[0].children?.filter(r => r.meta?.isVisible) as RouteRecordRaw[];
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
#adminView {

}
</style>
