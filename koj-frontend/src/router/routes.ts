import type { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import CodeView from "@/views/CodeView.vue";
import ProblemsetView from "@/views/ProblemsetView.vue";
import ContestsView from "@/views/ContestsView.vue";
import UserLoginView from "@/views/passport/UserLoginView.vue";
import UserRegisterView from "@/views/passport/UserRegisterView.vue";
import HealthView from "@/views/HealthView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import OAuthGithubView from "@/views/passport/OAuthGithubView.vue";
import AdminVIew from "@/views/admin/AdminVIew.vue";
import UserHomeView from "@/views/UserHomeView.vue";
import ProblemsetAdminView from "@/views/admin/ProblemsetAdminView.vue";
import ProblemAdminView from "@/views/admin/ProblemAdminView.vue";

export const routes: Array<RouteRecordRaw> = [
  /**
   * Admin routes
   */
  {
    path: '/admin',
    name: 'admin',
    component: AdminVIew,
    meta: {
      showOnNav: true
    },
    children: [
      {
        path: 'problemset',
        name: 'admin-problemset',
        component: ProblemsetAdminView,
      },
      {
        path: 'problem',
        name: 'admin-problem',
        component: ProblemAdminView,
      },
    ]
  },
  /**
   * Normal routes
   */
  {
    path: '/',
    redirect: '/home',
    meta: {
      showOnNav: false
    }
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: {
      showOnNav: true
    }
  },
  {
    path: '/code',
    name: 'code',
    component: CodeView,
    meta: {
      showOnNav: true
    }
  },
  {
    path: '/problemset',
    name: 'problemset',
    component: ProblemsetView,
    meta: {
      showOnNav: true
    }
  },
  {
    path: '/contests',
    name: 'contests',
    component: ContestsView,
    meta: {
      showOnNav: true
    }
  },
  {
    path: '/user-home/:username',
    name: 'user-home',
    component: UserHomeView,
    meta: {
      showOnNav: false
    }
  },
  {
    path: '/login',
    name: 'login',
    component: UserLoginView,
    meta: {
      showOnNav: false
    }
  },
  {
    path: '/register',
    name: 'register',
    component: UserRegisterView,
    meta: {
      showOnNav: false
    }
  },
  {
    path: '/health',
    name: 'health',
    component: HealthView,
    meta: {
      showOnNav: false
    }
  },
  {
    // 404 page
    path: '/404',
    name: '404',
    component: NotFoundView,
    meta: {
      showOnNav: false
    }
  },
  {
    // redirect to 404 page
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    meta: {
      showOnNav: false
    }
  },
  {
    path: '/oauth/github',
    name: 'oauth-github',
    component: OAuthGithubView,
    meta: {
      showOnNav: false
    }
  },
];
