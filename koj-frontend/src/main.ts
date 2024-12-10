import { createApp } from 'vue'
import pinia from './stores'
import './assets/styles/global.css'; // 导入全局样式
import dayjs from "dayjs"
import "dayjs/locale/zh-cn";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)

dayjs.locale("zh-cn")
app.provide('$dayjs', dayjs)
app.use(mavonEditor)
app.use(router)
app.use(pinia)

app.mount('#app')
