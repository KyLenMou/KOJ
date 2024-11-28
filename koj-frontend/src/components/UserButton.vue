<template>
  <div>
    <tiny-popover v-if="user.id?.length"  width="300" trigger="click" :visible-arrow="false" :offset="-50">
      <template #default>
        <tiny-layout class="avatar-menu-container">
          <tiny-row :flex="true" align="top">
            <tiny-image
              :src="user.avatar"
              fit="cover"
              alt="头像"
              style="width: 50px; height: 50px; border-radius: 50%; margin-right: 10px"
            />
            <div class="black-title">
              {{ user.username }}
            </div>
          </tiny-row>
          <tiny-row>
            <div style="display: flex">
              <span>过题数</span>
              <span style="margin-left: auto">57257/114514</span>
            </div>
            <tiny-progress
              :stroke-width="8"
              percentage="50"
              :show-text="false"
              color="black"
            ></tiny-progress>
          </tiny-row>
          <tiny-row>
            <div style="display: flex; justify-content: space-between">
              <div class="avatar-inner-box">
                <div class="black-title">114514</div>
                <span>提交</span>
              </div>
              <div class="avatar-inner-box">
                <div class="black-title">1919810</div>
                <span>收藏</span>
              </div>
            </div>
          </tiny-row>
          <tiny-row>
            <tiny-button class="avatar-inner-btn" @click="goTo('user')">
              <img src="@/assets/images/userhome.svg" width="18" /> 我的主页
            </tiny-button>
          </tiny-row>
          <tiny-row>
            <tiny-button class="avatar-inner-btn" @click="goTo('user/settings')">
              <img src="@/assets/images/usersetting.svg" width="18" /> 我的设置
            </tiny-button>
          </tiny-row>
          <tiny-row>
            <tiny-button class="avatar-inner-btn" @click="openAdmin">
              <img src="@/assets/images/admin.svg" width="18" /> 后台管理
            </tiny-button>
          </tiny-row>
          <tiny-row>
            <tiny-button class="avatar-inner-btn" @click="logout">
              <img src="@/assets/images/logout.svg" width="18" /> 退出登录
            </tiny-button>
          </tiny-row>
        </tiny-layout>
      </template>
      <template #reference>
        <tiny-user-head type="icon" round min style="cursor: pointer" />
      </template>
    </tiny-popover>
    <tiny-button v-else @click="openLoginForm"> 登录/注册 </tiny-button>
  </div>
</template>

<script setup lang="ts">
import { useDialogStore } from '@/stores/dialog';
import useUserStore from '@/stores/user';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

// 用户数据
const userStore = useUserStore()
const { user } = storeToRefs(userStore)

// 菜单跳转
const router = useRouter()
const goTo = (url: string) => {
  router.push(`/${url}`)
}

// 退出登录
const logout = async () => {
  // 注意，后台会返回提示信息并且显示，不需要额外处理
  await userStore.logout()
}

// 登录对话框
const dialogStore = useDialogStore()
const openLoginForm = () => {
  dialogStore.showPassportDialogVisible()
}

// Open admin page
const openAdmin = () => {
  window.open('/admin', '_blank');
}
</script>

<style scoped>

.avatar-menu-container > .tiny-row {
  margin-bottom: 10px;
}
.avatar-inner-box {
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
  justify-items: center;
  width: 40%;
}
.avatar-inner-btn {
  width: 100%;
  border-radius: 6px;
  border-radius: 6px;
  border-color: #e6e6e6 !important;
}
</style>
