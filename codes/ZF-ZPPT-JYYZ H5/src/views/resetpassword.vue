<template>
  <ion-page>
    <ion-content>
      <div class="login-top"></div>
      <div class="login-content">
        <div class="login-item" style="color: #f50909">
          {{ dataModel.userName }}，第一次登录，请重置密码！
        </div>
        <div class="login-item">
          <ion-item>
            <input
              v-model="dataModel.pwd_default"
              name="pwd_default"
              placeholder="请输入8到16位新密码"
              type="password"
            />
          </ion-item>
          <ion-note
            style="color: #f50909"
            v-if="v$.pwd_default.$error"
            slot="error"
            >密码必须包含字母、数字和特殊字符，且长度要在8到16位。
          </ion-note>
        </div>
        <div class="login-item">
          <ion-item>
            <input
              v-model="dataModel.pwd_confirm"
              name="pwd_confirm"
              placeholder="请再输入8到16位新密码"
              type="password"
            />
          </ion-item>
          <ion-note
            style="color: #f50909"
            v-if="v$.pwd_confirm.$error"
            slot="error"
            >密码必须包含字母、数字和特殊字符，且长度要在8到16位。
          </ion-note>
        </div>
        <div class="login-btn">
          <ion-buttons>
            <ion-button @click="onSave()"> 修改密码 </ion-button>
          </ion-buttons>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import router from "@/router";
import { useUserStore } from "@/store/modules/user";
import { computed, onMounted, reactive, ref } from "vue";
import { minLength, required, maxLength } from "@vuelidate/validators";
import { useVuelidate } from "@vuelidate/core";
import { updateUserPassword } from "@/api/account";
import { onIonViewDidEnter } from "@ionic/vue";

const userStore = useUserStore();
const userInfo = reactive(userStore.getUserInfo);
const dataModel = ref({
  userID: "",
  userName: "",
  loginID: "",
  pwd_default: "",
  pwd_confirm: "",
});
const passwordRegex = /(?=.*[a-z_])(?=.*\d)(?=.*[^a-z0-9_])[\S]{8,}/i;
// 定义校验器
const password = (value: any) => {
  return passwordRegex.test(value);
};

const rules = computed(() => {
  return {
    pwd_default: {
      required,
      minLength: minLength(8),
      maxLength: maxLength(16),
      password,
    },
    pwd_confirm: {
      required,
      minLength: minLength(8),
      maxLength: maxLength(16),
      password,
    },
  };
});
const v$ = useVuelidate(rules, dataModel);

const onSave = async () => {
  const isFormCorrect = await v$.value.$validate();

  console.log(isFormCorrect);

  if (!isFormCorrect) {
    return null;
  }

  updateUserPassword(dataModel.value as any).then((result) => {
    if (result) {
      router.push("/tabs/tabUser/myInfo");
    }
  });
};

onIonViewDidEnter(() => {
  dataModel.value.userID = userInfo.userID || "";
  dataModel.value.userName = userInfo.siteUserName || "";
  dataModel.value.loginID = userInfo.loginID || "";
  dataModel.value.pwd_default = "";
  dataModel.value.pwd_confirm = "";
});
</script>

<style lang="less">
ion-content {
  --background: #ffffff !important;
  height: 100%;
}

.login-top {
  background-image: url("./../assets/icon/login-bg.png");
  width: 100%;
  height: 35%;
  background-size: 100% 100%;
}

.login-content {
  position: relative;
  padding: 40px 40px;
  top: -35px;
  border-radius: 30px;
  background: #ffffff;
  /*margin: 0 20px;*/

  .login-logo {
    margin-bottom: 15px;
    padding-right: 5px;
    text-align: center;
  }

  .login-system {
    margin-bottom: 15px;
    text-align: center;

    img {
      height: 50px;
    }
  }

  .login-item {
    margin-bottom: 15px;

    ion-item {
      --inner-border-width: 0px;
      --background: #f3f3f3;
      border-radius: 30px;

      ion-icon {
        color: #b7b8bb;
      }

      ion-input {
        color: #7a7b80;
      }

      input {
        color: #7a7b80;
        background-color: #f3f3f3;
        height: 100%;
        border: none;
        width: 100%;
      }

      input:focus {
        box-shadow: none;
        border: none;
        outline: none;
      }

      input::placeholder {
        color: #b4b4b7;
      }
    }
  }

  .login-btn {
    border: none;
  }

  ion-item::part(native) {
    padding: 0 20px 0 20px;
    border: none;
  }

  ion-buttons {
    margin: 30px 0px;

    ion-button {
      width: 100%;
      color: white;
      --background: #39a9ff !important;
      --box-shadow: 0px 1px 5px #39a9ff;
      --border-radius: 30px !important;
      height: 40px;
      font-size: 18px;
    }
  }

  .error-label {
    text-align: center;

    p {
      color: red;
    }
  }
}
</style>
