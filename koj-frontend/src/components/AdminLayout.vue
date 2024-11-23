<template>
  <div>
    <tiny-container :aside-width="200" pattern="default" :header-height="50">
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
                  <tiny-dropdown-item @click="goToHome">回到主页</tiny-dropdown-item>
                  <tiny-dropdown-item>退出登录</tiny-dropdown-item>
                </tiny-dropdown-menu>
              </template>
            </tiny-dropdown>
          </template>
        </tiny-nav-menu>
      </template>
      <template #aside>
        <tiny-tree-menu
          ref="tree"
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
        <tiny-card class="admin-content-card">
          <router-view />
        </tiny-card>
      </div>
    </tiny-container>
  </div>
</template>

<script setup lang="ts">
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { TreeMenu as tinyTreeMenu } from '@opentiny/vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo-koj.png`, import.meta.url).href
}
const router = useRouter()
const goToHome = () => {
  router.push('/')
}

// 用户数据
const userStore = useUserStore()
const { user } = storeToRefs(userStore)

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
    id: 'user',
    label: '用户管理'
  },
  {
    id: 'problemset',
    label: '题目管理',
  },
  {
    id: 'problem',
    label: '新增题目',
  },
  {
    id: 'tag',
    label: '标签管理'
  },
  {
    id: 'contest',
    label: '比赛管理'
  },
])

</script>

<style scoped>
#admin-content {
  background: #f0f0f0;
  height: inherit;
  padding: 20px;
}
.admin-content-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: inherit;
  min-height: 90vh;
}
</style>
