<template>
  <div id="userLoginView">
    <common-card head="Login into KOJ" class="login-card">
      <template #content>
        <el-form
          class="login-form"
          label-position="right"
          label-width="auto"
          :model="formData"
        >
          <el-form-item label="Username/Email">
            <el-input :prefix-icon="User" v-model="formData.username"/>
          </el-form-item>
          <el-form-item label="Password">
            <el-input :prefix-icon="Lock" v-model="formData.password" type="password" show-password/>
          </el-form-item>
          <el-form-item label=" ">
            <el-checkbox v-model="formData.remember">
              Remember me for a month
            </el-checkbox>
          </el-form-item>
          <el-form-item label=" ">
            <el-button @click="doLogin" type="primary">Login</el-button>
            <el-button type="primary" link style="margin-left: auto">Forgot your password?
            </el-button>
          </el-form-item>
        </el-form>
      </template>
      <template #foot>
        <el-tooltip
          content="Login with QQ"
          placement="top"
        >
          <tencent-qq class="third-party-icon" theme="outline" size="24" fill="#333" style="margin-right: 10px"/>
        </el-tooltip>
        <el-tooltip
          content="Login with Github. First time login will auto register."
          placement="top"
        >
          <github-one class="third-party-icon" theme="outline" size="24" fill="#333" @click="goToGithubLogin" />
        </el-tooltip>
      </template>
    </common-card>
  </div>
</template>

<script setup lang="ts">
import { PassportControllerService, type UserLoginDTO } from "@/api";
import { User, Lock } from "@element-plus/icons-vue";
import { useCurrentUserStore } from "@/stores/currentUser";
import { ElMessage } from "element-plus";
import { TencentQq, GithubOne } from '@icon-park/vue-next';

const router = useRouter();
const {getCurrentUser, setCurrentUser} = useCurrentUserStore();

onMounted(() => {
  if (getCurrentUser().username) {
    ElMessage.success("You have already logged in")
    router.push("/home");
  }
})

// 登录表单数据
const formData = reactive<UserLoginDTO>({
  username: '',
  password: '',
  remember: true
})

// 登录操作
const doLogin = async () => {
  await PassportControllerService.userLoginUsingPost(formData);
  await setCurrentUser()
  ElMessage.success("Login success")
  await router.push("/home")
}

// 跳转到Github登录 todo 后端配置，获取client_id和重定向地址
const goToGithubLogin = () => {
  window.location.href = "https://github.com/login/oauth/authorize?" +
    "client_id=Ov23liqBU5jlmfAjUoQx" + "&" +
    "redirect_uri=http://127.0.0.1:5173/oauth/github";
}

</script>

<style scoped>
#userLoginView {

}

.login-card {
  margin: 10em auto;
  width: 35%;
}

.login-form {
  margin: 30px;
}

:deep(.el-form-item__label) {
  color: black !important;
  font-size: 1.1em;
}

.third-party-icon {
  cursor: pointer;
}
</style>
