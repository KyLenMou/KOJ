import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress' // progress bar
import OjLayout from '@/components/OjLayout.vue'
import ojRoutes from './routes/ojRoutes'
import adminRoutes from './routes/adminRoutes'
import createRouteGuard from './guard'
import AdminLayout from '@/components/AdminLayout.vue'

NProgress.configure({ showSpinner: false }) // NProgress Configuration
const routes = [...ojRoutes, ...adminRoutes]
const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

// createRouteGuard(router);

export default router
