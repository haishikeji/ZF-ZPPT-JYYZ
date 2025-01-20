<template>
  <ConfigProvider :locale="getAntdLocale">
    <router-view #="{ Component }" v-if="!loading">
      <component :is="Component" />
    </router-view>
    <!--    <LockScreen />-->
  </ConfigProvider>
</template>

<script setup lang="ts">
  import { ref, watchEffect } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import { ConfigProvider } from 'ant-design-vue';
  import { transformI18n } from './hooks/useI18n';
  /*import { LockScreen } from '@/components/basic/lockscreen';*/
  import { useLocale } from '@/locales/useLocale';
  import { getConfig } from '@/utils/config';
  import { getUrlParams } from '@/utils/urlUtils';
  import { getOAuthUrl } from '@/api/oauth';
  import { useUserStore } from '@/store/modules/user';

  const route = useRoute();
  const { getAntdLocale } = useLocale();
  const userStore = useUserStore();
  const loading = ref(true);
  const router = useRouter();
  const token = ref(userStore.getToken);

  watchEffect(() => {
    if (route.meta?.title) {
      // 翻译网页标题
      document.title = transformI18n(route.meta.title);
    }
  });

  const oauthLogin = function () {
    getConfig().then((res: any) => {
      if (!res.isDev && token.value == null) {
        const ticket = getUrlParams()['ticket'];
        const tm = getUrlParams()['tm'];

        if (!ticket) {
          getOAuthUrl('cas').then((res) => {
            if (res) window.location.href = res;
          });
        } else {
          userStore.oauthLogin('cas', ticket, tm).then(
            (data) => {
              loading.value = false;
              setTimeout(() => router.replace((route.query.redirect as string) ?? '/'));
            },
            () => {
              loading.value = false;
            },
          );
        }
      } else {
        loading.value = false;
      }
    });
  };

  if (location.href.indexOf('loginType=1') >= 0) {
    loading.value = false;
  } else {
    oauthLogin();
  }
</script>

<style lang="less">
  body {
    overflow: hidden;
  }

  .ant-input-affix-wrapper,
  .ant-select-selector {
    border: 1px solid #d9d9d9b8 !important;
  }

  .card-search,
  .card-edit {
    min-height: 100%;
    background-color: white;
    padding: 24px 16px 20px 16px;
  }

  .modal-search {
    min-height: 100%;
    background-color: white;
  }

  .card-search .edit-operation,
  .modal-search .edit-operation {
    .ant-col {
      text-align: right;

      .ant-btn {
        margin-left: 8px;
      }
    }
  }

  .search-result-list {
    margin-top: 20px;
  }

  .card-edit {
    .buttom-btns {
      text-align: right;
      margin-top: 20px;

      .ant-btn {
        margin-left: 10px;
      }
    }
  }

  .card-edit {
    .form-input {
      width: 200px;
    }
  }

  .detail-buttons {
    text-align: right;
    margin: 20px;
    width: 100%;
    padding-right: 20px;

    .ant-btn {
      margin-left: 10px;
    }
  }

  .ant-descriptions-item-label {
    width: 12%;
  }

  /*.ant-divider-horizontal.ant-divider-with-text-left:before,.ant-divider-horizontal.ant-divider-with-text-left:after {
  border-top-color: #1890ff !important;
}*/

  .ant-table {
    .anticon {
      font-size: 16px;
      margin-right: 10px;
    }

    .anticon-download {
      color: #28ba62;
    }

    .anticon-delete {
      color: #ff4961;
    }

    .table-operation {
      display: flex;
    }

    .ant-table tfoot > tr > td,
    .ant-table tfoot > tr > th,
    .ant-table-tbody > tr > td,
    .ant-table-thead > tr > th {
      padding: 6px;
    }
  }

  .flex-space-between {
    display: flex !important;
    justify-content: space-between !important;
  }
</style>

<style lang="css" src="./assets/css/common.css"></style>
