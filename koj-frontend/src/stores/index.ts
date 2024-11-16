import { createPinia } from 'pinia'
import persist from 'pinia-plugin-persistedstate'

// import useAppStore from './modules/app';
// import useUserStore from './modules/user';

const pinia = createPinia()
pinia.use(persist)
export default pinia
