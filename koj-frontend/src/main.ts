import { createApp } from 'vue'
import pinia from './stores'
import './assets/styles/global.css'; // 导入全局样式

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)
// app.config.errorHandler = (error, instance, info) => {
//     console.log('errorHandler', error, instance, info)
// }

app.use(mavonEditor)
app.use(router)
app.use(pinia)

app.mount('#app')
