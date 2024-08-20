import { ref } from 'vue'
import { defineStore } from 'pinia'
import { PassportControllerService, UserInfoControllerService, type UserInfoVO } from "@/api";

export const useCurrentUserStore = defineStore('user', () => {
  const currentUser = ref<UserInfoVO>({
    avatar: '',
    email: '',
    gender: '',
    id: '',
    signature: '',
    titleColor: '',
    titleName: '',
    username: '',
  })
  const getCurrentUser = async () => {
     const res = await UserInfoControllerService.getCurrentUserInfoUsingGet();
     currentUser.value = res.data || currentUser.value;
  }
  const clearCurrentUser = () => {
    currentUser.value = {
      avatar: '',
      email: '',
      gender: '',
      id: '',
      signature: '',
      titleColor: '',
      titleName: '',
      username: '',
    }
  }
  return {currentUser, getCurrentUser, clearCurrentUser}
}, {
  persist: true
})
