import type { RouteRecordRaw } from 'vue-router';
import RouterView from '@/layout/routerView/index.vue';
import { t } from '@/hooks/useI18n';

const moduleName = 'sale';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/sale',
    name: moduleName,
    redirect: '/sale/list',
    component: RouterView,
    meta: {
      title: t('routes.sale.modelName'),
      icon: 'icon-yibiaopan',
    },
    children: [
      {
        path: 'list',
        name: `${moduleName}-welcome`,
        meta: {
          title: t('routes.sale.listTitle'),
          icon: 'icon-shouye',
        },
        component: () =>
          import( '@/views/sale/salelist/index.vue'),
      },
      {
        path: 'edit',
        name: `${moduleName}-edit`,
        meta: {
          title: t('routes.sale.editTitle'),
          icon: 'icon-shouye',
          hideInMenu: true,
        },
        component: () =>
          import( '@/views/sale/salelist/editDic.vue'),
      },


    ],
  },
];

export default routes;
