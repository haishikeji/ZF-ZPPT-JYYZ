<template>
  <ion-page class="list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>推荐详情信息</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="bw-vue-form">
        <div class="form-title">推荐详情</div>
        <div class="form-detail">
          <ion-label>求职人</ion-label>
          <ion-text>{{ dataModel.name }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>求职岗位</ion-label>
          <ion-text>{{ dataModel.qzProfessionName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>招聘企业</ion-label>
          <ion-text>{{ dataModel.companyName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>招聘岗位</ion-label>
          <ion-text>{{ dataModel.zpProfessionName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>求职人联系电话</ion-label>
          <ion-text>{{ dataModel.userMobile }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>企业联系人</ion-label>
          <ion-text>{{ dataModel.companyUserName }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>企业联系电话</ion-label>
          <ion-text>{{ dataModel.companyMobile }}</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>推送时间</ion-label>
          <ion-text>{{ dayjs(dataModel.createTime).format('YYYY-MM-DD') }}</ion-text>
        </div>
        <div class="form-select">
          <ion-label>入职状态</ion-label>
          <ion-select name="entryState"  id="entryState" okText="确定" cancelText="取消" v-model="dataModel.entryState"
                      interface="action-sheet" placeholder="请选择入职状态" style="width:100%;text-align:left;" >
            <ion-select-option v-for=" (it,key) in entryStateList" :key="key" :value="it.value">
              {{ it.name }}
            </ion-select-option>
          </ion-select>
        </div>
        <div class="form-input">
          <ion-label>备注</ion-label>
          <ion-textarea rows="4" placeholder="请输入备注"
                        v-model="dataModel.remark" style="border-bottom: 1px solid #fff2e8;"></ion-textarea>
        </div>
      </div>
    </ion-content>
    <ion-footer>
      <ion-button shape="round" expand="block"
                  @click="onSetEntryState(dataModel)">提交
      </ion-button>
    </ion-footer>
  </ion-page>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import dayjs from "dayjs";
import {useRoute, useRouter} from "vue-router";
import {arrowBackOutline} from 'ionicons/icons';
import {getListById, setEntryState,saveRemark} from '@/api/recommendmgt/index'
import {onIonViewDidEnter} from '@ionic/vue'
import {getSysDictionaryList} from "@/api/system/dictionary";
import crtyptoHelp from "@/utils/crypto";

interface RecommendModel {
  companyMobile: string,
  companyName: string,
  companyUserName: string,
  entryState: any,
  entryStateName: string,
  genderName: string,
  jobHuntID: string,
  jobPeopleIsRead: string,
  jobPeopleIsReadName: string,
  name: string,
  officialAccountsID: string,
  postID: string,
  postIsRead: string,
  postIsReadName: string,
  pushName: string,
  qzProfessionName: string,
  recommendMgtID: string,
  recommendType: any,
  recommendTypeName: string,
  userMobile: string,
  zpProfessionName: string,
  remark:string
}
interface SelectProps {
  name: string,
  value: string
}
export default defineComponent({
  name: "RecommendDetail",
  setup() {
    const loading = ref(true);
    const dataModel = ref<RecommendModel>({
      companyMobile: "",
      companyName: "",
      companyUserName: "",
      entryState: null,
      entryStateName: "",
      genderName: "",
      jobHuntID: "",
      jobPeopleIsRead: "",
      jobPeopleIsReadName: "",
      name: "",
      officialAccountsID: "",
      postID: "",
      postIsRead: "",
      postIsReadName: "",
      pushName: "",
      qzProfessionName: "",
      recommendMgtID: "",
      recommendType: null,
      recommendTypeName: "",
      userMobile: "",
      zpProfessionName: "",
      remark:""
    });
    const router = useRouter();
    const route = useRoute();
    const entryStateList  = ref<SelectProps[]>([]);
    const loadData = async function (id: any) {
      loading.value = true;
      const entryStateResult :any = await getSysDictionaryList("EntryState");
      entryStateList.value = entryStateResult;
      getListById(id).then((data) => {
        dataModel.value = data;
        dataModel.value.userMobile = crtyptoHelp.decryptDesText(dataModel.value.userMobile);
      })
      loading.value = false;
    }

    const onSetEntryState = async (item: any) => {
      saveRemark(item).then(()=>{
        setEntryState(item).then(() => {
          router.push({path: './list', query: {reload:1}});
        });
      });
    }

    const onBack = () => {
      router.push({path: './list', query: {reload:1}});
    }

    onIonViewDidEnter (()=>{
      loadData(route.query.id);
    })

    return {
      dataModel,
      entryStateList,
      arrowBackOutline,
      dayjs,
      route,
      loadData,
      onSetEntryState,
      onBack
    }
  }
});
</script>
<style lang="less">

</style>