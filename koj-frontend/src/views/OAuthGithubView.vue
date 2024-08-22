<template>
  <div id="oAuthGithubView">
    <el-image style="width: 189px" :src="getImg()"/>
    <vue-loaders-line-scale color="#333333" scale="1.3" style="margin: auto 0"/>
    <github theme="outline" size="200" fill="#333"/>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from "element-plus";
import { OAuthControllerService } from "@/api";
import { useCurrentUserStore } from "@/stores/currentUser";
import { Github } from "@icon-park/vue-next"

function getImg() {
  return new URL(`../assets/logo-no-text.png`, import.meta.url).href;
}

const router = useRouter()
const {setCurrentUser} = useCurrentUserStore()

onMounted(async () => {
  const code: string | null = new URLSearchParams(window.location.search).get('code')
  if (!code) {
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
  display: flex;
  justify-content: space-around;
  margin: auto auto;
  width: 60%;
}

loading {
}

</style>
