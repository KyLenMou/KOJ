<template>
  <div id="adminView">
    <menu-component :selected-index="selectedIndex" :menu-list="menuList" />
    <router-view />
  </div>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
const router = useRouter();
const updateMenuItems = () => {
  menuList.value = routes[0].children
    ? routes[0].children.map(r => r.name as string)
    : [] as string[];
}
onMounted(() => {
  updateMenuItems();
  router.push({ name: menuList.value[0] });
})

const menuList = ref(['loading_menu']);
const selectedIndex = ref(0);

</script>

<style scoped>
#adminView {

}
</style>
