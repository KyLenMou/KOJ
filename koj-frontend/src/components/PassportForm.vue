<template>
  <tiny-dialog-box
    :lock-scroll="false"
    :show-close="false"
    v-model:visible="localVisible"
    :center="true"
    width="400px"
  >
    <template #title>
      <tiny-row :flex="true" justify="center" align="middle">
        <tiny-image fit="fill" :src="getImg()" style="height: 40px" />
        <div style="margin-left: 10px">Kode Online Judge</div>
      </tiny-row>
    </template>
    <template #default v-if="isLoginOrRegister">
      <tiny-form ref="loginFormRef" :model="userLoginDTO" label-position="top">
        <tiny-form-item style="text-align: center">
          <h2 style="font-size: large; margin: 0">登录 KOJ</h2>
        </tiny-form-item>
        <tiny-form-item
          prop="username"
          :rules="{ required: true, trigger: 'blur' }"
          :show-message="false"
        >
          <tiny-input v-model="userLoginDTO.username" placeholder="用户名/邮箱"></tiny-input>
        </tiny-form-item>
        <tiny-form-item
          prop="password"
          :rules="{ required: true, trigger: 'blur' }"
          :show-message="false"
        >
          <tiny-input
            v-model="userLoginDTO.password"
            type="password"
            placeholder="密码"
          ></tiny-input>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-button
            type="primary"
            style="width: 100%; border-radius: 6px"
            @click="login"
            v-model:loading="isLoginButtonLoading"
          >
            登录
          </tiny-button>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-row :flex="true">
            <tiny-button type="text" text="邮箱注册" @click="isLoginOrRegister = false">
            </tiny-button>
            <tiny-button type="text" style="margin-left: auto" text="忘记密码"> </tiny-button>
          </tiny-row>
        </tiny-form-item>
      </tiny-form>
    </template>
    <template #default v-else>
      <tiny-form
        ref="registerFormRef"
        :model="userRegisterDTO"
        label-position="top"
        validate-type="text"
      >
        <tiny-form-item style="text-align: center">
          <h2 style="font-size: large; margin: 0">注册 KOJ</h2>
        </tiny-form-item>
        <tiny-form-item
          prop="email"
          :rules="{
            type: 'email',
            required: true,
            trigger: 'blur'
          }"
        >
          <tiny-input
            v-model="userRegisterDTO.email"
            placeholder="邮箱"
            style="width: 69%"
          ></tiny-input>
          <tiny-button
            style="width: 29%; border-radius: 6px; margin-left: 2%"
            @click="sendRegisterCode"
          >
            <span>发送验证码</span>
          </tiny-button>
        </tiny-form-item>
        <tiny-form-item prop="code" :rules="{ required: true, len: 6, trigger: 'blur' }">
          <tiny-input v-model="userRegisterDTO.code" placeholder="验证码"></tiny-input>
        </tiny-form-item>
        <tiny-form-item
          prop="username"
          :rules="{ required: true, min: 5, max: 32, trigger: 'blur' }"
        >
          <tiny-input v-model="userRegisterDTO.username" placeholder="用户名"></tiny-input>
        </tiny-form-item>
        <tiny-form-item
          prop="password"
          :rules="{ required: true, min: 5, max: 32, trigger: 'blur' }"
        >
          <tiny-input
            v-model="userRegisterDTO.password"
            type="password"
            placeholder="密码"
          ></tiny-input>
        </tiny-form-item>
        <tiny-form-item
          prop="confirmPassword"
          :rules="{ required: true, min: 5, max: 32, trigger: 'blur' }"
        >
          <tiny-input
            v-model="userRegisterDTO.confirmPassword"
            type="password"
            placeholder="确认密码"
          ></tiny-input>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-button type="primary" style="width: 100%; border-radius: 6px" @click="register">
            注册
          </tiny-button>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-row :flex="true">
            <tiny-button type="text" text="已有账号？立即登录！ " @click="isLoginOrRegister = true">
            </tiny-button>
          </tiny-row>
        </tiny-form-item>
      </tiny-form>
    </template>
    <template #footer v-if="isLoginOrRegister">
      <tiny-divider content-background-color="#ffffff">其他方式登录</tiny-divider>
      <tiny-row :flex="true" justify="space-between" align="middle">
        <div class="circle-container">
          <img src="@/assets/images/qq.svg" alt="QQ Image" class="circle-image" />
        </div>
        <div class="circle-container">
          <img src="@/assets/images/wechat.svg" alt="QQ Image" class="circle-image" />
        </div>
        <div class="circle-container">
          <img src="@/assets/images/github.svg" alt="QQ Image" class="circle-image" />
        </div>
      </tiny-row>
    </template>
  </tiny-dialog-box>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import { TinyButton, TinyDialogBox } from '@opentiny/vue'
import { PassportControllerService, type UserLoginDTO, type UserRegisterDTO } from '@/api'
import useUserStore from '@/stores/user'

const isLoginOrRegister = ref(true)

// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo.png`, import.meta.url).href
}

const userStore = useUserStore()

// 登录
const loginFormRef = ref()
const userLoginDTO: UserLoginDTO = reactive({
  password: '',
  username: ''
})
const isLoginButtonLoading = ref(false)
const login = async () => {
  isLoginButtonLoading.value = true
  await loginFormRef.value.validate((valid: boolean) => {
    if (!valid) return
  })
  const res = await userStore.login(userLoginDTO)
  if (res) {
    localVisible.value = false
  }
  isLoginButtonLoading.value = false
}

// 注册
const registerFormRef = ref()
const userRegisterDTO: UserRegisterDTO = reactive({
  code: '',
  email: '',
  password: '',
  confirmPassword: '',
  username: ''
})
const sendRegisterCode = async () => {
  await PassportControllerService.sendRegisterCodeUsingPost(userRegisterDTO.email as string)
}
const register = async () => {
  await registerFormRef.value.validate(async (valid: boolean) => {
    if (!valid) return
  })
  const { code } = await PassportControllerService.userRegisterUsingPost(userRegisterDTO)
  if (code) return
  // 清空userRegisterDTO
  Object.assign(userRegisterDTO, {
    code: '',
    email: '',
    password: '',
    confirmPassword: '',
    username: ''
  })
  // 切换到登录界面
  isLoginOrRegister.value = true
}

// visible传值
const props = defineProps({
    passportDialogVisible: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['update:passportDialogVisible'])
const localVisible = ref(props.passportDialogVisible)
watch(
  () => props.passportDialogVisible,
  (newVal) => {
    localVisible.value = newVal
  }
)
watch(localVisible, (newVal) => {
  emit('update:passportDialogVisible', newVal)
})
</script>

<style scoped>
.circle-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px; /* 根据需要调整大小 */
  height: 30px; /* 根据需要调整大小 */
  border-radius: 50%;
  border: 1px solid #333333; /* 根据需要调整边框 */
}
:deep(.tiny-dialog-box__footer) {
  padding-top: 0;
}
</style>
