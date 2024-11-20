import { createApp } from 'vue'
import pinia from './stores'

import App from './App.vue'
import router from './router'

const app = createApp(App)
app.config.errorHandler = (error, instance, info) => {
    console.log('errorHandler', error, instance, info)
}
app.use(router)
app.use(pinia)

app.mount('#app')
