<template>
  <ion-page class="list-page company-list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>企业信息收集</ion-title>
        <ion-buttons slot="end">
          <ion-icon :icon="addCircleOutline" @click="onAdd"></ion-icon>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-item class="search-item">
        <ion-input placeholder="请输入公司名称" class="custom"
                   v-model="searchParams.companyName" style="border: 1px solid #f2f2f5;border-radius: 14px;--padding-start: 10px;height: 35px;"></ion-input>
        <ion-button slot="end" style="height: 33px;width: 70px;margin-left: 10px;--box-shadow: none;--border-radius: 14px;" @click="reload" >搜索
        </ion-button>
      </ion-item>

      <div class="bw-vue-list">
        <div class="list-content" v-if="!loading">
          <ion-list>
            <div v-for="(record,key) in dataList" :key="key">
              <ion-item-sliding>
                <ion-item  detail @click="onDetail(record.companyID)">
                  <ion-label>
                    <div class="multi-title">
                      <h2>{{ record.companyName }}</h2>
                    </div>
                    <p>地点：{{record.companyAddress}}</p>
                    <p>联系人：{{record.userName}}</p>
                    <div class="multi-title">
                      <p>联系电话：{{record.userMobile}}</p>
                      <p>岗位数量：{{ record.postCount }}</p>
                    </div>
                  </ion-label>
                </ion-item>
                <ion-item-options>
                  <ion-item-option @click="onEdit(record.companyID)">
                    <ion-icon :icon="buildOutline"></ion-icon>
                  </ion-item-option>
                  <ion-item-option color="danger" @click="setDelAlertOpen(true, record.companyID)">
                    <ion-icon :icon="trashOutline"></ion-icon>
                  </ion-item-option>
                </ion-item-options>
              </ion-item-sliding>
            </div>
          </ion-list>
        </div>
      </div>
      <b-empty v-if="dataList.length<=0" :loading="loading"/>
      <ion-infinite-scroll threshold="100px" @ionInfinite="onScroll($event)">
        <ion-infinite-scroll-content
            :loadingText="pagination.total>pagination.current*pagination.pageSize?'正在加载...':'暂无更多'"
            loadingSpinner="bubbles">
        </ion-infinite-scroll-content>
      </ion-infinite-scroll>
    </ion-content>
    <ion-alert
        :is-open="delAlertOpen"
        header="删除确认"
        message="确定要删除该企业吗？"
        :buttons="delAlertButtons"
        @didDismiss="setDelAlertOpen(false, null)"
    ></ion-alert>

    <ion-alert
        :is-open="infoAlertOpen"
        :header="infoAlterData.title"
        :message="infoAlterData.message"
        :buttons="infoAlertButtons"
        @didDismiss="setInfoAlertOpen(false)"
    ></ion-alert>
    <ion-loading
        :is-open="delLoading"
        message="删除中..."
        @didDismiss="setDelLoadingOpen(false)" >
    </ion-loading>
  </ion-page>
</template>

<script lang="ts">
import {buildOutline,trashOutline,arrowBackOutline, addCircleOutline} from 'ionicons/icons';
import {computed, defineComponent, reactive, ref} from 'vue';
import {useRoute, useRouter} from "vue-router";
import {useUserStore} from '@/store/modules/user';
import {IonIcon, onIonViewDidEnter} from '@ionic/vue';
import BEmpty from "@/components/empty.vue";
import {post} from "@/api/common";
import {getCompanyList} from '@/api/company/index';

