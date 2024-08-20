<template>
  <div id="navBarComponentView">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
    >
      <el-menu-item v-for="item in navItems" :key="item" :index="item" @click="doMenuClick(item)">
        {{ item }}
      </el-menu-item>
      <el-input style="width: 200px; position: absolute; right: 10px; top: 12px;"
                v-model="searchContent"
                :prefix-icon="Search"
                size="small">
      </el-input>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import { routes } from "@/router/routes";

const router = useRouter();

const navItems = routes.filter(r => {
  return r.meta?.showOnNav !== false;
}).map(r => {
  return r.name as string;
})

// menu匹配当前路由
router.afterEach((to, from, failure) => {
  activeIndex.value = to.name as string;
});

// 点击menu跳转
const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const activeIndex = ref('');
const searchContent = ref<string>("");
</script>

<style scoped>
#navBarComponentView {

}

.search-item {

}
:deep(.el-input__wrapper) {
  background-color: #f4f4f4;
}

:deep(.el-menu-item.is-active) {
  border-bottom: 3px solid #3b5998;
  color: #000 !important;
  background-color: #fff !important;
}

:deep(.el-menu-item) {
  color: #000 !important;
  font-family: "Calibri Light", sans-serif;
  font-weight: bold;
  font-size: 1.2em;
  outline: none;
  text-transform: uppercase;
  padding: 0;
  margin-right: 20px;
  line-height: 0px;
  user-select: none;
  background-color: #fff !important;
}
:deep(.el-menu-item:hover) {
  background-color: #fff !important;
}

:deep(.el-menu-item:first-child) {
  margin-left: 10px;
}

:deep(.el-menu--horizontal.el-menu) {
  border: 1px solid #b9b9b9;
  border-radius: 6px;
  padding: 0.5em;
}
:deep(.el-menu--horizontal) {
  height: 3.5em;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #ccc;
  padding: 5px;
  border-radius: 7px;
}

.nav-menu {
  list-style: none;
  display: flex;
  padding-left: 0;
  margin: 8px 0;
}

.nav-item {
  margin-left: 20px;
  position: relative;
  font-family: "Calibri Light", sans-serif;
  font-size: 15px;
}

.nav-item span {
  color: black;
  font-weight: bold;
}

.nav-item.active span {
  border-bottom: 3px solid #3b5998;
}

.nav-item:hover {
  background-color: #d2d2d2;
  transition: background-color 0.3s ease;
  cursor: pointer;
}

</style>
