<template>
  <ion-page class="list-page post-list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>
          企业岗位信息
        </ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <div class="stepFlex">
        <div v-for="(record,key) in stepList" :key="key" class="stepFlex-item">
          <div
              :class="[(record.val < curStepData?.statusVal || curStepData?.statusVal==stepList.val) ? 'greenCircle' :record.val == curStepData?.statusVal ? 'now' : 'grayCircle']"></div>
          <div v-if="key !== stepList.length - 1"
               :class="[record.val < curStepData?.statusVal ? 'greenLine' : 'grayLine']"></div>
          <div class="stepFlex-item-label">
            <p class="stepFlex-item-label-title">{{ record.title }}</p>
            <p class="stepFlex-item-label-desc">{{ record.desc }}</p>
          </div>
        </div>
      </div>

      <form autocomplete="off">
        <div class="bw-vue-form">
          <div class="form-title">
            岗位信息
            <ion-icon :icon="addCircleOutline" class="iconBtn" style="float:right;"
                       @click="onAdd()" ></ion-icon>
          </div>
          <div class="bw-vue-list">
            <div class="list-content" v-if="!loading">
            <ion-list>
              <div v-for="(record,key) in postList" :key="key" >
                <ion-item-sliding>
                <ion-item detail @click="onEdit(record.postID)">
                  <ion-label>
                    <h2>
                      {{ record.professionName }}
                    </h2>
                    <p v-if="record.startTime!=null&&record.endTime!=null">
                      {{ dayjs(record.startTime).format("YYYY-MM-DD") }}至{{ dayjs(record.endTime).format("YYYY-MM-DD") }}
                    </p>
                    <p v-if="record.startTime==null||record.endTime==null">
                      暂未设置有效期限
                    </p>
                    <p>
                      招聘人数：{{ record.recruitCount }} 人
                    </p>
                  </ion-label>
                </ion-item>
                <ion-item-options>
                  <ion-item-option color="danger" @click="setDelAlertOpen(true, record.postID)">
                    <ion-icon :icon="trashOutline"></ion-icon>
                  </ion-item-option>
             </ion-item-options>
                </ion-item-sliding>
              </div>
            </ion-list>
            </div>
          </div>
          <ion-infinite-scroll  threshold="100px" @ionInfinite="onScroll($event)">
            <ion-infinite-scroll-content
                :loadingText="pageParams.total>pageParams.pageIndex*pageParams.pageSize?'正在加载...':'暂无更多'"
                loadingSpinner="bubbles">
            </ion-infinite-scroll-content>
          </ion-infinite-scroll>
        </div>
      </form>
    </ion-content>
    <ion-footer>
      <ion-toolbar>
        <ion-button style="width: 48%;float:left;margin-left: 1%;" @click="onBack" >上一步</ion-button>
        <ion-button style="width: 48%;float:left;margin-right: 1%;" @click="onFinish" >完  成</ion-button>
      </ion-toolbar>
    </ion-footer>

    <ion-alert
        :is-open="delAlertOpen"
        header="删除确认"
        message="确定要删除该岗位吗？"
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
import {defineComponent, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {alertController, onIonViewDidEnter} from "@ionic/vue";
import {arrowBackOutline, addCircleOutline,trashOutline} from 'ionicons/icons';
import {getCompanyPostList} from '@/api/company/index'
import dayjs from "dayjs";
import {post, postByDataAndParams} from "@/api/common";
import {useUserStore} from "@/store/modules/user";

interface StepParams{
  loginUserID: string,
  openID:string,
  statusVal: number
}

export default defineComponent({
  name: 'PostList',
  setup() {
    const userStore = useUserStore();
    const userInfo = ref(userStore.getUserInfo);

    const router = useRouter();
    const route = useRoute();
    const loading = ref(false);
    const postList = ref<any>([]);
    const pageParams = reactive({
      pageIndex: 1,
      pageSize: 10,
      total:0,
      companyID:''
    });
    const curStepData = ref<StepParams>({
      loginUserID:"",
      openID:"",
      statusVal: 1
    });
    const stepList = ref([
      {
        title: '基础信息',
        desc: '企业基础信息',
        val: 1
      },
      {
        title: '岗位信息',
        desc: '企业岗位信息',
        val: 2
      }]);
    const presentAlert = async (message: string) => {
      const alert = await alertController.create({
        header: '错误！',
        message: message,
        buttons: [
          '确定'
        ],
      });

      await alert.present();
    };



    // 信息弹窗内容
    const infoAlterData = reactive({
      title:"",
      message:""
    });
    // 删除警告弹窗开关
    const delAlertOpen = ref(false);
    // 删除警告弹窗按钮定义
    const infoAlertButtons = [
      {
        text: '确定',
        role: 'confirm',
        handler: () => {
          reload(pageParams.companyID,curStepData.value.statusVal,curStepData.value.loginUserID,curStepData.value.openID);
        },
      },
    ];

    // 删除数据
    const delPostID = ref("");
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
          reload(pageParams.companyID,curStepData.value.statusVal,curStepData.value.loginUserID,curStepData.value.openID);
        },
      },
      {
        text: '确定',
        role: 'confirm',
        handler: () => {
          delLoading.value = true;
          postByDataAndParams("companyService/post/delete", [delPostID.value], {loginUserID: userInfo.value.userID}, "岗位信息删除").then((res) => {
           /* infoAlterData.title = "提示";
            infoAlterData.message = "删除成功";
            setInfoAlertOpen(true);*/
          }).finally(()=>{
            delLoading.value = false;
            reload(pageParams.companyID,curStepData.value.statusVal,curStepData.value.loginUserID,curStepData.value.openID);
          });
        },
      },
    ];
    // 设置要删除的求职人员
    function setDelAlertOpen(value: boolean, postID: any) {
      delAlertOpen.value = value;
      if(postID != null) {
        delPostID.value = postID;
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

    const onAdd = () => {
      router.push({path: './postEdit', query: {reload:1,id: null,companyID:pageParams.companyID,loginUserID:curStepData.value.loginUserID}});
    };
    const onEdit = (postID:any) => {
      router.push({path: './postEdit', query: {reload:1,id:postID,companyID:pageParams.companyID,loginUserID:curStepData.value.loginUserID}});
    };
    const onBack = () => {
        router.push({path: './companyEdit', query: {reload:1,id:pageParams.companyID,siteUserId:curStepData.value.loginUserID}});
    };

    const onFinish = () => {
      router.push('/jobUserInfo/finish');
    }

    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (pageParams.total > pageParams.pageIndex * pageParams.pageSize) {
          pageParams.pageSize += 10;
          loadData(pageParams.companyID,curStepData.value.statusVal,curStepData.value.loginUserID,curStepData.value.openID);
        }
      }, 500);
    }
    const loadData = async function (companyID:any,status:any,userID:any,openId:any) {
      loading.value = true;
      pageParams.companyID = companyID;
      curStepData.value.statusVal = status;
      curStepData.value.loginUserID = userID;
      curStepData.value.openID = openId;
      //console.log("S",pageParams);
      const result:any = await getCompanyPostList(pageParams);
      postList.value = postList.value.concat(result.list);
      pageParams.total = result.total;
      //console.log(postList.value);
      loading.value = false;
    };

    const reload = (companyID:any,status:any,userID:any,openId:any) => {
      pageParams.pageIndex = 1;
      postList.value = [];
      loadData(companyID,status,userID,openId);
      /*const jsonPostList = localStorage.getItem("postData");
      postList.value = JSON.parse(jsonPostList ?? "");*/
    };

    onIonViewDidEnter(() => {
      if (route.query.reload)
        reload(route.query.id,route.query.status,route.query.userID,route.query.openId);
    });

    return {
      arrowBackOutline,
      addCircleOutline,
      trashOutline,
      route,
      router,
      loading,
      pageParams,
      curStepData,
      postList,
      stepList,
      delPostID,
      delAlertButtons,
      infoAlertButtons,
      delAlertOpen,
      infoAlertOpen,
      delLoading,
      infoAlterData,
      onBack,
      onAdd,
      onEdit,
      onFinish,
      onScroll,
      loadData,
      presentAlert,
      /*calculateEndDate,*/
      dayjs,
      setDelAlertOpen,
      setInfoAlertOpen,
      setDelLoadingOpen,
    }
  }
});
</script>

