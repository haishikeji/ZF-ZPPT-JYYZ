<template>
  <ion-page class="list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="back"></ion-icon>
        </ion-buttons>
        <ion-title>
          信用记录收集
        </ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <form autocomplete="off">
        <div class="bw-vue-form">
          <div class="form-title">基本信息</div>
          <div class="form-input">
            <ion-label>录入时间<span class="danger">*</span></ion-label>
            <div class="dateTimeBox">
              <ion-datetime-button datetime="enterTime"></ion-datetime-button>
              <ion-modal :keep-contents-mounted="true">
                <ion-datetime id="enterTime" placeholder="日期"
                              v-model="dataModel.enterTime" :prefer-wheel="true"
                              dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                              :show-default-buttons="true">
                </ion-datetime>
              </ion-modal>
            </div>
          </div>
          <div class="form-input">
            <ion-label>信用记录内容<span class="danger">*</span></ion-label>
            <div>
              <ion-input name="creditRecordContent" id="creditRecordContent" class="custom"
                         placeholder="请输入信用记录内容" v-model="dataModel.creditRecordContent"></ion-input>
            </div>
          </div>
          <div class="form-input">
            <ion-label>做出列入决定部门<span class="danger">*</span></ion-label>
            <div>
              <ion-input name="decidingDepartment" id="decidingDepartment" class="custom"
                         placeholder="请输入决定部门" v-model="dataModel.decidingDepartment"></ion-input>
            </div>
          </div>
          <div class="form-input">
            <ion-label>决定文书号<span class="danger">*</span></ion-label>
            <div>
              <ion-input name="documentNumber" id="documentNumber" class="custom"
                         placeholder="请输入决定文书号" v-model="dataModel.documentNumber"></ion-input>
            </div>
          </div>
          <div class="form-input">
            <ion-label>列入日期<span class="danger">*</span></ion-label>
            <div class="dateTimeBox">
              <ion-datetime-button datetime="includeTime"></ion-datetime-button>
              <ion-modal :keep-contents-mounted="true">
                <ion-datetime id="includeTime" placeholder="日期"
                              v-model="dataModel.includeTime" :prefer-wheel="true"
                              dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                              :show-default-buttons="true">
                </ion-datetime>
              </ion-modal>
            </div>
          </div>
          <div class="form-input">
            <ion-label>到期日期<span class="danger">*</span></ion-label>
            <div class="dateTimeBox">
              <ion-datetime-button datetime="expirationTime"></ion-datetime-button>
              <ion-modal :keep-contents-mounted="true">
                <ion-datetime id="expirationTime" placeholder="日期"
                              v-model="dataModel.expirationTime" :prefer-wheel="true"
                              dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                              :show-default-buttons="true">
                </ion-datetime>
              </ion-modal>
            </div>
          </div>
          <div class="form-input">
            <ion-label>来源<span class="danger">*</span></ion-label>
            <div>
              <ion-input name="source" id="source" class="custom"
                         placeholder="请输入来源" v-model="dataModel.source"></ion-input>
            </div>
          </div>
          <div class="form-select">
            <ion-label>是否有效<span class="danger">*</span></ion-label>
            <ion-select name="isEffective" id="isEffective" okText="确定" cancelText="取消"
                        v-model="dataModel.isEffective"
                        interface="action-sheet" placeholder="请选择是否有效" style="width:100%;text-align:left;">
              <ion-select-option :value="0">是</ion-select-option>
              <ion-select-option :value="1">否</ion-select-option>
            </ion-select>
          </div>
          <div class="form-select">
            <ion-label>类别<span class="danger">*</span></ion-label>
            <ion-select name="departmentTypeID" id="departmentTypeID" okText="确定" cancelText="取消"
                        v-model="dataModel.departmentTypeID"
                        interface="action-sheet" placeholder="请选择类别" style="width:100%;text-align:left;">
              <ion-select-option v-for="(it,key) in creditRecordDepartmentList" :key="key" :value="it.value">
                {{ it.name }}
              </ion-select-option>
            </ion-select>
          </div>
        </div>
      </form>
    </ion-content>
    <ion-footer>
      <ion-toolbar>
        <ion-button style="width: 100%;" @click="onSave">提交</ion-button>
      </ion-toolbar>
    </ion-footer>
  </ion-page>
