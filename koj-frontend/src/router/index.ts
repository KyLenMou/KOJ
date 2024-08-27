import { createRouter, createWebHistory } from 'vue-router'
import { routes } from "@/router/routes";
import { useCurrentUserStore } from "@/stores/currentUser";
import { storeToRefs } from "pinia";
import { Badminton } from "@icon-park/vue-next";
import { ElMessage } from "element-plus";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export function setupRouterGuard() {
  const { getCurrentUser } = useCurrentUserStore();
  router.beforeEach((to, from, next) => {
    const userRole = getCurrentUser().userRole;
    const needAccess = to.path.startsWith("/admin") ? "admin" : "user";
    if (needAccess === 'admin' && userRole !== "admin" && userRole !== "root") {
      ElMessage.error('无权限访问');
      next(false);
      return;
    }
    next();
  });
}

export default router
