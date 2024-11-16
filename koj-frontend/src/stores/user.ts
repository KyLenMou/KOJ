import { defineStore } from 'pinia'
// import { setToken, clearToken } from '@/utils/auth';
import type { UserState, UserInfo } from './types'
import { RoleType } from '@/common/RoleType'
import { computed, ref } from 'vue'
import { PassportControllerService, type UserLoginDTO } from '@/api'
import log from '@/common/DevUtils'

const useUserStore = defineStore(
  'user',
  () => {
    const user = ref<UserState>({
      userId: '',
      username: '',
      role: RoleType.guest
    })

    const isAdmin = computed(() => user.value.role === 'admin')

    const setUser = (userId: string, username: string, role: RoleType) => {
      user.value.userId = userId
      user.value.username = username
      user.value.role = role
    }

    const login = async (userLoginDTO: UserLoginDTO) => {
      const res = await PassportControllerService.userLoginUsingPost(userLoginDTO)
      log.info(res)
      if (res.data) {
        setUser(res.data.userId, res.data.username, res.data.userRole)
      }
    }
    return { user, isAdmin, setUser, login }
  },
  {
    persist: true
  }
)

export default useUserStore
