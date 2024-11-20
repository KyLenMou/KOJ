import { TinyModal } from '@opentiny/vue'
import axios from 'axios'

const defaultMessages = {
  0: '操作成功',
  400: '请求参数错误',
  401: '暂无权限访问',
  402: '未登录，请先登录',
  403: '禁止访问',
  404: '请求资源不存在',
  500: '系统错误，请联系管理员'
}

axios.interceptors.request.use(
  function (config) {
    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  function (res) {
    const { code, message } = res.data as { code: keyof typeof defaultMessages; message: string }
    if (code !== 0) {
      const status = code === 402 ? 'warning' : 'error'
      TinyModal.message({ message: message || defaultMessages[code] || '未知错误', status })
    } else if (message) {
      TinyModal.message({ message, status: 'success' })
    }
    return res
  },
  function (error) {
    TinyModal.message({ message: error, status: 'error' })
    return error
  }
)

export default axios
