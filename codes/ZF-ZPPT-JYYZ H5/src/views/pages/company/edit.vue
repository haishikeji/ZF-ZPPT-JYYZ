<template>
  <ion-page>
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>
          企业信息录入
        </ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="stepFlex">
        <div v-for="(record,key) in stepList" :key="key" class="stepFlex-item">
          <div
              :class="[(record.val < curStepData?.statusVal || curStepData?.statusVal == stepList.val) ? 'greenCircle' :record.val == curStepData?.statusVal ? 'now' : 'grayCircle']"></div>
          <div v-if="key !== stepList.length - 1"
               :class="[record.val < curStepData?.statusVal ? 'greenLine' : 'grayLine']"></div>
          <div class="stepFlex-item-label">
            <p class="stepFlex-item-label-title" @click="onRedirect(record.val)" >{{ record.title }}</p>
            <!--<p class="stepFlex-item-label-desc"  @click="onRedirect(record.val)" >{{ record.desc }}</p>-->
          </div>
        </div>
      </div>
      <form autocomplete="off">
        <div class="bw-vue-form">
          <div class="form-title">基本项目</div>
          <div class="form-input">
            <ion-label>企业名称<span class="danger">*</span></ion-label>
            <div>
              <ion-input placeholder="请输入企业名称" label-placement="stacked" :style="{float: 'left',width:isAdd?'78%':'100%'}"
                         @click="onOpenCompany" v-model="dataModel.companyName" class="custom" readonly >
              </ion-input>
              <ion-item v-if="isAdd" style="width:22%;float: right;padding:0px;margin: 0px;">
                <company-selection-like ref="refCompanySelectionLike" @resultInfo="onResultInfo"></company-selection-like>
              </ion-item>
            </div>
<!--            <ion-label v-if="isAdd" style="float:right;width:80px;color:#02A5F0FF;position: relative;bottom:34px;left:14px;"-->
<!--                       @click="onGetFirmByCompanyName">同步</ion-label>-->
<!--          <ion-note slot="error">企业名称不能为空</ion-note>-->
          </div>

          <div class="form-input">
            <ion-label>统一信用代码<span class="danger">*</span></ion-label>
            <ion-input placeholder="请输入统一信用代码或工商注册号" label-placement="stacked" :clear-input="true"
                       v-model="dataModel.companyCode" class="custom">
            </ion-input>
<!--            <ion-note slot="error">统一信用代码不能为空</ion-note>-->
          </div>

          <!--          <div class="form-select">-->
          <!--            <ion-label>所属驿站<span class="danger">*</span></ion-label>-->
          <!--            <ion-select disabled id="siteID" name="siteID"  cancel-text="取消"  v-model="dataModel.siteID"-->
          <!--                        interface="action-sheet" placeholder="请选择所属驿站"  style="width: 100%;text-align: left;">-->
          <!--              <ion-select-option v-for="(record,key) in siteList" :key="key"  v-model:value="record.siteID">-->
          <!--                {{ record.siteName }}-->
          <!--              </ion-select-option>-->
          <!--            </ion-select>-->
          <!--&lt;!&ndash;            <ion-note slot="error">服务驿站不能为空</ion-note>&ndash;&gt;-->
          <!--          </div>-->
          <ion-label style="padding-left: 10px;">所在街道/社区/镇村<span class="danger">*</span></ion-label>
          <div class="form-select">
            <ion-select disabled name="regionCode"  id="regionCode" okText="确定" cancelText="取消" v-model="dataModel.regionCode"
                        interface="action-sheet" placeholder="请选择市/县" style="width:50%;float:left;" @ionChange="changeCity()" >
            <ion-select-option v-for=" (it,key) in regionList" :key="key" :value="it.code">
              {{ it.name }}
            </ion-select-option>
            </ion-select>
            <ion-select interface="action-sheet" placeholder="请选择街道/社区/镇村" cancel-text="取消"
                        id="streetCode" v-model="dataModel.streetCode" style="width: 50%;float: left;">
              <ion-select-option v-for="(record,key) in streetList" :key="key"
                                 v-model:value="record.code">
                {{ record.name }}
              </ion-select-option>
            </ion-select>
          </div>
          <div style="width: 100%;overflow: hidden;"></div>
          <div class="form-input">
            <ion-label>办公地址<span class="danger">*</span></ion-label>
            <ion-textarea placeholder="请输入办公地址" label-placement="stacked" :rows="3" :clear-input="true"
                          v-model="dataModel.companyAddress" class="custom" style="border-bottom: 1px solid #fff2e8;">
            </ion-textarea>