</template>
<script lang="ts">
import {computed, defineComponent, reactive, ref, toRefs} from "vue";
import {arrowBackOutline} from 'ionicons/icons';
import {useRoute, useRouter} from "vue-router";
import {alertController, onIonViewDidEnter} from "@ionic/vue";
import {useVuelidate} from "@vuelidate/core";
import {getSysDictionaryList} from "@/api/system/dictionary";
import {useUserStore} from "@/store/modules/user";
import {required} from "@vuelidate/validators";
import {getCreditRecordById, saveCreditRecord} from "@/api/creditRecord/creditRecord";
import dayjs from "dayjs";

interface postModel {
  dataModel: any;
}

export default defineComponent({
  name: 'PostEdit',
  setup() {
    const router = useRouter();
    const route = useRoute();
    const userStore = useUserStore();
    const userInfo = ref(userStore.getUserInfo);
    const isShow = ref<any>(false);
    const isEdit = ref<any>(false);
    const isEditCompany = ref(0);
    const isCommit = ref<any>(false);
    const formState = reactive<postModel>({
      dataModel: {}
    });
    const rules = computed(() => {
      return {
        dataModel: {
          enterTime: {required},
          creditRecordContent: {required},
          decidingDepartment: {required},
          documentNumber: {required},
          includeTime: {required},
          expirationTime: {required},
          source: {required},
          isEffective: {required},
          departmentTypeID: {required},
        }
      }
    });
    const v$ = useVuelidate(rules, formState);
    const isTrailList = ref([
      {value: true, name: '是'},
      {value: false, name: '否'},
    ]);
    const creditRecordDepartmentList = ref<Array<any>>([]);
    const refPostSelectionLike = ref();

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

    const onSetWorkCategoryID = (data: any) => {
      formState.dataModel.workCode = data.value;
      formState.dataModel.workCategoryName = data.text;
    }

    const onSave = async function () {
      isCommit.value = true;
      const isFormCorrect = await v$.value.$validate();
      if (!isFormCorrect) {
        await presentAlert('请输入完整信息！');
        return null;
      }
      if (isCommit.value && inputValid.value) {
        saveCreditRecord(formState.dataModel, userInfo.value.userID ? userInfo.value.userID : "").then(result => {
          if (result) {
            router.push({
              path: "./creditRecordList",
              query: {reload: 1, id: formState.dataModel.companyID, status: 4, random: Math.random()}
            });
          }
        })
      }
    }

    const back = () => {
      router.push({
        path: "./creditRecordList",
        query: {reload: 1, id: formState.dataModel.companyID, status: 4, isEdit: isEditCompany.value}
      });
    }

    const getCreditRecordDepartmentList = async function () {
      const data: any = await getSysDictionaryList("CreditRecordDepartment");
      creditRecordDepartmentList.value = data;
    }

    const loadData = async (creditRecordID: any, companyID: any, isEdit: any) => {
      isCommit.value = false;
      await getCreditRecordDepartmentList();
      isEditCompany.value = isEdit;
      const loginUserInfo = useUserStore().getUserInfo;
      formState.dataModel = await getCreditRecordById(creditRecordID);
      formState.dataModel.companyID = companyID;
      formState.dataModel.loginUserID = loginUserInfo.userID == undefined ? "" : loginUserInfo.userID;
      if (!creditRecordID) {
        formState.dataModel.enterTime = dayjs().format("YYYY-MM-DD");
        formState.dataModel.includeTime = dayjs().format("YYYY-MM-DD");
        formState.dataModel.expirationTime = dayjs().format("YYYY-MM-DD");
        formState.dataModel.isEffective = 0;
      }
    };

    const onIsTrailChange = () => {
      if (!formState.dataModel.isTrail) {
        formState.dataModel.trailMonths = 0;
      }
    }

    const inputValid = ref(true);

    const reload = (creditRecordID: any, companyID: any, isEdit: any) => {
      formState.dataModel.recruitCount = null;
      loadData(creditRecordID, companyID, isEdit);
    }

    onIonViewDidEnter(() => {
      if (route.query.reload)
        reload(route.query.id, route.query.companyID, route.query.isEdit);
    });


    return {
      ...toRefs(formState),
      arrowBackOutline,
      isCommit,
      route,
      router,
      isShow,
      isEdit,
      isTrailList,
      v$,
      refPostSelectionLike,
      onIsTrailChange,
      onSetWorkCategoryID,
      onSave,
      back,
      creditRecordDepartmentList
    }
  }
});
</script>

<style lang="less">
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