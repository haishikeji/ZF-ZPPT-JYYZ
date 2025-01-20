<template>
  <ion-page>
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onCancel"
                    style="padding-left:10px;width:24px;height:24px;"></ion-icon>
        </ion-buttons>
        <ion-title>个人荣誉收集</ion-title>
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
            <p class="stepFlex-item-label-title" @click="onPathRedirect(record.val)">{{ record.title }}</p>
            <p class="stepFlex-item-label-desc" @click="onPathRedirect(record.val)">{{ record.desc }}</p>
          </div>
        </div>
      </div>

      <form ref="editForm" @submit.prevent="onSave">
        <div class="bw-vue-form">
          <ion-list class="canvasWrapper">
            <div class="form-title">个人荣誉</div>
            <div class="form-input">
              <ion-label>荣誉名称<span class="danger">*</span></ion-label>
              <ion-input name="schoolName" id="schoolName" class="custom"
                         placeholder="请输入荣誉名称" v-model="honorData.dataModel.honorName"></ion-input>
            </div>
            <div class="form-input">
              <ion-label>
                获得时间<span class="danger">*</span>
              </ion-label>
              <div class="dateTimeBox">
                <ion-datetime-button datetime="completeTime"></ion-datetime-button>
                <ion-modal :keep-contents-mounted="true">
                  <ion-datetime id="completeTime" placeholder="日期"
                                v-model="honorData.dataModel.getTime" :prefer-wheel="true"
                                dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                                :show-default-buttons="true">
                  </ion-datetime>
                </ion-modal>
              </div>
            </div>
          </ion-list>
        </div>
      </form>
    </ion-content>
    <ion-footer>
      <ion-toolbar>
        <ion-button style="width:100%;" @click="onSave">保 存</ion-button>
      </ion-toolbar>
    </ion-footer>
    <ion-loading
        :is-open="loading"
        message="加载中..."
        @didDismiss="setOpen(false)">
    </ion-loading>
  </ion-page>
</template>

<script setup lang="ts">
import {arrowBackOutline} from "ionicons/icons";
import {useRoute, useRouter} from "vue-router";
import {computed, reactive, ref} from "vue";
import {getHonorByID, saveHonor} from "@/api/honor/honor";
import {alertController, onIonViewDidEnter} from "@ionic/vue";
import {required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import dayjs from "dayjs";

const router = useRouter();
const route = useRoute();

const honorData = reactive({
  dataModel: {
    honorID: "",
    jobUserID: "",
    honorName: '',
    getTime: dayjs().format("YYYY-MM-DD"),
  }
});
const curStepData = ref({
  name: "",
  statusVal: 2
});
const stepList = ref([
  {title: '基础信息', desc: '个人基础信息', val: 1},
  {title: '教育经历', desc: '完善教育经历', val: 2},
  {title: '工作经验', desc: '完善工作经验', val: 3},
  {title: '求职意向', desc: '个人求职意向', val: 4},
  {title: '个人荣誉', desc: '完善个人荣誉', val: 5},
]);
const loading = ref(false);
const isEditJobUser = ref(0);
const presentAlert = async (message: string) => {
  const alert = await alertController.create({
    header: '错误！',
    message: message,
    buttons: [
      '确定'
    ],
  });
  await alert.present();
}
const honorRules = computed(() => {
  return {
    dataModel: {
      honorName: {required},
      getTime: {required},
    }
  }
});
const honorValid = useVuelidate(honorRules, honorData);

const loadData = async (honorID: any, jobUserID: any, status: any, isEdit: any) => {
  loading.value = true;
  curStepData.value.statusVal = status;
  isEditJobUser.value = isEdit;
  await getHonorByID(honorID).then((result: any) => {
    honorData.dataModel.honorID = result.honorID;
    honorData.dataModel.honorName = result.honorName;
    honorData.dataModel.getTime = dayjs(result.getTime).format("YYYY-MM-DD");
    honorData.dataModel.jobUserID = jobUserID;
  }).finally(() => {
    loading.value = false;
  })
};

const reload = async (honorID: any, jobUserID: any, status: any, isEdit: any) => {
  await loadData(honorID, jobUserID, status, isEdit);
}

const onCancel = () => {
  router.push({path: './edit', query: {reload: 1, jobUserID: honorData.dataModel.jobUserID, status: 5}});
}

const onPathRedirect = (statusValue: any) => {
  if (isEditJobUser.value == 1) router.push({
    path: './edit',
    query: {reload: 1, jobUserID: honorData.dataModel.jobUserID, status: statusValue, isEdit: isEditJobUser.value}
  });
}
const onSave = async function () {
  const isFormCorrect = await honorValid.value.$validate();
  if (!isFormCorrect) {
    await presentAlert("请填写完整的信息！");
    return null;
  }
  saveHonor(honorData.dataModel).then(result => {
    if (result) {
      honorData.dataModel.honorID = "";
      honorData.dataModel.honorName = "";
      honorData.dataModel.getTime = dayjs().format("YYYY-MM-DD");
      router.push({path: './edit', query: {reload: 1, jobUserID: honorData.dataModel.jobUserID, status: 5}});
    }
  });
}

const setOpen = (isOpen: boolean) => {
  loading.value = isOpen;
};

onIonViewDidEnter(() => {
  if (route.query.reload)
    reload(route.query.honorID, route.query.jobUserID, route.query.status, route.query.isEdit);
});
</script>

<style lang="less">
ion-input.custom {
  --placeholder-color: gray;
  --placeholder-opacity: 0.5;
}

.title-item {
  margin-left: 15px;
  color: #1c3d70 !important;
  font-size: 14px !important;
  font-weight: bold;
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
}

.dateTimeBox {
  width: 100%;
  display: flex;
  margin-top: 5px;
  justify-content: space-between;
  align-content: center;
}
</style>