<!--            <ion-note slot="error">办公地址不能为空</ion-note>-->
          </div>
          <div class="form-input">
            <ion-label>企业联系人<span class="danger">*</span></ion-label>
            <ion-input placeholder="请输入企业联系人" label-placement="stacked" :clear-input="true"
                       v-model="dataModel.userName" class="custom">
            </ion-input>
<!--            <ion-note slot="error">企业联系人不能为空</ion-note>-->
          </div>
          <div class="form-input">
            <ion-label>联系电话<span class="danger">*</span></ion-label>
            <ion-input placeholder="请输入联系电话" label-placement="stacked" :clear-input="true"
                       v-model="dataModel.userMobile" class="custom">
            </ion-input>
<!--            <ion-note slot="error">企业联系电话不能为空</ion-note>-->
          </div>
          <div class="form-select">
            <ion-label>企业状态<span class="danger">*</span></ion-label>
            <ion-select id="recordStatus" name="recordStatus" cancel-text="取消"  v-model="dataModel.recordStatus"
                        interface="action-sheet" placeholder="请选择企业状态" style="width: 100%;text-align: left;">
              <ion-select-option v-for="(record,key) in companyStatusList" :key="key"
                                 v-model:value="record.value">
                {{ record.name }}
              </ion-select-option>
            </ion-select>
<!--            <ion-note slot="error">企业状态不能为空</ion-note>-->
          </div>
          <div class="form-input">
            <ion-label>用工情况(人，含临时人员)<span class="danger">*</span></ion-label>
            <ion-input type="number" placeholder="请输入用工人数" label-placement="stacked" :clear-input="true"
                       v-model="dataModel.workSituation" class="custom" @ionBlur="workSituationBlur">
            </ion-input>
          </div>
          <div class="form-title">
            扩展项目
            <div style="float:right;">
              <ion-label style="color: red;font-size: 14px;" @click="isShow=!isShow">{{isShow?"收起":"展开"}}</ion-label>
            </div>
          </div>

          <div v-show="isShow" >
            <div class="form-select">
              <ion-label>是否缺工</ion-label>
              <ion-select id="isShortAge" name="isShortAge" cancel-text="取消" v-model="dataModel.isShortage"
                          interface="action-sheet" placeholder="请选择是否缺工" style="width: 100%;text-align: left;">
                <ion-select-option v-for="(record,key) in shortAgeTypeList" :key="key"
                                   v-model:value="record.value">
                  {{ record.name }}
                </ion-select-option>
              </ion-select>
            </div>
            <div class="form-input">
              <ion-label>法定代表人(负责人)</ion-label>
              <ion-input placeholder="请输入法定代表人" label-placement="stacked" :clear-input="true"
                         v-model="dataModel.frName" class="custom">
              </ion-input>
            </div>
            <div class="form-input">
              <ion-label>营业执照有效期</ion-label>
              <div>
                <ion-radio v-model:checked="isLongDate" justify="start" labelPlacement="end"
                           @click="changeLongDate" style="height:30px;">至长期</ion-radio>
                <ion-datetime-button datetime="validDate" style="position:relative;right:105px;"></ion-datetime-button>
                <ion-modal :keep-contents-mounted="true" >
                  <ion-datetime id="validDate" name="validDate"
                                v-model="dataModel.validDate"  :prefer-wheel="true"  @ionChange="changeValidDate"
                                dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                                :show-default-buttons="true" >
                  </ion-datetime>
                </ion-modal>
              </div>
            </div>

            <div class="form-input">
              <ion-label>成立日期</ion-label>
                <ion-datetime-button datetime="establishmentTime" style="position:relative;right:110px;" ></ion-datetime-button>
                <ion-modal :keep-contents-mounted="true" >
                  <ion-datetime id="establishmentTime" name="establishmentTime"
                                v-model="dataModel.establishmentTime"  :prefer-wheel="true"
                                dataformatas="YYYY-MM-DD" presentation="date" cancel-text="取消" done-text="确定"
                                :show-default-buttons="true" >
                  </ion-datetime>
                </ion-modal>
            </div>
            <div class="form-input" >
              <ion-label>注册资本</ion-label>
              <div>
                <ion-input placeholder="请输入注册资本" label-placement="stacked" :clear-input="true"
                           v-model="dataModel.registeredCapital" class="custom" style="width:60%;float:left;">
                </ion-input>
                <ion-select id="registeredCapitalType" name="registeredCapitalType" cancel-text="取消"  v-model="dataModel.registeredCapitalType"
                            interface="action-sheet" style="width: 40%;text-align: left;">
                  <ion-select-option v-for="(record,key) in registeredCapitalTypeList" :key="key"
                                     v-model:value="record.value">
                    {{ record.name }}
                  </ion-select-option>
                </ion-select>
              </div>
            </div>
            <div class="form-select">
              <ion-label>企业注册地址行政区划</ion-label>
              <ion-select interface="action-sheet" placeholder="请选择" cancel-text="取消"
                          id="signInPoliticalArea" v-model="dataModel.signInPoliticalArea"
                          style="width: 100%;text-align: left;">
                <ion-select-option v-for="(record,key) in regionList" :key="key"
                                   v-model:value="record.code">
                  {{ record.name }}
                </ion-select-option>
              </ion-select>
            </div>
            <div class="form-input" >
              <ion-label>企业邮箱</ion-label>
              <ion-input placeholder="请输入企业邮箱" label-placement="stacked" :clear-input="true"
                         v-model="dataModel.companyEmail" class="custom">
              </ion-input>
            </div>
            <div class="form-select">
            <ion-label>经济类型</ion-label>
              <ion-select interface="action-sheet" placeholder="请选择经济类型" cancel-text="取消"
                          id="companyType" v-model="dataModel.companyType"  style="width: 100%;text-align: left;">
                <ion-select-option v-for="(record,key) in companyTypeList" :key="key"
                                   v-model:value="record.value">
                  {{ record.name }}
                </ion-select-option>
              </ion-select>
            </div>
            <div class="form-input">
              <ion-label>所属行业</ion-label>
              <div>
                <ion-input placeholder="请选择所属行业" label-placement="stacked" style="float: left;width:78%;"
                           v-model="dataModel.industryName" class="custom" readonly >
                </ion-input>
                <ion-item style="width:22%;float: right;padding:0px;margin: 0px;">
                  <industry-selection  :IndustryId="dataModel.industryID" @SetIndustryID="onSetIndustryID"></industry-selection>
                </ion-item>
              </div>

