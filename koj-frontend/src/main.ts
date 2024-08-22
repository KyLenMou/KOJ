import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/theme-chalk/dark/css-vars.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import {install} from '@icon-park/vue-next/es/all';
import '@icon-park/vue-next/styles/index.css';
import 'vue-loaders/dist/vue-loaders.css';
import VueLoaders from 'vue-loaders';


const app = createApp(App)

// vue-loaders
app.use(VueLoaders)

// icon
install(app, 'i');

// pinia
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

// element
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(ElementPlus, {
  locale: zhCn,
})

// router
app.use(router)

app.mount('#app')
