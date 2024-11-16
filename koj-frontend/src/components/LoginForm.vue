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
    <template #default>
      <tiny-form ref="formRef" :model="createData" @validate="validate" label-position="top">
        <tiny-form-item style="text-align: center">
          <h2 style="font-size: large; margin: 0">登录 KOJ</h2>
        </tiny-form-item>
        <tiny-form-item prop="name" :rules="{ required: true, messages: '必填', trigger: 'blur' }">
          <tiny-input v-model="createData.name" placeholder="用户名/邮箱"></tiny-input>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-input v-model="createData.nickname" type="password" placeholder="密码"></tiny-input>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-button type="primary" style="width: 100%; border-radius: 6px"> 登录 </tiny-button>
        </tiny-form-item>
        <tiny-form-item>
          <tiny-row :flex="true">
            <tiny-link type="primary">邮箱注册</tiny-link>
            <tiny-link type="primary" style="margin-left: auto">忘记密码</tiny-link>
          </tiny-row>
        </tiny-form-item>
      </tiny-form>
    </template>
    <template #footer>
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
import { ref, watch } from 'vue'
import { TinyButton, TinyDialogBox, TinyModal } from '@opentiny/vue'

// 获得logo图片
const getImg = () => {
  return new URL(`@/assets/logo.png`, import.meta.url).href
}

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

const createData = ref({
  name: '',
  nickname: ''
})
const formRef = ref()
function validate() {
  TinyModal.message({ message: '校验事件触发了', status: 'info' })
}
function submit() {
  formRef.value.validate(() => {
    // empty
  })
}
const emit = defineEmits(['update:visible'])

const localVisible = ref(props.visible)

watch(
  () => props.visible,
  (newVal) => {
    localVisible.value = newVal
  }
)

watch(localVisible, (newVal) => {
  emit('update:visible', newVal)
})
</script>

<style scoped>
#login {
}
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