<style lang="less">
.next-btn {
  width: 100%;
  --border-radius: 0px;
  --background: #f2f2f5;
  margin: 20px 0 0 0;
  color: #363432;
  font-size: 14px;
}

.iconBtn{
  width:24px;
  height:24px;
}
.stepFlex {
  margin: 0;
  display: flex;
  width: 100%;

  .stepFlex-item {
    position: relative;
    flex: 1;
    text-align: center;
    margin-top: -10px;

    .stepFlex-item-label {
      padding-top: 60px;
      font-size: 14px;

      .stepFlex-item-label-title {
        margin-top: 30px;
      }

      .stepFlex-item-label-desc {
        margin-top: 5px;
        color: #b9b9bd;
      }
    }
  }

  .greenCircle {
    top: calc(50% - 15px);
    left: calc(50% - 4px);
    position: absolute;
    z-index: 2;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #31A2FE;
  }

  .now {
    top: calc(50% - 18px);
    left: calc(50% - 8px);
    position: absolute;
    z-index: 3;
    width: 16px;
    height: 16px;
    border-radius: 50%;
    background-color: #31A2FE;
    border: 4px solid #c5e8f9;
  }

  .grayCircle {
    top: calc(50% - 15px);
    left: calc(50% - 4px);
    position: absolute;
    z-index: 2;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #ccc;
  }

  .greenLine {
    width: 100%;
    top: calc(50% - 11px);
    left: calc(50% - 2px);
    height: 2px;
    background-color: #31A2FE;
    position: absolute;
  }

  .grayLine {
    height: 0;
    border: 1px dashed #ccc;
    width: 100%;
    top: calc(50% - 11px);
    left: calc(50% - 2px);
    position: absolute;
  }


  .post-list-page {
    .list-content {
      margin: 0px 15px !important;
      background-color: white !important;
      border-radius: 0 !important;

      ion-item {
        margin-top: 10px;
        font-size: 14px;
        border: 1px solid rgb(242, 242, 245);

        p {
          font-size: 12px;
        }
      }
    }
  }

}

</style>