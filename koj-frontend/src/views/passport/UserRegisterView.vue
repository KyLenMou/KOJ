<template>
  <div id="userRegisterView">
    <common-card head="Register in KOJ" class="login-card">
      <template #content>
        <el-form
          ref="ruleFormRef"
          style="max-width: 600px"
          class="login-form"
          status-icon
          label-position="right"
          label-width="auto"
          :model="formData"
          :rules="rules"
        >
          <el-form-item label="Username" prop="username">
            <el-input v-model="formData.username"/>
          </el-form-item>
          <el-form-item label="Email" prop="email">
            <el-input v-model="formData.email">
              <template #append>
                <el-space v-if="countdown <= 0 ">
                  <el-icon v-if="countdown !== -1" style="font-size: 1.2em;">
                    <Message/>
                  </el-icon>
                  <el-button @click="doGetEmailCaptcha" tabindex="-1" :loading="countdown === -1">
                    Send Captcha
                  </el-button>
                </el-space>
                <el-space v-else>
                  Wait for
                  {{ countdown + 's' }}
                </el-space>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="Captcha" prop="code">
            <el-input v-model="formData.code"/>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input v-model="formData.password" type="password" show-password/>
          </el-form-item>
          <el-form-item label="ConfirmPassword" prop="confirmPassword">
            <el-input v-model="formData.confirmPassword" type="password" show-password/>
          </el-form-item>
          <el-form-item label=" ">
            <el-button @click="doRegister(ruleFormRef)" type="primary" :loading="isRegisterLoading">Register</el-button>
            <el-button type="primary" link @click="goToLogin" style="margin-left: auto">Already have an account? Login
                                                                                        now!
            </el-button>
          </el-form-item>
        </el-form>
      </template>
    </common-card>
  </div>
</template>

<script setup lang="ts">
import { Loading, Message } from "@element-plus/icons-vue";
import { PassportControllerService, type UserRegisterDTO } from "@/api";
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import type UserRegisterView from "@/views/passport/UserRegisterView.vue";
import { useCurrentUserStore } from "@/stores/currentUser";
import { storeToRefs } from "pinia";
import { GithubOne, TencentQq } from "@icon-park/vue-next";

const router = useRouter()

const {setCurrentUser} = useCurrentUserStore()
const {currentUser} = storeToRefs(useCurrentUserStore())
const isRegisterLoading = ref(false)

// 表单实例
const ruleFormRef = ref<FormInstance>()

// 验证码倒计时
const countdown = ref(0)

const goToLogin = () => {
  router.push('/login')
}

// 表单数据
const formData = reactive<UserRegisterDTO>({
  username: '',
  email: '',
  code: '',
  password: '',
  confirmPassword: '',
})

// 表单规则
const rules = reactive<FormRules<typeof formData>>({
  username: [
    {required: true, message: 'Please input your username', trigger: 'blur'},
    {min: 5, max: 32, message: 'Length should be 5 to 32', trigger: 'blur'}
  ],
  email: [
    {required: true, message: 'Please input your email', trigger: 'blur'},
    {type: 'email', message: 'Please input correct email address', trigger: ['blur', 'change']}
  ],
  code: [
    {required: true, message: 'Please input the captcha', trigger: 'blur'},
    {min: 6, max: 6, message: 'Length should be 6', trigger: 'blur'}
  ],
  password: [
    {required: true, message: 'Please input your password', trigger: 'blur'},
    {min: 5, max: 32, message: 'Length should be 5 to 32', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: 'Please confirm your password', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== formData.password) {
          callback(new Error('The two passwords do not match'));
        } else {
          callback();
        }
      }, trigger: 'blur'
    }
  ]
})

// 获取邮箱验证码
const doGetEmailCaptcha = async () => {
  countdown.value = -1
  if (!formData.email) {
    ElMessage.error('Please input your email')
    countdown.value = 0
    return
  }
  try {
    await PassportControllerService.getRegisterCodeUsingGet(formData.email)
  } catch (e) {
    countdown.value = 0
    return
  }
  ElMessage.success('Captcha sent, please check your email')
  captchaCountdown()
}

// 邮箱验证码倒计时
const captchaCountdown = () => {
  countdown.value = 60
  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value === 0) {
      clearInterval(timer)
    }
  }, 1000)
}

// 注册
const doRegister = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  isRegisterLoading.value = true
  // 校验
  try {
    await new Promise((resolve, reject) => {
      formEl.validate((valid) => {
        if (!valid) {
          reject(new Error('Validation failed'))
        } else {
          resolve(null)
        }
      })
    })
  } catch (e) {
    isRegisterLoading.value = false
    return
  }
  // 注册
  try {
    await PassportControllerService.userRegisterUsingPost(formData)
  } catch (e) {
    isRegisterLoading.value = false
    return
  }
  // 注册完自动登录
  await setCurrentUser()
  ElMessage.success('Register success, welcome, ' + currentUser.value.username)
  isRegisterLoading.value = false
  await router.push('/home')
}

</script>

<style scoped>
#userRegisterView {

}

.login-card {
  margin: 10em auto;
  width: 50%;
}

.login-form {
  margin: 20px;
}

:deep(.el-form-item__label) {
  color: black !important;
  font-size: 1.1em;
}

</style>
