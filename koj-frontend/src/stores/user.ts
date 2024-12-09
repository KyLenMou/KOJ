import { defineStore } from 'pinia'
import { ref } from 'vue'
import { PassportControllerService, type UserInfoVO, type UserLoginDTO } from '@/api'

const useUserStore = defineStore(
  'user',
  () => {
    const user = ref<UserInfoVO>({
      id: '',
      username: '',
      role: '',
      avatar: '',
      titleColor: '',
      titleName: ''
    })

    const getCurrentUser = async () => {
      const { data, code } = await PassportControllerService.getCurrentUserInfoUsingGet()
      if (code) return
      if (data) {
        user.value = data
      }
    }

    const login = async (userLoginDTO: UserLoginDTO) => {
      const { data, code } = await PassportControllerService.userLoginUsingPost(userLoginDTO)
      if (code) return false
      user.value = data
      return true
    }

    const logout = async () => {
      const { code } = await PassportControllerService.userLogoutUsingPost()
      if (code) return false
      Object.assign(user.value, {
        id: '',
        username: '',
        role: '',
        avatar: '',
        titleColor: '',
        titleName: ''
      })
      return true
    }
    return { user, getCurrentUser, login, logout }
  },
  {
    persist: true
  }
)

export default useUserStore
