import type { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import CodeView from "@/views/CodeView.vue";
import ProblemsetView from "@/views/ProblemsetView.vue";
import ContestsView from "@/views/ContestsView.vue";
import UserLoginView from "@/views/UserLoginView.vue";
import UserRegisterView from "@/views/UserRegisterView.vue";
import HealthView from "@/views/HealthView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import OAuthGithubView from "@/views/OAuthGithubView.vue";

export const routes: Array<RouteRecordRaw> = [
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
  }
];