<!--              <ion-select interface="action-sheet" placeholder="请选择所属行业" cancel-text="取消"-->
<!--                          id="professionID" v-model="dataModel.professionID"  style="width: 100%;text-align: left;">-->
<!--                <ion-select-option v-for="(record,key) in professionList" :key="key"-->
<!--                                   v-model:value="record.value">-->
<!--                  {{ record.name }}-->
<!--                </ion-select-option>-->
<!--              </ion-select>-->
            </div>
            <div class="form-select">
              <ion-label>所属产业分类</ion-label>
              <ion-select interface="action-sheet" placeholder="请选择所属产业分类" cancel-text="取消"
                          id="estateCategoryID" v-model="dataModel.estateCategoryID"  style="width: 100%;text-align: left;">
                <ion-select-option v-for="(record,key) in estateCategoryList" :key="key"
                                   v-model:value="record.value">
                  {{ record.name }}
                </ion-select-option>
              </ion-select>
            </div>
<!--            <div class="form-select">-->
<!--              <ion-label>企业标签</ion-label>-->
<!--              <ion-select interface="action-sheet" placeholder="请选择企业标签" cancel-text="取消"-->
<!--                          id="tagID" v-model="dataModel.tagID"  style="width: 100%;text-align: left;">-->
<!--                <ion-select-option v-for="(record,key) in companyTagList" :key="key"-->
<!--                                   v-model:value="record.value" style="width:100%;text-align: left;">-->
<!--                  {{ record.name }}-->
<!--                </ion-select-option>-->
<!--              </ion-select>-->
<!--            </div>-->
            <div class="form-input">
              <ion-label>企业网站</ion-label>
              <ion-input placeholder="请输入企业网站" label-placement="stacked" :clear-input="true"
                         v-model="dataModel.website" class="custom">
              </ion-input>
            </div>
            <div class="form-input">
              <ion-label>企业福利</ion-label>
              <ion-textarea placeholder="请输入企业福利" :rows="3" label-placement="stacked" :clear-input="true"
                            v-model="dataModel.bonus" class="custom" style="border-bottom: 1px solid #fff2e8;">
              </ion-textarea>
            </div>
            <div class="form-select">
            <ion-label>企业规模</ion-label>
              <ion-select interface="action-sheet" placeholder="请选择企业规模" cancel-text="取消"
                          id="companyModel" v-model="dataModel.companyModel"  style="width: 100%;text-align: left;">
                <ion-select-option v-for="(record,key) in companyModelList" :key="key"
                                   v-model:value="record.value" style="width:100%;text-align: left;">
                  {{ record.name }}
                </ion-select-option>
              </ion-select>
            </div>
            <div class="form-input">
            <ion-label>参保人数(人)</ion-label>
              <ion-input type="number" placeholder="请输入参保人数" label-placement="stacked" :clear-input="true"
                         v-model="dataModel.insuredCount" class="custom" @ionBlur="insuredCountBlur">
              </ion-input>
            </div>
            <div class="form-input">
            <ion-label>经营范围</ion-label>
              <ion-textarea placeholder="请输入经营范围" :rows="3" label-placement="stacked" :clear-input="true"
                            v-model="dataModel.businScope" class="custom" style="border-bottom: 1px solid #fff2e8;">
              </ion-textarea>
            </div>
            <div class="form-input">
            <ion-label>企业简介</ion-label>
              <ion-textarea placeholder="请输入企业简介" :rows="3" label-placement="stacked" :clear-input="true"
                            v-model="dataModel.companyDesc" class="custom" style="border-bottom: 1px solid #fff2e8;">
              </ion-textarea>
            </div>
            <div class="form-input">
              <ion-label>标签</ion-label>
              <ion-row>
                <ion-col size="auto" v-for="(itemLabel,keyLabel) in dataModel.listLabel" :key="keyLabel">
                  <ion-button color="primary" size="small">{{ itemLabel.labelName }}</ion-button>
                </ion-col>
                <ion-col>
                  <ion-item style="float: right;padding:0px;margin: 0px;">
                    <label-selection :key="'refLabelSelectionKey'" :ref="refLabelSelection" :big-type="1" :list-label="dataModel.listLabel"
                                     :title="'企业标签'" v-bind="$attrs" @resultBack="onResultBackLabels"></label-selection>
