import { TinyModal } from "@opentiny/vue";
import axios from "axios";

// const clearCurrentUserIfNeeded = () => {
//   const { clearCurrentUser } = useCurrentUserStore()
//   clearCurrentUser()
// }


axios.interceptors.request.use(
  function (config) {
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  function (res) {
    if (res.data.code !== 0) {
      if (res.data.code === -1 ) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      } else if (res.data.code === 400) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      } else if (res.data.code === 401) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      } else if (res.data.code === 402) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
        // 如果当前在admin页面，跳转到首页
      } else if (res.data.code === 403) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      } else if (res.data.code === 404) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      } else if (res.data.code === 500) {
        TinyModal.message({
            message: `出现错误`,
            status: 'error'
          })
      }
      return Promise.reject(res.data.message)
    }
    return res
  },
  function (error) {
    TinyModal.message({
        message: `出现错误`,
        status: 'error'
      })
    return Promise.reject(error);
  }
);

// 把axiosInstance作为axios导出
export default axios;
