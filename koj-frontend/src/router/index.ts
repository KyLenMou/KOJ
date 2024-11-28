import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress' // progress bar
import ojRoutes from './routes/ojRoutes'
import adminRoutes from './routes/adminRoutes'
import problemRoutes from './routes/problemRoutes'
import createRouteGuard from './guard'

NProgress.configure({ showSpinner: false }) // NProgress Configuration
const routes = [...ojRoutes, ...adminRoutes,...problemRoutes]
const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

// createRouteGuard(router);

export default router
