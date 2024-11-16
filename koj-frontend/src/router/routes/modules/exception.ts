import { RoleType } from '@/common/RoleType';

export default {
  path: 'exception',
  name: 'Exception',
  id: 'Exception',
  label: 'Exception',
  component: () => import('@/views/NotFoundView.vue'),
  meta: {
    locale: 'menu.exception',
    requiresAuth: true,
    order: 6,
    roles: [RoleType.admin, RoleType.user, RoleType.guest],
  }
};
