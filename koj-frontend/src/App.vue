<template>
  <div id="app">
    <HomeHeaderComponent/>
    <NavBarComponent/>
    <transition name="el-zoom-in-bottom">
      <router-view/>
    </transition>
    <HomeFooterComponent/>
  </div>
</template>

<script setup lang="ts">
import NavBarComponent from "@/components/NavBarComponent.vue";
import HomeHeaderComponent from "@/components/HomeHeaderComponent.vue";
import HomeFooterComponent from "@/components/HomeFooterComponent.vue";
import { HealthControllerService } from "@/api";

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  height: 100vh; /* 设置 #app 的高度为视口的高度 */
  display: flex; /* 使用 Flexbox 布局 */
  flex-direction: column; /* 设置 Flex items 沿着垂直方向排列 */
  padding-top: 0.5em;
  max-width: 1200px;
  min-width: 920px;
  margin: 0 auto;
}

.header-font {
  color: #3b5998;
  font-size: 1.2rem;
  font-weight: 600;
  font-family: verdana, arial, sans-serif;;
  position: relative;
}

.common-link {
  color: #00c;
  cursor: pointer;
  text-decoration: underline;
}

.black-first-letter::first-letter {
  color: #000000;
}
</style>
