import type { RouteRecordRaw } from 'vue-router'

const ojRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/home',
    component: () => import('@/components/OjLayout.vue'),
    children: [
      {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/oj/HomeView.vue')
      },
      {
        path: '/problemset',
        name: 'Problemset',
        component: () => import('@/views/oj/ProblemsetView.vue')
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/views/oj/NotFoundView.vue')
      }
    ]
  }
]

export default ojRoutes
