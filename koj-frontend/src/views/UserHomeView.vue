<template>
  <div id="userHome">
    <menu-component :menu-list="menuList" :selected-index="selectedIndex"/>
  </div>
</template>

<script setup lang="ts">
import MenuComponent from "@/components/MenuComponent.vue";
import { storeToRefs } from "pinia";
import { useCurrentUserStore } from "@/stores/currentUser";

const { currentUser } = storeToRefs(useCurrentUserStore());

const menuList = ref([currentUser.value.username as string, 'Blog', 'Teams', 'Submissions', 'Groups', 'Contests']);
const selectedIndex = ref(0);


</script>

<style scoped>
#userHome {

}
</style>
