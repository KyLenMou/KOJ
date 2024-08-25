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

export default router
