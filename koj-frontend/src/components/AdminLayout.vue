<template>
  <div>
    <tiny-container :aside-width="250" pattern="default" :header-height="50">
      <template #header>
        <tiny-nav-menu style="height: 50px; padding: 5px; z-index: 2">
          <!-- logo -->
          <template #logo>
            <tiny-image fit="fill" :src="getImg()" style="height: 40px" />
          </template>
          <!-- toolbar -->
          <template #toolbar>
            <tiny-dropdown>
              <tiny-image
                :src="user.avatar"
                fit="cover"
                alt="头像"
                style="width: 40px; height: 40px; border-radius: 50%; margin-right: 10px"
              />
              <template #dropdown>
                <tiny-dropdown-menu popper-class="my-class">
                  <tiny-dropdown-item>回到OJ主页</tiny-dropdown-item>
                  <tiny-dropdown-item>退出登录</tiny-dropdown-item>
                </tiny-dropdown-menu>
              </template>
            </tiny-dropdown>
          </template>
        </tiny-nav-menu>
      </template>
      <template #aside>
        <tiny-tree-menu
          style="height: 100%"
          width-adapt
          :data="menuData"
          :show-filter="false"
          :node-height="45"
          @node-click="handleMenuClick"
        >
        </tiny-tree-menu>
      </template>
      <div id="admin-content">
        <tiny-card style="width: 100%; height: 100%">
          <router-view />
        </tiny-card>
      </div>
    </tiny-container>
  </div>
</template>

<script setup lang="ts">
import useUserStore from '@/stores/user'
import { TinyModal } from '@opentiny/vue'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import { useRouter } from 'vue-router';

// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo-koj.png`, import.meta.url).href
}

// 用户数据
const userStore = useUserStore()
const { user } = storeToRefs(userStore)

const router = useRouter()

const handleMenuClick = (menu: any) => {
    router.push(`/admin/${menu.id}`)
}

const menuData = ref([
  {
    id: 'dashboard',
    label: '仪表板'
  },
  {
    id: 'settings',
    label: '系统设置'
  },
  {
    id: 30,
    label: '用户管理',
    children: [
      {
        id: 31,
        label: '用户列表'
      },
      {
        id: 32,
        label: '生成用户'
      }
    ]
  },
  {
    id: 'problem',
    label: '题目管理',
    children: [
      {
        id: 'problem-list',
        label: '题目列表'
      },
      {
        id: 'create-problem',
        label: '创建题目'
      },
      {
        id: 43,
        label: '标签管理'
      }
    ]
  }
])
</script>

<style scoped>
#admin-content {
  background: #f0f0f0;
  height: 100%;
  padding: 20px;
}
:deep(.tiny-tree-node__content::before) {
  border-left: 4px solid black !important;
}
</style>
