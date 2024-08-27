import axios from "axios";
import { ElMessage } from 'element-plus'
import { useCurrentUserStore } from "@/stores/currentUser";

const clearCurrentUserIfNeeded = () => {
  const { clearCurrentUser } = useCurrentUserStore()
  clearCurrentUser()
}

const router = useRouter()

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
        ElMessage.error(res.data.message)
      } else if (res.data.code === 400) {
        ElMessage.error(res.data.message)
      } else if (res.data.code === 401) {
        ElMessage.warning(res.data.message)
      } else if (res.data.code === 402) {
        ElMessage.warning(res.data.message)
        // 清空currentUser
        clearCurrentUserIfNeeded()
        // 如果当前在admin页面，跳转到首页
        if (router.currentRoute?.value.path.startsWith("/admin")) {
          router.push('/')
        }
      } else if (res.data.code === 403) {
        ElMessage.error(res.data.message)
      } else if (res.data.code === 404) {
        ElMessage.error(res.data.message)
      } else if (res.data.code === 500) {
        ElMessage.error(res.data.message)
      }
      return Promise.reject(res.data.message)
    }
    return res;
  },
  function (error) {
    ElMessage.error(error.message)
    return Promise.reject(error);
  }
);

// 把axiosInstance作为axios导出
export default axios;