<!--                    <component :is="refLabelSelection" :big-type="1" :list-label="dataModel.listLabel" :title="'企业标签'"-->
<!--                               @resultBack="onResultBackLabels"></component>-->
                  </ion-item>
                </ion-col>
              </ion-row>
            </div>
          </div>
        </div>
      </form>
    </ion-content>
    <ion-footer>
      <ion-toolbar>
          <ion-button style="width:100%;" @click="onNext">保存并下一步</ion-button>
      </ion-toolbar>
    </ion-footer>
    <ion-alert
        :is-open="alertingInfo"
        :header="infoAlterData.title"
        :message="infoAlterData.message"
        :buttons="infoAlertButtons"
        @didDismiss="setInfoAlertOpen(false)"
    ></ion-alert>
    <ion-loading
        :is-open="loadingFirm"
        message="正在同步企业信息..."
        @didDismiss="setInfoLoadingOpen(false)" >
    </ion-loading>
  </ion-page>
</template>
<script lang="ts">
import {arrowBackOutline} from 'ionicons/icons';
import {defineComponent, ref, reactive, computed, toRefs} from "vue";
import {useRoute,useRouter} from "vue-router";
import {alertController, onIonViewDidEnter} from "@ionic/vue";
import {useVuelidate} from "@vuelidate/core";
import {
  getCompanyById,
  getRegionList,
  getSiteList,
  getStreeList,
  normalFirm,
  saveCompany
} from "@/api/company";
import {required} from "@vuelidate/validators";
import {getSysDictionaryList} from "@/api/system/dictionary";
import dayjs from "dayjs";
import IndustrySelection from "@/components/industrySelection.vue";
import {getPosition} from "@/utils/position";
import CompanySelectionLike from "@/components/companySelectionLike.vue";
import LabelSelection from "@/components/labelSelection.vue";

