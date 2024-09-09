<template>
  <div id="menuComponentView">
    <ul class="subNavBar">
      <li class="rightLava" :style="{ left: left + 'px', width: width + 'px' }">
        <div class="leftLava"/>
      </li>
      <li v-for="(item,index) in p.menuList"
          :key="index"
          @mouseover="move($event)"
          @mouseout="reset"
          @click="moveTo(index, $event)">
        <a>{{ item.meta?.menuName }}</a>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, toRaw, withDefaults } from "vue";
import type { RouteRecordRaw } from "vue-router";

interface Props {
  menuList: RouteRecordRaw[];
  selectedIndex: number;
}

const p = withDefaults(defineProps<Props>(), {
  menuList: () => [] ,
  selectedIndex: () => 0,
});


const router = useRouter();

// 动画参数
const left = ref(0);
const width = ref(0);
const leftOffset = ref(0);
const widthOffset = ref(0);

onMounted(async () => {
  // 渲染好a之后再框元素，防止宽度不正确
  await nextTick()
  // 获取当前选中的菜单元素
  const menuElements = document.querySelectorAll('li:not(.rightLava) a');
  if (menuElements) {
    const menuElement = menuElements[p.selectedIndex] as HTMLElement;
    left.value = menuElement.offsetLeft;
    width.value = menuElement.offsetWidth;
  }
});

// menu匹配当前路由
router.afterEach((to, from, failure) => {
  const index = p.menuList.findIndex(item => item.name === to.name);
  if (index !== -1) {
    const menuElements = document.querySelectorAll('li:not(.rightLava) a');
    if (menuElements) {
      const menuElement = menuElements[index] as HTMLElement;
      left.value = menuElement.offsetLeft;
      width.value = menuElement.offsetWidth;
    }
  }

});

// lava移动效果
const move = (event:MouseEvent) => {
  const liElement = (event.target as HTMLElement).closest('a');
  if (!liElement) return;
  leftOffset.value = left.value - liElement.offsetLeft;
  widthOffset.value = width.value - liElement.offsetWidth;
  left.value -= leftOffset.value;
  width.value -= widthOffset.value;
};

// lava移动到指定位置并路由跳转
const moveTo = async (index:number, event:MouseEvent) => {
  const liElement = (event.target as HTMLElement).closest('a');
  if (!liElement) return;
  await router.push({ name: p.menuList[index].name });
  leftOffset.value = left.value - liElement.offsetLeft;
  widthOffset.value = width.value - liElement.offsetWidth;
  left.value -= leftOffset.value;
  width.value -= widthOffset.value;
  left.value -= leftOffset.value;
  width.value -= widthOffset.value;
};

// lava重置效果
const reset = () => {
  left.value += leftOffset.value;
  width.value += widthOffset.value;
};

</script>

<style scoped>
#menuComponentView {

}

.subNavBar {
  position: relative;
  overflow: hidden;
  list-style: none;
  padding-left: 0;
  user-select: none;
}

.rightLava {
  background: url("https://codeforces.org/s/54774/images/menu/lava-right.png") no-repeat top right;
  height: 20px;
  z-index: 1;
  transition: left 0.3s ease-in-out, width 0.3s ease-in-out;
  position: absolute;
  width: 20px;
}

.leftLava {
  background: url("https://codeforces.org/s/54774/images/menu/lava-left.png") no-repeat top left;
  z-index: 1;
  height: 20px;
  margin-right: 10px;
}

a {
  cursor: pointer;
  position: relative;
  overflow: hidden;
  text-decoration: none;
  text-transform: uppercase;
  font-family: "Arial Narrow", sans-serif;
  font-weight: 700;
  font-size: 1rem;
  color: #000;
  outline: none;
  text-align: center;
  user-select: none;
  line-height: 20px;
  height: 20px;
  z-index: 2;
  letter-spacing: 0;
  float: left;
  display: block;
  margin: auto 8px;
  padding: 0 5px;
  transition: text-shadow 100ms ease-in-out,transform 100ms ease-in-out;
}

a:first-child {
  margin: auto 8px auto 0;
}

a:active {
  text-shadow: white 2px 2px;
  transform: translateX(-1px) translateY(-1px);
}

</style>
