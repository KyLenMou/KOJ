import type { RouteRecordRaw } from 'vue-router'

const problemRoutes: RouteRecordRaw[] = [
  {
    path: '/problem/:id',
    name: 'Problem',
    component: () => import('@/views/problem/ProblemView.vue')
  }
]

export default problemRoutes
