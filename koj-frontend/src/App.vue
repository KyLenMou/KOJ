<template>
  <div id="app">
    <HomeHeaderComponent/>
    <NavBarComponent/>
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component"/>
      </transition>
    </router-view>
    <HomeFooterComponent/>
  </div>
</template>

<script setup lang="ts">
import NavBarComponent from "@/components/NavBarComponent.vue";
import HomeHeaderComponent from "@/components/HomeHeaderComponent.vue";
import HomeFooterComponent from "@/components/HomeFooterComponent.vue";
import { useCurrentUserStore } from "@/stores/currentUser";

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
  max-width: 1200px;
  min-width: 920px;
  margin: 0 auto;
}

h1 {
  font-weight: normal;
  margin: 10px;
}

h2 {
  font-weight: normal;
  margin: 10px;
}

h3 {
  margin: 5px;
}

.el-select {
  min-width: 100px;
}
.space-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.space-around {
  display: flex;
  justify-content: space-around;
}
.title-header {
  line-height: 1.5em;
  font-size: 1.3rem;
  font-weight: 600;
  color: #3b5998;
}
.commonBox {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 7px;
  margin-bottom: 20px;
}
.common-margin {
  margin: 10px;
}
.common-margin-bottom {
  margin-bottom: 10px;
}

.fade-enter-active, .fade-leave-active {
  transform: scaleY(1);
  transition: transform 250ms cubic-bezier(0.23, 1, 0.32, 1), opacity 250ms cubic-bezier(0.23, 1, 0.32, 1);
  transform-origin: center bottom;
}

.fade-enter-from, .fade-leave-to {
  transform: scaleY(0.5);
  opacity: 0;
}

.fade-enter-to, .fade-leave-from {
  opacity: 1;
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

::-webkit-scrollbar {
  width: 6px;
  height: 12px;
  -webkit-box-shadow: inset 0 0 6px rgb(0 0 0 / 10%);
}

::-webkit-scrollbar-thumb {
  display: block;
  min-height: 12px;
  min-width: 6px;
  border-radius: 8px;
  background-color: #bbb;
}

::-webkit-scrollbar-thumb:hover {
  display: block;
  min-height: 12px;
  min-width: 6px;
  border-radius: 6px;
  background-color: rgb(159, 159, 159);
}
</style>
