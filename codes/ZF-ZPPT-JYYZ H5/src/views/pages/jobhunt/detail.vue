<template>
  <ion-page>
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>
          {{ curTitle }}
        </ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div
          style="margin-top: 7px;height: 35px; display: grid; grid-template-columns: repeat(5, minmax(0, 1fr));justify-content: center;">
        <ion-label @click="onTabChange(1)" class="tab-item">
          <span :class="curTabIndex==1?'active':'passive'">个人信息</span>
        </ion-label>
        <ion-label  @click="onTabChange(2)" class="tab-item">
          <span :class="curTabIndex>=2&&curTabIndex<3?'active':'passive '">教育经历</span>
        </ion-label>
        <ion-label @click="onTabChange(3)" class="tab-item">
          <span :class="curTabIndex>=3&&curTabIndex<4?'active':'passive '">工作经验</span>
        </ion-label>
        <ion-label @click="onTabChange(4)" class="tab-item">
          <span :class="curTabIndex>=4&&curTabIndex<5?'active':'passive '">求职意向</span>
        </ion-label>
        <ion-label @click="onTabChange(5)" class="tab-item">
          <span :class="curTabIndex>=5?'active':'passive '">个人荣誉</span>
        </ion-label>
    </div>
      <div class="bw-vue-form">
        <div v-show="curTabIndex==1">
          <div class="form-title" >&nbsp;</div>
          <div class="form-detail">
            <ion-label>姓名</ion-label>
            <ion-text>{{ dataModel.name }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>性别</ion-label>
            <ion-text>{{ dataModel.genderName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>联系电话</ion-label>
            <ion-text>{{ dataModel.userMobile }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>所属驿站</ion-label>
            <ion-text>{{ dataModel.siteName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>所属街道</ion-label>
            <ion-text v-if="dataModel.regionName!=null&&dataModel.streetName!=null">{{ dataModel.regionName+"-"+dataModel.streetName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>居住地址</ion-label>
            <ion-text>{{ dataModel.address }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>就业状态</ion-label>
            <ion-text>{{ dataModel.jobStatusName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>重点人员类别</ion-label>
            <ion-text>{{ dataModel.keyTypeName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>毕业日期</ion-label>
            <ion-text>{{ dataModel.finishDate }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>求职人员标签</ion-label>
            <ion-text >{{ dataModel.tagName }}</ion-text>
          </div>
<!--          <div class="form-detail">-->
<!--            <ion-label>社会保障卡号</ion-label>-->
<!--            <ion-text>{{ dataModel.socialSecurityCard }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>政治面貌</ion-label>-->
<!--            <ion-text>{{ dataModel.politicsStatusName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>民族</ion-label>-->
<!--            <ion-text>{{ dataModel.nationName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>毕业院校</ion-label>-->
<!--            <ion-text>{{ dataModel.finishSchool }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>专业</ion-label>-->
<!--            <ion-text>{{ dataModel.profession }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>是否非全日制</ion-label>-->
<!--            <ion-text>{{ dataModel.isFullTimeName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>职业资格类别</ion-label>-->
<!--            <ion-text style="width: 52%;text-align:right;">{{ dataModel.occupationalCategoryName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>职业资格等级</ion-label>-->
<!--            <ion-text>{{ dataModel.occupationalLevelName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>籍贯</ion-label>-->
<!--            <ion-text>{{ dataModel.nativePlace }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>出生日期</ion-label>-->
<!--            <ion-text v-if="dataModel.birthDay!=null">{{ dayjs(dataModel.birthDay).format("YYYY-MM-DD") }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>户口性质</ion-label>-->
<!--            <ion-text>{{ dataModel.familyNatureName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>最高学历</ion-label>-->
<!--            <ion-text>{{ dataModel.cultureName}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>健康状况</ion-label>-->
<!--            <ion-text>{{ dataModel.healthName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail" name="bloodTypeName">-->
<!--            <ion-label>血型</ion-label>-->
<!--            <ion-text>{{ dataModel.bloodTypeName }}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>身高</ion-label>-->
<!--            <ion-text v-if="dataModel.height!=null">{{ dataModel.height +"cm"}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>视力</ion-label>-->
<!--            <ion-text>{{ dataModel.vision}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>体重</ion-label>-->
<!--            <ion-text  v-if="dataModel.weight!=null">{{ dataModel.weight +"kg"}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail" name="maritalStatusName">-->
<!--            <ion-label>婚姻状况</ion-label>-->
<!--            <ion-text>{{ dataModel.maritalStatusName}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>联系人</ion-label>-->
<!--            <ion-text>{{ dataModel.userName}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>电子邮箱</ion-label>-->
<!--            <ion-text>{{ dataModel.email}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
<!--            <ion-label>家庭住址</ion-label>-->
<!--            <ion-text>{{ dataModel.familyAddress}}</ion-text>-->
<!--          </div>-->
<!--          <div class="form-detail">-->
          <!--            <ion-label>个人评价</ion-label>-->
<!--            <ion-text>{{ dataModel.hobby}}</ion-text>-->
<!--          </div>-->
        </div>
        <div v-show="curTabIndex==2">
          <br/>
          <div v-if="!loading" id="educationList" >
            <div v-for="(record,key) in curUserEducationList" :key="key" >
              <ion-item detail @click="onEducationEdit(record.educationID)" class="ion-item">
                <ion-label>
                  <h2>
                    {{ record.schoolName }}
                  </h2>
                  <p v-if="record.schoolTime!=null&&record.overTime!=null">
                    {{ dayjs(record.schoolTime).format("YYYY-MM-DD") }}至{{ dayjs(record.overTime).format("YYYY-MM-DD") }}
                  </p>
                </ion-label>
              </ion-item>
            </div>
          </div>
        </div>
        <div v-show="curTabIndex==2.5">

          <div class="form-detail">
            <ion-label>毕业院校/培训机构</ion-label>
            <ion-text>{{ curUserEducationInfo.schoolName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>最高学历</ion-label>
            <ion-text>{{ curUserEducationInfo.cultureRankName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>专业</ion-label>
            <ion-text>{{ curUserEducationInfo.major }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>起止日期</ion-label>
            <ion-text v-if="curUserEducationInfo.schoolTime!=null&&curUserEducationInfo.overTime!=null">
              {{ dayjs(curUserEducationInfo.schoolTime).format("YYYY-MM-DD") }}至{{ dayjs(curUserEducationInfo.overTime).format("YYYY-MM-DD") }}
            </ion-text>
          </div>
        </div>
        <div v-show="curTabIndex==3">
          <br/>
          <div v-if="!loading" id="experienceList" >
            <div v-for="(record,key) in curUserExperienceList" :key="key" >
              <ion-item detail @click="onExperienceEdit(record.experienceID)" class="ion-item">
                <ion-label>
                  <h2>
                    {{ record.workAddress }}
                  </h2>
                  <p v-if="record.startTime!=null&&record.endTime!=null">
                    {{ dayjs(record.startTime).format("YYYY-MM-DD") }}至{{ dayjs(record.endTime).format("YYYY-MM-DD") }}
                  </p>
                </ion-label>
              </ion-item>
            </div>
          </div>
        </div>
        <div v-show="curTabIndex==3.5">
          <div class="form-detail">
            <ion-label>工作单位</ion-label>
            <ion-text>{{ curUserExperienceInfo.workAddress }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>职务</ion-label>
            <ion-text>{{ curUserExperienceInfo.duties }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>工作内容</ion-label>
            <ion-text style="width: 54%;text-align:right;">{{ curUserExperienceInfo.workOffice }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>起止日期</ion-label>
            <ion-text v-if="curUserExperienceInfo.startTime!=null&&curUserExperienceInfo.endTime!=null">
              {{ dayjs(curUserExperienceInfo.startTime).format("YYYY-MM-DD") }}至{{ dayjs(curUserExperienceInfo.endTime).format("YYYY-MM-DD") }}
            </ion-text>
          </div>
        </div>
        <div v-show="curTabIndex==4">
          <br/>
          <div v-if="!loading" id="jobHuntList" >
            <div v-for="(record,key) in curUserJobHuntList" :key="key" >
              <ion-item detail @click="onJobHuntEdit(record.jobHuntID)" class="ion-item">
                <ion-label>
                  <h2>
                    {{ record.professionName }}
                  </h2>
                  <p v-if="record.minSalary!=null&&record.maxSalary!=null">
                   期望薪资: {{  record.minSalary +"-"+ record.maxSalary }}
                  </p>
                </ion-label>
              </ion-item>
            </div>
          </div>
        </div>
        <div v-show="curTabIndex==4.5">
          <div class="form-detail">
            <ion-label>岗位名称</ion-label>
            <ion-text>{{ curUserJobHuntInfo.professionName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>求职类型</ion-label>
            <ion-text>{{ curUserJobHuntInfo.jobHuntTypeStr }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>人才类型</ion-label>
            <ion-text>{{ curUserJobHuntInfo.jobUserTypeStr }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>月薪要求(元)</ion-label>
            <ion-text v-if="curUserJobHuntInfo.minSalary!=null&&curUserJobHuntInfo.maxSalary!=null">
              {{ curUserJobHuntInfo.minSalary+"-"+curUserJobHuntInfo.maxSalary }}
            </ion-text>
          </div>
          <div class="form-detail">
            <ion-label>工作年限</ion-label>
            <ion-text >
              {{ curUserJobHuntInfo.workYear }}
            </ion-text>
          </div>
          <div class="form-detail">
            <ion-label>期望工作地区</ion-label>
            <ion-text>
              {{ curUserJobHuntInfo.areaWork}}
            </ion-text>
          </div>
          <div class="form-detail">
            <ion-label>可到职日期</ion-label>
            <ion-text v-if="curUserJobHuntInfo.inDate!=null">
              {{ dayjs(curUserJobHuntInfo.inDate).format("YYYY-MM-DD") }}
            </ion-text>
          </div>
          <div class="form-detail">
            <ion-label>其他信息</ion-label>
            <ion-text>
              {{ curUserJobHuntInfo.otherDemand }}
            </ion-text>
          </div>
        </div>
        <div v-show="curTabIndex==5">
          <br/>
          <div v-if="!loading" id="honorList">
            <div v-for="(record,key) in curUserHonorList" :key="key">
              <ion-item detail @click="onHonorEdit(record.honorID)" class="ion-item">
                <ion-label>
                  <h2>{{ record.honorName }}</h2>
                  <p>获得时间：{{ dayjs(record.getTime).format("YYYY-MM-DD") }}</p>
                </ion-label>
              </ion-item>
            </div>
          </div>
        </div>
        <div v-show="curTabIndex==5.5">
          <div class="form-detail">
            <ion-label>荣誉名称</ion-label>
            <ion-text>{{ curUserHonorInfo.honorName }}</ion-text>
          </div>
          <div class="form-detail">
            <ion-label>获得时间</ion-label>
            <ion-text>{{ dayjs(curUserHonorInfo.getTime).format("YYYY-MM-DD") }}</ion-text>
          </div>
        </div>
      </div>
      <ion-infinite-scroll  threshold="100px" @ionInfinite="onScroll($event)">
        <ion-infinite-scroll-content
            :loadingText="pageParams.total>pageParams.pageIndex*pageParams.pageSize?'正在加载...':'暂无更多'"
            loadingSpinner="bubbles">
        </ion-infinite-scroll-content>
      </ion-infinite-scroll>
    </ion-content>
    <ion-footer>
    </ion-footer>
  </ion-page>
</template>
<script lang="ts">
import {defineComponent, ref, reactive, toRefs} from "vue";
import {getJobUserById} from "@/api/jobUserInfo";
import {getEducationListByJobUserID} from "@/api/education";
import {getExperienceListByJobUserID} from "@/api/experience";
import {getJobHuntListByJobUserID} from "@/api/jobHuntInfo";
import {getSysDictionaryList} from "@/api/system/dictionary";
import {useRoute, useRouter} from "vue-router";
import {onIonViewDidEnter} from "@ionic/vue";
import {arrowBackOutline} from 'ionicons/icons';
import dayjs from "dayjs";
import crtyptoHelp from "@/utils/crypto";
import {getHonorListByJobUserID} from "@/api/honor/honor";

interface FormData{
  dataModel:any
}
export default defineComponent({
  name: 'UserDetail',
  setup() {
    const router = useRouter();
    const route = useRoute();
    const pageParams = reactive({
      pageIndex: 1,
      pageSize: 10,
      total:0,
      educationTotal:0,
      experienceTotal:0,
      jobHuntTotal:0,
      jobUserID:''
    });
    const curTabIndex = ref(1);
    const curTitle = ref("求职人员详情");
    const loading = ref<boolean>(false);
    const formData = reactive<FormData>({
      dataModel: {
      }
    });

    const dicHighestDegree = ref([]);
    const curUserEducationList = ref<any>([]);
    const curUserEducationInfo = ref<any>({});
    const curUserExperienceList = ref<any>([]);
    const curUserExperienceInfo = ref<any>({});
    const curUserJobHuntList = ref<any>([]);
    const curUserJobHuntInfo = ref<any>({});
    const curUserHonorList = ref([]);
    const curUserHonorInfo = ref({});


    const onBack=()=>{
      router.push({path:'./list',query:{reload:1}});
    };

    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (pageParams.total > pageParams.pageIndex * pageParams.pageSize) {
          pageParams.pageSize += 10;
          loadData(pageParams.jobUserID,route.query.loginUserID);
        }
      }, 500);
    }


    const loadData = async (jobUserID:any,loginUserId:any)=>{
      loading.value = true;
      curTabIndex.value = 1;
      curTitle.value ="求职人员详情";
      pageParams.jobUserID  = jobUserID;
      const reqData = await getJobUserById(jobUserID,loginUserId,true);
      formData.dataModel = reqData;
      formData.dataModel.tagName = "";
      if(formData.dataModel.listLabel != null && formData.dataModel.listLabel.length>0){
        (formData.dataModel.listLabel as any[]).map(it=>{
          formData.dataModel.tagName += it.labelName+" ";
        })
      }


      // const identityNumber = crtyptoHelp.decryptDesText(formData.dataModel.identityNumber);
      // const replaceStr =  identityNumber.substring(3,15);
      // formData.dataModel.identityNumberShow = String(identityNumber).replace(replaceStr,'************');
      formData.dataModel.userMobile = crtyptoHelp.decryptDesText(formData.dataModel.userMobile);
      console.log("dataModel",formData.dataModel);

      dicHighestDegree.value = await getSysDictionaryList("HighestDegree");

      const educationResult = await getEducationListByJobUserID(pageParams);
      pageParams.educationTotal = educationResult.total;
      curUserEducationList.value = educationResult.list;
      //console.log("educationList",curUserEducationList.value );

      const experienceResult = await getExperienceListByJobUserID(pageParams);
      pageParams.experienceTotal = experienceResult.total;
      curUserExperienceList.value = experienceResult.list;
      //console.log("experienceList",curUserExperienceList.value);

      const jobHuntResult = await getJobHuntListByJobUserID(pageParams);
      pageParams.jobHuntTotal = jobHuntResult.toatal;
      curUserJobHuntList.value = jobHuntResult.list;
      //console.log("jobHuntList",curUserJobHuntList.value);

      await getHonorListByJobUserID(pageParams.jobUserID).then((result: any) => {
        curUserHonorList.value = result;
      })

      loading.value = false;
    };

    const onTabChange = (tabIndex:number)=>{
      if(tabIndex!=curTabIndex.value){
        pageParams.pageSize = 10;
      }
      curTabIndex.value = tabIndex;
      switch (tabIndex){
        default:
        case 1:
          curTitle.value ="求职人员详情";
          break;
        case 2:
          curTitle.value ="教育经历详情";
          pageParams.total = pageParams.educationTotal;
          break;
        case 3:
          curTitle.value ="工作经验详情";
          pageParams.total = pageParams.experienceTotal;
          break;
        case 4:
          curTitle.value ="求职意向详情";
          pageParams.total = pageParams.jobHuntTotal;
          break;
      }
    }

    const onEducationEdit = (curEducationID:any)=>{
      curTabIndex.value = 2.5;
      //console.log("dicHighestDegree",dicHighestDegree.value);
      (curUserEducationList.value as any[]).map(item=>{
        if(item.educationID == curEducationID){
          curUserEducationInfo.value = item;
          (dicHighestDegree.value as any[]).map(curItem=>{
            if(curItem.value == item.cultureRank){
              curUserEducationInfo.value.cultureRankName = curItem.name;
            }
          })
          //console.log("curUserEducationInfo",curUserEducationInfo.value);
        }
      })
    }

    const onExperienceEdit = (curExperienceID:any)=>{
      curTabIndex.value = 3.5;
      (curUserExperienceList.value as any[]).map(item=>{
        if(item.experienceID == curExperienceID){
          curUserExperienceInfo.value = item;
          //console.log("curUserExperienceInfo",curUserExperienceInfo.value);
        }
      })
    }

    const onJobHuntEdit = (curJobHuntID:any)=>{
      curTabIndex.value = 4.5;
      (curUserJobHuntList.value as any[]).map(item=>{
        if(item.jobHuntID == curJobHuntID){
          curUserJobHuntInfo.value = item;
          //console.log("curUserJobHuntInfo",curUserJobHuntInfo.value);
        }
      })
    }

    function onHonorEdit(honorID: any) {
      curTabIndex.value = 5.5;
      (curUserHonorList.value as any[]).map(item => {
        if (item.honorID == honorID) {
          curUserHonorInfo.value = item;
        }
      })
    }

    const reload = (jobUserID:any,loginUserID:any) => {
      loadData(jobUserID,loginUserID);
    };

    onIonViewDidEnter(() => {
      if (route.query.reload) {
        reload(route.query.id,route.query.loginUserID)
      }
    });


    return {
      ...toRefs(formData),
      arrowBackOutline,
      route,
      router,
      curTabIndex,
      curTitle,
      pageParams,
      curUserEducationList,
      curUserEducationInfo,
      curUserExperienceList,
      curUserExperienceInfo,
      curUserJobHuntList,
      curUserJobHuntInfo,
      curUserHonorList,
      curUserHonorInfo,
      loading,
      onScroll,
      onTabChange,
      onEducationEdit,
      onExperienceEdit,
      onJobHuntEdit,
      onHonorEdit,
      onBack,
      loadData,
      dayjs,
    }
  }
});
</script>

<style lang="less">

.active{
  position: relative;
  top:6px;
  border-bottom: 4px solid #1a65eb;
  color:#1a65eb;
  padding-bottom: 2px;
}
.passive{
  position: relative;
  top:6px;
  color:black;
  padding-bottom: 2px;
}
.tab-item{
  height: 35px;
  width: 100%;
  float:left;
  text-align: center;
  border:none;
}

#educationList,#experienceList,#jobHuntList {
  margin: 40px 10px 10px 10px;
  background-color: white !important;

  .ion-item {
    margin-top: 10px;
    font-size: 14px;
    border-bottom: 1px solid rgb(242, 242, 245);

    p {
      font-size: 12px;
    }
  }
}

ion-item {
  --border-width: 0;
  --border-style: none;

  ion-label, ion-input, ion-select, ion-datetime-button {
    font-size: 14px !important;
  }
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


</style>