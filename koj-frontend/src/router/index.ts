import { createRouter, createWebHistory } from 'vue-router';
import NProgress from 'nprogress'; // progress bar
import DefaultLayout from '@/components/DefaultLayout.vue';
import appRoutes from './routes';
import createRouteGuard from './guard';

NProgress.configure({ showSpinner: false }); // NProgress Configuration

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // 本地地址
    {
      path: '/',
      redirect: `${import.meta.env.VITE_CONTEXT}login`,
    },
    {
      path: '/' + import.meta.env.VITE_CONTEXT,
      redirect: `${import.meta.env.VITE_CONTEXT}home`,
    },
    // 线上地址
    {
      path: import.meta.env.VITE_CONTEXT,
      redirect: { path: `${import.meta.env.VITE_CONTEXT}login` },
    },
    {
      path: import.meta.env.VITE_CONTEXT + 'login',
      name: 'login',
      component: () => import('@/views/passport/LoginView.vue'),
      meta: {
        requiresAuth: false,
      },
    },
    {
      name: 'root',
      path: import.meta.env.VITE_CONTEXT,
      component: DefaultLayout,
      children: appRoutes,
    },
    {
      path: import.meta.env.VITE_CONTEXT + ':pathMatch(.*)*',
      name: 'notFound',
      component: () => import('@/views/NotFoundView.vue'),
    },
  ],
  scrollBehavior() {
    return { top: 0 };
  },
});

// createRouteGuard(router);

export default router;
