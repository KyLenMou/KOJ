<template>
  <div id="userRegisterView">
    <common-card head="Register in KOJ" class="login-card">
      <template #content>
        <el-form
          class="login-form"
          label-position="right"
          label-width="auto"
          :model="formLabelAlign"
          style="max-width: 600px"
        >
          <el-form-item label="Username">
            <el-input v-model="formLabelAlign.username" />
            The user name cannot be more than 32 characters
          </el-form-item>
          <el-form-item label="Email">
            <el-input v-model="formLabelAlign.email">
              <template #append>
                <el-space>
                <el-icon style="font-size: 1.2em;"><Message /></el-icon>
                <el-button>
                  Send Captcha
                </el-button>

                </el-space>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="Captcha">
            <el-input v-model="formLabelAlign.code" />
          </el-form-item>
          <el-form-item label="Password">
            <el-input v-model="formLabelAlign.password" />
          </el-form-item>
          <el-form-item label="ConfirmPassword">
            <el-input v-model="formLabelAlign.confirmPassword" />
            The password must be between 5 and 32 characters long
          </el-form-item>
          <el-form-item label=" ">
            <el-button @click="onSubmit">Register</el-button>
            <el-button type="primary" link @click="onSubmit" style="margin-left: auto">Already have an account? Login now! </el-button>
          </el-form-item>
        </el-form>
      </template>
    </common-card>
  </div>
</template>

<script setup lang="ts">
import { Message } from "@element-plus/icons-vue";

const formLabelAlign = reactive({
  username: '',
  email: '',
  code: '',
  password: '',
  confirmPassword: '',
})

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
