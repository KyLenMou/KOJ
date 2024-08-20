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
      <template #footer>
        <el-tooltip
          content="Login with QQ"
          placement="top"
        >
          <tencent-qq class="third-party-icon" theme="outline" size="24" fill="#333" style="margin-right: 10px"/>
        </el-tooltip>
        <el-tooltip
          content="Login with Github"
          placement="top"
        >
          <github-one class="third-party-icon" theme="outline" size="24" fill="#333" />
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


const doLogin = async () => {
  await PassportControllerService.userLoginUsingPost(formData);
  await setCurrentUser()
  ElMessage.success("Login success")
  await router.push("/home")
}

const formData = reactive<UserLoginDTO>({
  username: '',
  password: '',
  remember: true
})

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
