<template>
  <ion-page>
    <ion-content>
      <div class="tab-main-content">
        <div class="main_top"></div>
        <div class="tool-box">
          <a class="tool-item box-line"
             @click="saveSysLog('我的二维码（APP）', '/tabs/tabMain/userQrCode/index', '点击菜单', router.push('/tabs/tabMain/userQrCode/index'))">
            <div class="tool-img">
              <img src="@/assets/icon/qrcode.png">
            </div>
            <div class="tool-title">我的二维码</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('求职人员收集（APP）', '/tabs/tabMain/jobhunt/list', '点击菜单', router.push('/tabs/tabMain/jobhunt/list'))">
            <div class="tool-img">
              <img src="@/assets/icon/qzxx.png">
            </div>
            <div class="tool-title">求职人员收集</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('求职意向（APP）', '/tabs/tabMain/jobhunt/recommend/list', '点击菜单', router.push('/tabs/tabMain/jobhunt/recommend/list'))">
            <div class="tool-img">
              <img src="@/assets/icon/qzyx.png">
            </div>
            <div class="tool-title">求职意向</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('企业信息收集（APP）', '/tabs/tabMain/company/list', '点击菜单', router.push({path:'/tabs/tabMain/company/list',query:{reload:1}}))">
            <div class="tool-img">
              <img src="@/assets/icon/qyxx.png">
            </div>
            <div class="tool-title">企业信息<br/>收集</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('岗位信息（APP）', '/tabs/tabMain/post/list', '点击菜单', router.push('/tabs/tabMain/post/list'))">
            <div class="tool-img">
              <img src="@/assets/icon/gwxxgl.png">
            </div>
            <div class="tool-title">岗位信息</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('走访签到（APP）', '/tabs/tabWork/work/signin', '点击菜单', router.push('/tabs/tabWork/work/signin'))">
            <div class="tool-img">
              <img src="@/assets/icon/map.png">
            </div>
            <div class="tool-title">走访签到</div>
          </a>
          <a class="tool-item box-line"
             @click="saveSysLog('工作任务（APP）', '/tabs/tabMain/workTask/list', '点击菜单', router.push('/tabs/tabMain/workTask/list'))">
            <div class="tool-img">
              <img src="@/assets/icon/gzrw.png">
            </div>
            <div class="tool-title">工作任务</div>
          </a>


          <a class="tool-item box-line"
             @click="saveSysLog('工作备忘（APP）', '/tabs/tabMain/workLog/list', '点击菜单', router.push('/tabs/tabMain/workLog/list'))">
            <div class="tool-img">
              <img src="@/assets/icon/gzrz.png">
            </div>
            <div class="tool-title">工作备忘</div>
          </a>
        </div>
        <div class="panel">
          <div class="panel_title">
            <div class="panel_title_text">工作任务安排</div>
            <div class="panel_more" @click="onTaskList">更多＞</div>
          </div>
          <div class="panel_content">
            <div style="padding: 10px 0;">
              <ion-item style="--inner-border-width: 0px;" v-for="(record,key) in dataList" :key="key" detail
                        @click="onDetail(record.doTaskID)" v-show="record.taskStatus==0">
                <span class="b-badge"
                      :class="record.taskLevel==1?'b-badge-dange':(record.taskLevel==2?'b-badge-warning':'b-badge-default')"></span>
                <ion-label style="color: #404040;">
                  {{ record.doTaskName }}
                </ion-label>
                <ion-label slot="end" style="color: #899099;">
                  {{ dayjs(record.finishTime).format("MM-DD") }}
                </ion-label>
              </ion-item>
            </div>
            <b-empty v-if="dataList.length<=0" :loading="loading"/>
          </div>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import {defineComponent, reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {getMyWorkTasks} from '@/api/workTask';
import {alertController, onIonViewDidEnter} from "@ionic/vue";
import dayjs from "dayjs";
import BEmpty from "@/components/empty.vue";
import {saveLog} from "@/api/system/sysLog";
import {saveSysLog} from "@/utils/common";

interface taskModel {
  doTaskID: string,
  taskStatus: number
}

export default defineComponent({
  name: "TabMainList",
  methods: {saveSysLog},
  components: {BEmpty},
  setup() {
    const router = useRouter();
    const dataList = ref<taskModel[]>([]);
    const loading = ref(true);
    const searchParamsState = reactive({
      pageIndex: 1,
      pageSize: 5,
      total: 0,
    });

    const loadData = async function () {
      loading.value = true;
      const result: any = await getMyWorkTasks(searchParamsState);
      dataList.value = [];
      dataList.value = dataList.value.concat(result.list);
      console.log("dataList", dataList.value);
      loading.value = false;
    }

    const onDetail = (doTaskID: string) => {
      router.push({path: "/tabs/tabMain/workTask/edit", query: {reload: 1, doTaskID: doTaskID}});
    }

    function onTaskList() {
      saveLog('工作任务（APP）', '/tabs/tabMain/workTask/list', '点击菜单');
      router.push("/tabs/tabMain/workTask/list");
    }

    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (searchParamsState.total > searchParamsState.pageIndex * searchParamsState.pageSize) {
          searchParamsState.pageIndex += 1;
          loadData();
        }
      }, 500);
    }
    const reload = () => {
      searchParamsState.pageIndex = 1;
      loadData();
    }

    onIonViewDidEnter(() => {
      reload();
    });

    const alertTaskWarning = async () => {
      const result: any = await getMyWorkTasks({
        pageIndex: 1,
        pageSize: 1000,
      });

      const taskWarningList = result.list.filter((it: any) => it.taskLevel === 1 && it.taskStatus === 0);

      if (taskWarningList.length > 0) {
        const alert = await alertController.create({
          header: '任务提醒',
          message: '您有' + taskWarningList.length + '条特别重要任务需要处理！请及时处理！',
          buttons: [
            {
              text: '去处理',
              handler: () => {
                onTaskList();
              }
            }
          ],
          cssClass: 'task_warning_alert'
        });

        await alert.present();
      }

    };

    alertTaskWarning();

    return {
      router,
      loading,
      dataList,
      searchParamsState,
      loadData,
      onDetail,
      onScroll,
      reload,
      dayjs,
      onTaskList
    }
  }
});
</script>

