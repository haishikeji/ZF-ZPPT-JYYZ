<template>
  <ion-page class="list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>就业服务活动详情</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="bw-vue-form">
        <div class="form-title">就业服务活动详情</div>
        <div class="form-detail">
          <ion-label>活动名称</ion-label>
          <ion-text>{{ jobFair.dataModel.name }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>活动类型</ion-label>
          <ion-text>{{ jobFair.dataModel.activityTypeName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>主办单位</ion-label>
          <ion-text>{{ jobFair.dataModel.zbUnit }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>承办单位</ion-label>
          <ion-text>{{ jobFair.dataModel.cbUnit }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>活动地址</ion-label>
          <ion-text>{{ jobFair.dataModel.address }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>企业数量</ion-label>
          <ion-text>
            {{ jobFair.dataModel.companyCount }}
          </ion-text>
        </div>
        <div class="form-detail">
          <ion-label>活动时间</ion-label>
          <ion-text v-if="jobFair.dataModel.startTime && jobFair.dataModel.endTime">
            {{ dayjs(jobFair.dataModel.startTime).format("YYYY-MM-DD") }} 至
            {{ dayjs(jobFair.dataModel.endTime).format("YYYY-MM-DD") }}
          </ion-text>
        </div>
        <div class="form-detail">
          <ion-label>联系人</ion-label>
          <ion-text>{{ jobFair.dataModel.userName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>联系电话</ion-label>
          <ion-text>{{ jobFair.dataModel.userMrobile }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>所属县区</ion-label>
          <ion-text>{{ jobFair.dataModel.regionName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>所属驿站</ion-label>
          <ion-text>{{ jobFair.dataModel.siteName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>摊位数</ion-label>
          <ion-text>{{ jobFair.dataModel.boothCount }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>展位数量</ion-label>
          <ion-text>{{ jobFair.dataModel.displayCount }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>是否制作海报</ion-label>
          <ion-text>{{ jobFair.dataModel.isMake ? '是' : '否' }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>乘车路线</ion-label>
          <ion-text>{{ jobFair.dataModel.carLine }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>活动描述</ion-label>
          <ion-text>{{ jobFair.dataModel.jobFariDesc }}</ion-text>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {arrowBackOutline} from "ionicons/icons";
import {useRoute, useRouter} from "vue-router";
import {reactive, ref} from "vue";
import dayjs from "dayjs";
import {onIonViewDidEnter} from "@ionic/vue";
import {getPostByID} from "@/api/post";
import {getJobFairById} from "@/api/jobFair";

const router = useRouter();
const route = useRoute();

const jobFair = reactive({
  dataModel: {
    jobfairsID: "",
    name: "",
    activityTypeName: "",
    zbUnit: "",
    cbUnit: "",
    address: "",
    companyCount: "",
    startTime: "",
    endTime: "",
    userName: "",
    userMrobile: "",
    regionName: "",
    siteName: "",
    boothCount: "",
    displayCount: "",
    isMake: "",
    longitude: "",
    latitude: "",
    carLine: "",
    jobFariDesc: ""
  }
})
const loading = ref(false);

function onBack() {
  router.go(-1)
}

const loadData = async function (id: any) {
  loading.value = true;
  getJobFairById(id).then((data: any) => {
    jobFair.dataModel = data
  })
  loading.value = false;
}

onIonViewDidEnter(() => {
  loadData(route.query.id);
});
</script>

<style scoped>

</style>