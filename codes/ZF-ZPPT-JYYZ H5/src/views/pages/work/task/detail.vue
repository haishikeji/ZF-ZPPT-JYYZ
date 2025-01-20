<template>
  <ion-page>
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onCancel"></ion-icon>
        </ion-buttons>
        <ion-title>工作任务完成情况</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="bw-vue-form">
        <div class="form-title">工作任务详细信息</div>
        <div class="form-detail">
          <ion-label>任务名称</ion-label>
          <ion-text>{{ taskInfo.doTaskName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>要求完成时间</ion-label>
          <ion-text>{{ taskInfo.finishTime ? dayjs(taskInfo.finishTime).format("YYYY-MM-DD") : '' }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>任务类型</ion-label>
          <ion-text>{{ taskInfo.workTypeName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>任务内容</ion-label>
          <ion-text>{{ taskInfo.content }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>任务完成情况</ion-label>
          <ion-text>{{ taskInfo.taskStatusName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>任务完成时间</ion-label>
          <ion-text>{{ dayjs(taskInfo.completeTime).format("YYYY-MM-DD") }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>任务完成详情</ion-label>
          <ion-text>{{ taskInfo.completeDesc }}</ion-text>
        </div>
      </div>
    </ion-content>

    <ion-loading
        :is-open="bodyLoading"
        message="加载中..."
        @didDismiss="setBodyLoadingOpen(false)">
    </ion-loading>
  </ion-page>
</template>

<script setup lang="ts">

import {arrowBackOutline} from "ionicons/icons";
import {useRoute, useRouter} from "vue-router";
import dayjs from "dayjs";
import {onMounted, reactive, ref} from "vue";
import {getMyWorkTasks} from "@/api/workTask";

const router = useRouter();
const route = useRoute();

// 任务信息
const taskInfo = reactive<any>({
  doTaskName: "",
  finishTime: "",
  workTypeName: "",
  content: "",
  taskStatusName: "",
  completeTime: "",
  completeDesc: ""
})
// 查询数据
const searchParamsState = reactive({
  pageIndex: 1,
  pageSize: 10,
  total: 0,
  doTaskID: '',
  taskName: ''
});
// 页面加载动画开关
const bodyLoading = ref(false);

function loadData(doTaskID: any) {
  bodyLoading.value = true;

  searchParamsState.doTaskID = doTaskID;
  getMyWorkTasks(searchParamsState).then((result: any) => {
    const data = result.list[0];
    Object.keys(taskInfo).forEach(key => {
      taskInfo[key] = data[key];
    })
  })
  console.log(taskInfo);
  bodyLoading.value = false;
}

// 设置加载动画状态
function setBodyLoadingOpen(value: boolean) {
  bodyLoading.value = value;
}

// 返回
function onCancel() {
  router.go(-1);
}

// 初始化
onMounted(() => {
  loadData(route.query.doTaskID)
})
</script>

<style scoped>

</style>