import type { RouteRecordRaw } from 'vue-router'

const adminRoutes: RouteRecordRaw[] = [
  {
    path: '/admin/*',
    redirect: '/admin/dashboard'
  },
  {
    path: '/admin/',
    name: 'adminRoot',
    component: () => import('@/components/AdminLayout.vue'),
    children: [
      {
        path: '',
        redirect: '/admin/dashboard'
      },
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/AdminDashboardView.vue')
      },
      {
        path: 'user',
        name: 'AdminUser',
        component: () => import('@/views/admin/AdminUserView.vue')
      },
      {
        path: 'problemset',
        name: 'AdminProblemset',
        component: () => import('@/views/admin/AdminProblemsetView.vue')
      },
      {
        path: 'problem',
        name: 'AdminProblem',
        component: () => import('@/views/admin/AdminProblemView.vue')
      },
      {
        path: 'settings',
        name: 'AdminSettings',
        component: () => import('@/views/admin/AdminSettingsView.vue')
      }
    ]
  }
]

export default adminRoutes
