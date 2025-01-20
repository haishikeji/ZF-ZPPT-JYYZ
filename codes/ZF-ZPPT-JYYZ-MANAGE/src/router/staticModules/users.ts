import type { RouteRecordRaw } from 'vue-router';
import RouterView from '@/layout/routerView/index.vue';
import { t } from '@/hooks/useI18n';

const moduleName = 'syssetting';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/syssetting',
    name: moduleName,
    redirect: '/syssetting/userslist',
    component: RouterView,
    meta: {
      title: t('routes.users.moduleName'),
      icon: 'icon-yibiaopan',
    },
    children: [
      {
        path: 'userslist',
        name: `userslist`,
        meta: {
          title: t('routes.users.userslistTitle'),
          icon: 'icon-shouye',
        },
        component: () =>
          import( '@/views/system/users/index.vue'),
      },
      {
        path: 'useredit',
        name: `'useredit`,
        meta: {
          title: t('routes.users.usereditTitle'),
          icon: 'icon-shouye',
          hideInMenu: true,
        },
        component: () =>
          import( '@/views/system/users/index.vue'),
      },


    ],
  },
];

export default routes;
