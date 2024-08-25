<template>
  <div id="homeHeaderComponent">
    <el-image
      class="header-image"
      :src="getImg()"/>
    <div class="header-right">
      <el-space>
        <el-icon>
          <Bell/>
        </el-icon>
        <el-divider direction="vertical"></el-divider>
        <el-icon>
          <Location/>
        </el-icon>
      </el-space>
      <div>
        <el-link type="primary" v-if="currentUser.username?.length" @click="goToUserHome">{{ currentUser.username }}</el-link>
        <el-link type="primary" v-else @click="goToRegister">Register</el-link>
        <el-divider direction="vertical"></el-divider>
        <el-link type="primary" v-if="currentUser.username?.length" @click="logout">Logout</el-link>
        <el-link type="primary" v-else @click="goToLogin">Login</el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useCurrentUserStore } from "@/stores/currentUser";
import { PassportControllerService, type UserInfoVO } from "@/api";
import { storeToRefs } from "pinia";
import { Bell, Location } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import router from "@/router";

function getImg() {
  return new URL(`../assets/logo.png`, import.meta.url).href;
}

const { clearCurrentUser } = useCurrentUserStore();
const { currentUser } = storeToRefs(useCurrentUserStore());

const logout = async (event: Event) => {
  event.preventDefault();
  await PassportControllerService.userLogoutUsingPost();
  clearCurrentUser();
  ElMessage.success("Logout success");
}

const goToRegister = () => {
  router.push("/register");
}

const goToLogin = () => {
  router.push("/login");
}

const goToUserHome = () => {
  router.push(`/user-home/${currentUser.value.username}`);
}

</script>

<style scoped>
#homeHeaderComponent {
  display: flex;
  justify-content: space-between;
}
.el-link {
  font-size: 0.8em;
}
.header-image {
  height: 70px;
  float: left;
}

.header-right {
  float: left;
  text-align: right;
  font-size: 20px;
}
</style>
