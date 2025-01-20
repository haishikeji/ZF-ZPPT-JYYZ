<template>
  <ion-page class="edit-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="()=>router.push('./')"></ion-icon>
        </ion-buttons>
        <ion-title>修改密码</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="bw-vue-form">
        <div class="form-detail">
          <ion-label>用户名</ion-label>
          <ion-text>{{ dataModel.userName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>账号</ion-label>
          <ion-text>{{ dataModel.loginID }}</ion-text>
        </div>
      </div>
      <form class="form-table">
        <div class="bw-vue-form">
          <div class="form-input">
            <ion-label>新密码</ion-label>
            <ion-input v-model="dataModel.pwd_default" name="pwd_default"
                       placeholder="请填写8到16位新密码" type="password"></ion-input>
            <ion-note style="color: #f50909" v-if="v$.pwd_default.$error" slot="error">密码必须包含字母、数字和特殊字符，且长度要在8到16位。
            </ion-note>
          </div>
          <div class="form-input">
            <ion-label>确认新密码</ion-label>
            <ion-input v-model="dataModel.pwd_confirm" name="pwd_confirm"
                       placeholder="请填写8到16位新密码" type="password"></ion-input>
            <ion-note style="color: #f50909" v-if="v$.pwd_confirm.$error" slot="error">密码必须包含字母、数字和特殊字符，且长度要在8到16位。
            </ion-note>
          </div>
        </div>
      </form>
    </ion-content>
    <ion-footer>
      <div class="edit-button">
        <ion-button color="light" @click="onCancel()">取消</ion-button>
        <ion-button @click="onSave">提交</ion-button>
      </div>
    </ion-footer>
  </ion-page>
</template>
<script setup lang="ts">
import {arrowBackOutline} from 'ionicons/icons';
import router from '@/router';
import {useUserStore} from '@/store/modules/user';
import {computed, onMounted, reactive, ref} from 'vue';
import {minLength, required, maxLength} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {updateUserPassword} from "@/api/account";
import {onIonViewDidEnter} from "@ionic/vue";

const userStore = useUserStore();
const userInfo = reactive(userStore.getUserInfo);
const dataModel = ref({userID: '', userName: '', loginID: '', pwd_default: '', pwd_confirm: ''});
const passwordRegex = /(?=.*[a-z_])(?=.*\d)(?=.*[^a-z0-9_])[\S]{8,}/i;
// 定义校验器
const password = (value:any) => {
  return passwordRegex.test(value);
};

const rules = computed(() => {
  return {
    pwd_default: {required, minLength: minLength(8), maxLength: maxLength(16),password},
    pwd_confirm: {required, minLength: minLength(8), maxLength: maxLength(16),password}
  }
});
const v$ = useVuelidate(rules, dataModel);

const onSave = async () => {
  const isFormCorrect = await v$.value.$validate();

  console.log(isFormCorrect)

  if (!isFormCorrect) {
    return null;
  }

  updateUserPassword(dataModel.value as any).then(result => {
    if (result) {
      router.push("/tabs/tabUser");
    }
  });
}

const onCancel = () => {
  router.push("/tabs/tabUser");
}

onIonViewDidEnter(() => {
  dataModel.value.userID = userInfo.userID || '';
  dataModel.value.userName = userInfo.siteUserName || '';
  dataModel.value.loginID = userInfo.loginID || '';
  dataModel.value.pwd_default = "";
  dataModel.value.pwd_confirm = "";
});


</script>

<style scoped lang="less">
ion-content {
  --background: #ffffff !important;
}

ion-item {
  --inner-border-width: 0px 0px 1px 0px;
  margin-right: 10px;
}

</style>
