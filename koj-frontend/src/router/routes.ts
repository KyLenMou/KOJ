import type { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import CodeView from "@/views/CodeView.vue";
import ProblemsetView from "@/views/oj/problem/ProblemsetView.vue";
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
import ProblemView from "@/views/oj/problem/ProblemView.vue";
import ProblemDetailView from "@/views/oj/problem/ProblemDetailView.vue";
import ProblemSubmitView from "@/views/oj/problem/ProblemSubmitView.vue";
import ProblemSubmissionView from "@/views/oj/problem/ProblemSubmissionView.vue";
import QueueView from "@/views/oj/queue/QueueView.vue";

export const routes: Array<RouteRecordRaw> = [
  /**
   * Admin routes 0
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
          isVisible: true,
          menuName: 'Problemset',
        },
      },
      {
        path: 'problem',
        name: 'admin-problem',
        component: ProblemAdminView,
        meta: {
          isVisible: false,
          menuName: 'Problem',
        },
      },
      {
        path: 'tag',
        name: 'admin-tag',
        component: TagAdminView,
        meta: {
          isVisible: true,
          menuName: 'Tag',
        },
      },
    ]
  },
  /**
   * Problem routes 1
   */
  {
    path: '/problem/:problemDisplayId',
    name: 'problem',
    component: ProblemView,
    meta: {
      isVisible: false
    },
    children: [
      {
        path: '',
        name: 'problem-detail',
        component: ProblemDetailView,
        meta: {
          isVisible: true,
          menuName: 'Problem',
        }
      },
      {
        path: 'submit',
        name: 'problem-submit',
        component: ProblemSubmitView,
        meta: {
          isVisible: true,
          menuName: 'Submit',
        }
        // todo add more children
      },
      {
        path: 'submission',
        name: 'problem-submission',
        component: ProblemSubmissionView,
        meta: {
          isVisible: true,
          menuName: 'Submission',
        }
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
    path: '/queue',
    name: 'queue',
    component: QueueView,
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
    path: '/oauth/github',
    name: 'oauth-github',
    component: OAuthGithubView,
    meta: {
      isVisible: false
    }
  },
  {
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
];
