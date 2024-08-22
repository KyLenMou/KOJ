<template>
  <div id="oAuthGithubView">
    正在使用github登录
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from "element-plus";
import { OAuthControllerService } from "@/api";
import { useCurrentUserStore } from "@/stores/currentUser";

const router = useRouter()
const {setCurrentUser} = useCurrentUserStore()

onMounted(async () => {
  const code: string | null = new URLSearchParams(window.location.search).get('code')
  if (!code) {
    ElMessage.error('Login With Github Fail')
    return
  }
  await OAuthControllerService.redirectByGithubUsingGet(code)
  // github登录成功，获取当前用户信息
  await setCurrentUser()
  ElMessage.success('Login With Github Success')
  await router.push('/')
})

</script>

<style scoped>
#oAuthGithubView {

}
</style>
