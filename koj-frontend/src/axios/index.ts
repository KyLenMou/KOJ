import axios from "axios";
import { ElMessage } from 'element-plus'
import { useCurrentUserStore } from "@/stores/currentUser";

const clearCurrentUserIfNeeded = () => {
  const { clearCurrentUser } = useCurrentUserStore()
  clearCurrentUser()
}

axios.interceptors.request.use(
  function (config) {
    // 把url前缀（http://127.0.0.1:8090）替换为代理地址（/api）
    // config.url = config.url?.replace('http://127.0.0.1:8090', '/api')

    // Do something before request is sent
    console.log("请求拦截器：" + config);
    return config;
  },
  function (error) {
    // Do something with request error
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
    return Promise.reject(error);
  }
);

// 把axiosInstance作为axios导出
export default axios;