export default defineComponent({
  name: 'CompanyList',
  components: {IonIcon, BEmpty},
  setup() {
    const router = useRouter();
    const route = useRoute();
    const curUserID = ref("");
    const total = ref(10);
    const loading = ref(true);
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 5,
      loginUserID: '',
      companyName: '',
    });
    const pagination = computed(() => ({
      total: total,
      current: searchParams.pageIndex,
      pageSize: searchParams.pageSize
    }));
    const dataList = ref<any>([]);

    const loadData = async function () {
      loading.value = true;
      const loginUserInfo = useUserStore().getUserInfo;
      searchParams.loginUserID = curUserID.value = loginUserInfo.userID == undefined?"":loginUserInfo.userID;
      getCompanyList(searchParams).then(data => {
        dataList.value = dataList.value.concat(data.list);
        total.value = data.total;
        console.log(dataList.value);
      })
      loading.value = false;
    }

    const reload = () => {
      dataList.value = [];
      searchParams.pageIndex = 1;
      loadData();
    }

    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (pagination.value.total.value > pagination.value.current * pagination.value.pageSize) {
          searchParams.pageIndex += 1;
          loadData();
        }
      }, 500);
    }


    // 信息弹窗内容
    const infoAlterData = reactive({
      title:"",
      message:""
    })
    // 删除警告弹窗开关
    const delAlertOpen = ref(false);
    // 删除警告弹窗按钮定义
    const infoAlertButtons = [
      {
        text: '确定',
        role: 'confirm',
        handler: () => {
          reload();
        },
      },
    ];

    // 删除数据
    const delCompanyID = ref("");
    // 删除加载
    const delLoading = ref(false);
    // 信息弹窗开关
    const infoAlertOpen = ref(false);
    // 删除警告弹窗按钮定义
    const delAlertButtons = [
      {
        text: '取消',
        role: 'cancel',
        handler: () => {
          reload();
        },
      },
      {
        text: '确定',
        role: 'confirm',
        handler: () => {
          delLoading.value = true;
          post("companyService/company/delete",[delCompanyID.value],"企业信息删除").then((res) => {
            /*infoAlterData.title = "提示";
            infoAlterData.message = "删除成功";
            setInfoAlertOpen(true);*/
          }).finally(()=>{
            delLoading.value = false;
            reload();
          });
        },
      },
    ];
      // 设置要删除的企业信息
    function setDelAlertOpen(value: boolean, companyID: any) {
      delAlertOpen.value = value;
      if(companyID != null) {
        delCompanyID.value = companyID;
      }
    }
    // 设置信息提示弹窗开关
    function setInfoAlertOpen(value: boolean) {
      infoAlertOpen.value = value;
    }
    // 设置删除弹窗开启关闭
    function setDelLoadingOpen(value: boolean) {
      delLoading.value = value;
    }
    const onDetail = (companyID:string) => {
      router.push({path: './detail', query: {reload: 1,id: companyID,loginUserID:curUserID.value}});
    }

    const onAdd = () => {
      router.push({path: './edit', query: {reload: 1,id: null,status: 1,loginUserID:curUserID.value}});
    }

    const onEdit = (companyID: string) => {
      router.push({path: './edit', query: {reload: 1, id: companyID,status: 1,loginUserID:curUserID.value}});
    }

    const onBack = () => {
      router.push('../');
    }

    onIonViewDidEnter(() => {
      if(route.query.reload)reload();
    });

    return {
      arrowBackOutline,
      addCircleOutline,
      buildOutline,
      trashOutline,
      router,
      total,
      loading,
      dataList,
      pagination,
      searchParams,
      delCompanyID,
      delAlertButtons,
      infoAlertButtons,
      delAlertOpen,
      infoAlertOpen,
      delLoading,
      infoAlterData,
      onBack,
      onAdd,
      onDetail,
      onScroll,
      onEdit,
      setDelAlertOpen,
      setInfoAlertOpen,
      setDelLoadingOpen,
      loadData,
      reload,
    }
  }
});
</script>

<style lang="less">
.custom{
  --placeholder-color: gray;
  --placeholder-opacity: 0.5;
}

.search-item{
  margin: 10px 0;
  font-size: 14px;
  ion-input{
    border: 1px solid #f2f2f5;
    border-radius: 4px;
    --padding-start: 10px;
    width: 100px;
  }

  input{
    padding: 6px !important;
  }
}

.company-list-page {
  .list-content {
    margin: 0px 15px !important;
    background-color: white !important;
    border-radius: 0 !important;

    ion-item {
      font-size: 14px;

      p {
        font-size: 12px;
      }
    }
  }
}

</style>
