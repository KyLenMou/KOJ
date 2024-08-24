import { ref } from 'vue'
import { defineStore } from 'pinia'
import { PassportControllerService, type UserInfoVO } from "@/api";

export const useCurrentUserStore = defineStore('user', () => {
  const currentUser = ref<UserInfoVO>({})
  const setCurrentUser = async () => {
    const res = await PassportControllerService.getCurrentUserInfoUsingGet()
    currentUser.value = res.data || currentUser.value;
  }
  const getCurrentUser = () => {
    return currentUser.value
  }
  const clearCurrentUser = () => {
    currentUser.value = {}
  }
  return {currentUser, getCurrentUser, setCurrentUser, clearCurrentUser}
}, {
  persist: true
})
