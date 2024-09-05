import type { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import CodeView from "@/views/CodeView.vue";
import ProblemsetView from "@/views/oj/ProblemsetView.vue";
import ContestsView from "@/views/ContestsView.vue";
import UserLoginView from "@/views/passport/UserLoginView.vue";
import UserRegisterView from "@/views/passport/UserRegisterView.vue";
import HealthView from "@/views/HealthView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import OAuthGithubView from "@/views/passport/OAuthGithubView.vue";
import AdminVIew from "@/views/admin/AdminVIew.vue";
import UserHomeView from "@/views/UserHomeView.vue";
import ProblemsetAdminView from "@/views/admin/AdminProblemsetView.vue";
import ProblemAdminView from "@/views/admin/AdminProblemView.vue";
import TagAdminView from "@/views/admin/AdminTagView.vue";
import ProblemView from "@/views/oj/ProblemView.vue";

export const routes: Array<RouteRecordRaw> = [
  /**
   * Admin routes
   */
  {
    path: '/admin',
    name: 'admin',
    component: AdminVIew,
    meta: {
      isVisible: true
    },
    children: [
      {
        path: 'problemset',
        name: 'admin-problemset',
        component: ProblemsetAdminView,
        meta: {
          isVisible: true
        },
      },
      {
        path: 'problem',
        name: 'admin-problem',
        component: ProblemAdminView,
        meta: {
          isVisible: false
        },
      },
      {
        path: 'tag',
        name: 'admin-tag',
        component: TagAdminView,
        meta: {
          isVisible: true
        },
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
      isVisible: false
    }
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: {
      isVisible: true
    }
  },
  {
    path: '/code',
    name: 'code',
    component: CodeView,
    meta: {
      isVisible: true
    }
  },
  {
    path: '/problemset',
    name: 'problemset',
    component: ProblemsetView,
    meta: {
      isVisible: true
    }
  },
  {
    path: '/contests',
    name: 'contests',
    component: ContestsView,
    meta: {
      isVisible: true
    }
  },
  {
    path: '/user-home/:username',
    name: 'user-home',
    component: UserHomeView,
    meta: {
      isVisible: false
    }
  },
  {
    path: '/login',
    name: 'login',
    component: UserLoginView,
    meta: {
      isVisible: false
    }
  },
  {
    path: '/register',
    name: 'register',
    component: UserRegisterView,
    meta: {
      isVisible: false
    }
  },
  {
    path: '/health',
    name: 'health',
    component: HealthView,
    meta: {
      isVisible: false
    }
  },
  {
    // 404 page
    path: '/404',
    name: '404',
    component: NotFoundView,
    meta: {
      isVisible: false
    }
  },
  {
    // redirect to 404 page
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    meta: {
      isVisible: false
    }
  },
  {
    path: '/oauth/github',
    name: 'oauth-github',
    component: OAuthGithubView,
    meta: {
      isVisible: false
    }
  },
  {
    path: '/problem/:problemId',
    name: 'problem-detail',
    component: ProblemView,
    meta: {
      isVisible: false
    }
  }
];