interface StepParams{
  name: string,
  statusVal: number,
  loginUserID: string
}
export default defineComponent({
  // name: 'CompanyEdit',
  components:{IndustrySelection,CompanySelectionLike, LabelSelection},
  setup() {
    const route = useRoute();
    const router = useRouter();
    const isAllowCommit = ref(true);
    const curStepData = ref<StepParams>({
      name:"",
      statusVal: 1,
      loginUserID:""
    });
    const isAdd = ref(false);
    const isShow = ref<any>(false);
    const loadingFirm = ref(false);
    // 信息弹窗内容
    const infoAlterData = reactive({
      title:"",
      message:""
    });
    const alertingInfo = ref(false);
    function setInfoAlertOpen(value: boolean) {
      alertingInfo.value = value;
    }
    function setInfoLoadingOpen(value: boolean) {
      loadingFirm.value = value;
    }
    const infoAlertButtons = [
      {
        text: '确定',
        role: 'confirm',
        handler: () => {
          setInfoAlertOpen(false);
        },
      },
    ];
    const isLongDate = ref<any>(false);
    const formState = reactive({
      dataModel: {
        companyID:null,
        companyCode: '',
        companyName: null,
        siteID: null,
        companyAddress: null,
        userName: null,
        userMobile: '',
        recordStatus: null,
        isShortage: null,
        frName: null,
        validDate: dayjs().format("YYYY-MM-DD"),
        establishmentTime: dayjs().format("YYYY-MM-DD"),
        registeredCapital:null,
        signInPoliticalArea:null,
        companyEmail: '',
        companyType: null,
        industryID:null,
        industryName:null,
        estateCategoryID: "",
        tagID:null,
        website:null,
        companyModel: 0,
        regionCode: null,
        streetCode: null,
        workSituation: null,
        insuredCount: null,
        businScope: null,
        companyDesc: null,
        longitude: null,
        latitude: null,
        listLabel: null,
        loginUserID: ""
    }});
    const stepList = ref([
      {title: '基础信息', desc: '企业基础信息', val: 1},
      {title: '岗位信息', desc: '企业岗位信息', val: 3},
      {title: '信用记录', desc: '企业信用记录', val: 4}
    ]);

    const companyStatusList = ref([]);
    const companyModelList = ref<any>([]);
    const companyTypeList = ref<any>([]);
    const companyTagList = ref<any>([]);
    const estateCategoryList = ref<any>([]);
    const regionList = ref<any>([]);
    const streetList = ref<any>([]);
    const siteList = ref<any>([]);
    const shortAgeTypeList = ref<any>([]);
    const registeredCapitalTypeList = ref<any>([]);
    const refCompanySelectionLike = ref();
    const refLabelSelection = ref();

    // /** 选择的区域 */
    // const regionChecked= ref<string>('');
    // // /** 选择的街道 */
    // const streetChecked= ref<string>('');
    //
    // // /** 编辑传入的地址 */
    // const defaultAddText= ref<string>('');
    //
    // const allData = ref<any>({
    //   province: [],
    //   city: [],
    //   region: [{name:"",code:""}],
    //   street: [{name:"",code:""}]
    // });
    //
    // //获取选定的省市区
    // const getCityArea = () =>{
    //   // 城市选中后默认为字符串，并且获取到的是编码不是直接内容，为了把省份 城市 区域分为三个字段，所以把字符串分割为数组，在每个单独赋值
    //   let cities = cityDefault.value.split(' ');
    //   allData.value.province = provinceColumns.value;
    //   allData.value.city = cityColumns.value;
    //   allData.value.region = regionColumns.value[0].options;
    //   allData.value.street = streetColumns.value[0].options;
    //
    //   /*  循环城市数组，通过获取到的省市区编码来查询对应的文字内容 **/
    //   allData.value.region.map(v=>{
    //     if(v.code == cities[0]){
    //       regionChecked.value = v.name; /* 县区 */
    //     }
    //   })
    //   allData.value.street.map(v => {
    //     if (v.code == cities[1]) {
    //       streetChecked.value = v.name;  /* 街道 */
    //     }
    //   })
    // }

    const getCompanyModelList = async function(){
      const companyModelResult :any = await getSysDictionaryList("CompanyModel");
      companyModelList.value = companyModelResult;
    }

    const getCompanyTagList = async function(){
      const companyTagResult :any = await getSysDictionaryList("CompanyTag");
      companyTagList.value = companyTagResult;
    }

    const getCompanyTypeList = async function(){
      const companyTypeResult :any = await getSysDictionaryList("CompanyType");
      companyTypeList.value = companyTypeResult;
    }

    const getCompanyStatusList = async function(){
      const companyStatusResult :any = await getSysDictionaryList("CompanyStatus");
      companyStatusList.value = companyStatusResult;
    }

    const getEstateCategoryList = async function(){
      const estateCategoryResult :any = await getSysDictionaryList("CompanyEstateCategory");
      estateCategoryList.value = estateCategoryResult;
    }

    const getShortAgeTypeList = async function(){
      const shortAgeTypeResult :any = await getSysDictionaryList("IsShortAge");
      shortAgeTypeList.value = shortAgeTypeResult;
    }

    const getRegisteredCapitalTypeList = async function(){
      const registeredCapitalTypeResult :any = await getSysDictionaryList("registeredCapitalType");
      registeredCapitalTypeList.value = registeredCapitalTypeResult;
    }


    function isStringInteger(value: string) {
      const n = parseInt(value, 10);
      return n.toString() === value && Number.isInteger(n);
    }

    function workSituationBlur() {
      inputValid.value = true;
      if (formState.dataModel.workSituation != null) {
        if(!isStringInteger(formState.dataModel.workSituation)||formState.dataModel.workSituation<1){
          presentAlert("用工情况必须为一个正整数！");
          inputValid.value = false;
          return;
        }
        // 匹配企业规模
        const workSituationRanges = [
          {max: 19, model: 1},
          {min: 20, max: 99, model: 2},
          {min: 100, max: 299, model: 3},
          {min: 300, max: 499, model: 4},
          {min: 500, max: 999, model: 5},
          {min: 1000, max: 9999, model: 6},
          {min: 10000, model: 7}
        ];
        const workSituation = formState.dataModel.workSituation;
        formState.dataModel.companyModel = 1;  // 默认值，如果所有范围都不匹配
        for (const range of workSituationRanges) {
          if (
              (range.min === undefined || workSituation >= range.min) &&
              (range.max === undefined || workSituation <= range.max)
          ) {
            formState.dataModel.companyModel = range.model;
            break;
          }
        }
      }
    }

    function insuredCountBlur() {
      inputValid.value = true;
      if (formState.dataModel.insuredCount != null) {
        if(!isStringInteger(formState.dataModel.insuredCount)||formState.dataModel.insuredCount<1){
          presentAlert("参保人数必须为一个正整数！");
          inputValid.value = false;
        }
      }
    }

    const rules = computed(() => {
      return {
        dataModel: {
          companyCode: {required},
          companyName: {required},
          // siteID: {required},
          companyAddress: {required},
          userName: {required},
          userMobile: {required},
          recordStatus: {required},
          workSituation: {required},
        }
      }
    });
    const v$ = useVuelidate(rules, formState);

    const changeLongDate = ()=>{
      isLongDate.value = true;
      formState.dataModel.validDate = "2099-12-31T00:00:00.000+08:00";
    };

    const changeValidDate = () => {
        isLongDate.value = false;
    };

    const companyCodeValidate = ()=>{
      isAllowCommit.value = true;
      const codeReg = /^[0-9A-Z]+$/;
      const gsCodeReg = /^\d{15}$/;
      if (formState.dataModel.companyCode.length != 18 && formState.dataModel.companyCode.length != 15) {
        presentAlert('输入的统一信用代码无效！');
        isAllowCommit.value = false;
        return;
      }
      if (formState.dataModel.companyCode.length == 15) {
        if (!gsCodeReg.test(formState.dataModel.companyCode)) {
          presentAlert('输入的工商注册号有误！');
          isAllowCommit.value = false;
        }
      }
      if (formState.dataModel.companyCode.length == 18) {
        if (!codeReg.test(formState.dataModel.companyCode)) {
          presentAlert('输入的统一信用代码无效！');
          isAllowCommit.value = false;
        } else {
          let aCode;
          let aCodeValue;
          let total = 0;
          const weightedFactors = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28];
          const str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
          for (let i = 0; i < formState.dataModel.companyCode.length - 1; i++) {
            aCode = formState.dataModel.companyCode.substring(i, i + 1);
            aCodeValue = str.indexOf(aCode);
            total += aCodeValue * weightedFactors[i];
          }
          let logicCheckCode = 31 - total % 31;
          if (logicCheckCode == 31) {
            logicCheckCode = 0;
          }
          const Str = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
          const Array_Str = Str.split(',');
          const correctCodeStr = Array_Str[logicCheckCode];
          console.log("正确的校验码是", correctCodeStr);
          const currentCodeStr = formState.dataModel.companyCode.substring(17, 18);
          if (correctCodeStr != currentCodeStr) {
            presentAlert('输入的统一信用代码无效！');
            isAllowCommit.value = false;
          }
        }
      }
    }
    const inputDataValidate = () =>{
      const mobileReg = /^1[3|4|5|6|7|8|9]\d{9}$/;
      // const landlineReg = /[0-9-()（）]{7,18}/;
      const landlineReg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
      if(!mobileReg.test(formState.dataModel.userMobile)&&!landlineReg.test(formState.dataModel.userMobile)){
        presentAlert("输入的联系电话有误!");
        isAllowCommit.value = false;
      }
      const emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
      // 这里由于企业邮箱非必填所以先判断是否填写了企业邮箱
      if(formState.dataModel.companyEmail!=null&&formState.dataModel.companyEmail!=""){
        if(!emailReg.test(formState.dataModel.companyEmail)){
          presentAlert("输入的企业邮箱有误!");
          isAllowCommit.value = false;
        }
      }
    };

    const onGetFirmByCompanyName = async function() {
      if(formState.dataModel.companyName){
        await setInfoLoadingOpen(true);
        let matchedFirmInfo :any = await normalFirm(formState.dataModel.companyName);
        if(matchedFirmInfo){
          await setInfoLoadingOpen(false);
          infoAlterData.title = "提示";
          infoAlterData.message = "同步成功！";
          await setInfoAlertOpen(true);
        }else{
          matchedFirmInfo = {};
          await setInfoLoadingOpen(false);
          infoAlterData.title = "错误";
          infoAlterData.message = "查找不到该企业，请核实企业名称是否正确！";
          await setInfoAlertOpen(true);
        }

        formState.dataModel.frName = matchedFirmInfo.frName;
        formState.dataModel.registeredCapital = matchedFirmInfo.registeredCapital;
        formState.dataModel.companyCode = matchedFirmInfo.companyCode;
        formState.dataModel.insuredCount = matchedFirmInfo.insuredCount;
        formState.dataModel.userMobile = matchedFirmInfo.userMobile;
        formState.dataModel.companyEmail = matchedFirmInfo.companyEmail;
        formState.dataModel.companyAddress = matchedFirmInfo.companyAddress;
        formState.dataModel.businScope = matchedFirmInfo.businScope;
        formState.dataModel.industryID = matchedFirmInfo.industryID;
        formState.dataModel.industryName = matchedFirmInfo.industryName;
        setEstateCategoryID(matchedFirmInfo.industryName);
        formState.dataModel.signInPoliticalArea = matchedFirmInfo.signInPoliticalArea;
        formState.dataModel.establishmentTime = matchedFirmInfo.establishmentTime;
      }else{
        infoAlterData.title = "提示";
        infoAlterData.message = "请输入企业名称！";
        await setInfoAlertOpen(true);
      }
    }

    const onSetIndustryID = (data: any)=>{
      formState.dataModel.industryID = data.value;
      formState.dataModel.industryName = data.text;
      const text = data.text.split("-")[0] as string;
      setEstateCategoryID(text)
    }

    function setEstateCategoryID(industryName: any) {
      // 所属产业判断
      const categoryMap: { [key: string]: any } = {
        '农、林、牧、渔业': 1,
        '采矿业': 2,
        '制造业': 2,
        '电力、热力、燃气及水生产和供应业': 2,
        '建筑业': 2
      };
      if (industryName) {
        formState.dataModel.estateCategoryID = categoryMap[industryName] || 3;
      } else {
        formState.dataModel.estateCategoryID = "3";
      }
    }

    const inputValid = ref(true);
    const onNext = async () => {
      console.log(formState.dataModel)
      const isFormCorrect = await v$.value.$validate();
      if (!isFormCorrect) {
        await presentAlert('请输入完整信息！');
        return null;
      }
      companyCodeValidate();
      inputDataValidate();
      if(isAllowCommit.value&&inputValid.value){
        saveCompany(formState.dataModel).then(result => {
          if (result) {
              router.push({path: './postList', query: {reload:1,id:formState.dataModel.companyID,status:3,isEdit:isAdd.value!=true?1:0,random:Math.random()}});
          }
        });
      }
    };

    const onResultBackLabels = (data: any) => {
      formState.dataModel.listLabel = data;
      refLabelSelection.value = null;
    }

    const onResultInfo = (data: any)=>{
      formState.dataModel.companyName = data.text;
      onGetFirmByCompanyName();
    }

    const onOpenCompany = () => {
      if(!isAdd.value){
        return;
      }

      refCompanySelectionLike.value.onOpen();
    }

    const onBack = () => {
        router.push({path: './list', query: {reload:1}});
    }
    const onRedirect = (statusValue:any) => {
      if(!isAdd.value && statusValue==3) {
          router.push({path: './postList', query: {reload:1,id:formState.dataModel.companyID,status:3,isEdit:1,random:Math.random()}});
      }
    }

    const getRegionListData = () => {
      getRegionList({}).then(data => {
        regionList.value = data;
      });
    }
    const getStreetListData = (code: any) => {
      getStreeList({code: code}).then(data => {
        streetList.value = data;
      });
    }
    const getSiteListData = () => {
      getSiteList({}).then(data => {
        siteList.value = data;
      });
    }
    const changeCity = () => {
      if (formState.dataModel.regionCode)
        getStreetListData(formState.dataModel.regionCode);
    }


    const loadData = async (companyID: any,status:any,loginUserId:any) => {
      curStepData.value.statusVal = status;
      curStepData.value.loginUserID = loginUserId;
      isAdd.value = companyID == null;
      const reqData = await getCompanyById(companyID,loginUserId);
      formState.dataModel = reqData;
      formState.dataModel.loginUserID = loginUserId;
      isLongDate.value = formState.dataModel.validDate == "2099-12-31T00:00:00.000+08:00";
      if(formState.dataModel.regionCode!=null) getStreetListData(formState.dataModel.regionCode);
      if (companyID == null) {
        setLongitudeLatitude();
      }
    };

   /* watch(() => route.query, () => {
      if (route.query.reload) {
        loadData(route.query.id,route.query.status);
      }
    });*/

    const reload = (companyID:any,status:any,loginUserId:any) => {
      isShow.value = false;
      loadData(companyID,status,loginUserId);
    }

    onIonViewDidEnter(() => {
      if (route.query.reload) {
        reload(route.query.id,route.query.status,route.query.loginUserID);
      }
    });

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

    // 设置经纬度
    function setLongitudeLatitude() {
      getPosition().then((data: any) => {
        if (data.longitude != null && data.latitude != null) {
          formState.dataModel.longitude = data.longitude;
          formState.dataModel.latitude = data.latitude;
        }
      })
    }

    return {
      ...toRefs(formState),
      arrowBackOutline,
      route,
      router,
      isAdd,
      isShow,
      v$,
      refCompanySelectionLike,
      onOpenCompany,
      onResultInfo,
      refLabelSelection,
      onResultBackLabels,
      isLongDate,
      curStepData,
      stepList,
      companyTypeList,
      siteList,
      regionList,
      streetList,
      shortAgeTypeList,
      registeredCapitalTypeList,
      companyStatusList,
      companyModelList,
      companyTagList,
      estateCategoryList,
      infoAlertButtons,
      infoAlterData,
      alertingInfo,
      loadingFirm,
      workSituationBlur,
      insuredCountBlur,
      setInfoAlertOpen,
      setInfoLoadingOpen,
      onGetFirmByCompanyName,
      onSetIndustryID,
      onNext,
      onBack,
      onRedirect,
      getCompanyStatusList,
      getCompanyModelList,
      getCompanyTypeList,
      getCompanyTagList,
      getEstateCategoryList,
      getShortAgeTypeList,
      getRegisteredCapitalTypeList,
      getRegionListData,
      getSiteListData,
      changeCity,
      changeLongDate,
      changeValidDate,
      loadData,
    }
  },mounted(){
    this.getCompanyModelList();
    this.getCompanyTypeList();
    this.getCompanyTagList();
    this.getCompanyStatusList();
    this.getEstateCategoryList();
    this.getShortAgeTypeList();
    this.getRegisteredCapitalTypeList();
    this.getRegionListData();
    this.getSiteListData();
  }
});
</script>

<style lang="less">

ion-select#siteID.md.select-disabled.legacy-select::after{display: none;}
ion-select#regionCode.md.select-disabled.legacy-select::after{display: none;}

.custom{
  --placeholder-color: gray;
  --placeholder-opacity: 0.5;
}

.title-item{
  margin-left: 15px;
  color:#1c3d70 !important;
  font-size: 14px !important;
  font-weight: bold;
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
      padding-top: 30px;
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