<style lang="less">
.tab-main-content {
  background-image: url("../../assets/icon/main_bg_2.png");
  width: 100%;
  height: 100%;
  background-size: contain;
  background-repeat: no-repeat;
  background-color: #F7F8FC;
  background-attachment: local;
  overflow-y: auto;
  padding-bottom: 15px;
  background-size: cover;

  .main_top {
    background-image: url("../../assets/icon/main_top_bg.png");
    height: 150px;
    background-size: contain;
    background-repeat: no-repeat;
    margin: 50px 15px 10px 15px;
    background-size: contain;
    background-position: center;
  }

  .main-title {
    font-size: 24px;
    color: white;
    margin: 40px 0 30px 0;
    text-align: center;
  }

  .item-detail {
    margin: 10px;
    border-radius: 8px;
    color: #380F0A;
    font-size: 15px;
  }


  .panel {
    margin: 10px;

    .panel_title {
      background-image: url("../../assets/icon/panel_title_bg.png");
      height: 40px;
      background-repeat: no-repeat;
      background-size: contain;
      display: flex;
      justify-content: space-between;
      width: 100%;

      .panel_title_text {
        padding: 6px 28px;
        font-weight: bold;
        font-size: 16px;
      }

      .panel_more {
        padding: 8px;
        color: #007EFF;
        font-size: 14px;
      }
    }

    .panel_content {
      min-height: 180px;
      background-color: white;
      border-radius: 10px;
      border-top-left-radius: 0px;
      position: relative;
      top: -1px;
    }
  }


  .tool-box {
    display: flex;
    flex-wrap: wrap;
    /*padding: 10px 0;*/

    .tool-item {
      flex-direction: column;
      flex: 0 0 25%;
      margin: 10px 0;
      text-decoration: none;
      cursor: pointer;
      width: 25%;
      padding: 0 2px;

      ion-img {
        height: 35px;
        width: 43px;
      }

      .tool-img {
        display: flex;
        justify-content: center;

        img {
          width: 36px;
          height: 36px;
        }
      }

      .tool-title {
        color: #5E4545;
        margin-top: 10px;
        display: flex;
        justify-content: center;
        font-size: 14px;
        text-align: center;
      }
    }
  }
}

.task_warning_alert .alert-title,.task_warning_alert .alert-message{
  color: red;
}

</style>
