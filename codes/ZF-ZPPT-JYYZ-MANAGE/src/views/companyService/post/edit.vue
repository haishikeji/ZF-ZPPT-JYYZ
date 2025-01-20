<template>
  <div class="card-edit">
    <a-form :model="dataModel" autocomplete="off" @finish="onFinish">
      <!-- 信用问题提示 -->
      <p v-if="creditRecordMessage" style="color: red">{{ creditRecordMessage }}</p>
      <a-divider orientation="left">岗位基础信息</a-divider>
      <a-row justify="start">
        <a-col flex="800px">
          <a-form-item label="企业名称" :label-col="{ span: 3}"
                       name="companyID" :rules="[{ required: true, message: '请选择关联企业!' }]">
            <a-auto-complete
              :disabled="isEdit"
              v-model:value="dataModel.companyName"
              :options="companyList"
              @search="selectCompanyList"
              @change="companyChange"
              :field-names="{
                            value:'companyName',
                          }"
            >
              <template #option="item">
                <span v-html="formatStr(item.companyName,false)"></span>
              </template>
            </a-auto-complete>
          </a-form-item>
        </a-col>
        <a-col flex="530px">
          <a-form-item label="岗位名称" :label-col="{ span: 5 }"
                       name="professionName" :rules="[{ required: true, message: '请选择岗位名称!' }]">
            <a-auto-complete
              v-model:value="dataModel.professionName"
              :options="professionList"
              placeholder="请选择岗位名称"
              @search="selectProfessionList"
              @change="professionChange"
              :field-names="{
                            value:'professionName',
                          }"
            >
              <template #option="item">
                <span v-html="formatStr(item.professionName, true)"></span>({{ item.parentProfessionName }})
              </template>
            </a-auto-complete>
          </a-form-item>
        </a-col>
      </a-row>

      <a-row justify="start">
        <a-col flex="530px">
          <a-form-item label="工种名称" :label-col="{ span: 5 }"
                       name="workCategoryName" :rules="[{ required: true, message: '请选择工种名称!' }]">
            <a-cascader :load-data="loadLevelWorkCategoryList" v-model:value="dataModel.workCategoryName"
                        :options="firstWorkCategoryList" change-on-select @change="workCategoryChange">
            </a-cascader>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item label="招聘人数" :label-col="{ span: 9}"
                       name="recruitCount" :rules="[{ required: true, message: '请输入招聘人数!' }]" >
            <a-input-number style="width: 100%" :controls="false" :min="1" v-model:value="dataModel.recruitCount"
                            placeholder="" @blur="recruitCountChange"/>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item label="招聘日期" :label-col="{ span: 7 }"
                       name="jobFindDate" :rules="[{ required: true, message: '请选择招聘日期' }]"  >
            <a-range-picker v-model:value="dataModel.jobFindDate" :placeholder="['开始日期', '结束日期']"
                            format="YYYY-MM-DD"
                            @change="onJobFindDateChange"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row justify="start">
        <a-col  flex="1340px">
          <a-form-item label="工作地点" :label-col="{span:2}" name="jobPlace"
                       :rules="[{ required: true, message: '请输入工作地点' }]" >
            <a-textarea v-model:value="dataModel.jobPlace" placeholder="" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row justify="start">
        <a-col flex="530px">
          <a-form-item
            label="联系人姓名"
            :label-col="{ span: 5 }"
            name="contactName"
          >
            <a-input v-model:value="dataModel.contactName" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item
            label="联系人电话"
            :label-col="{ span: 9 }"
            name="contactMobile"
          >
            <a-input v-model:value="dataModel.contactMobile" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item
            label="联系人邮箱"
            :label-col="{ span: 7}"
            name="contactEmail"
          >
            <a-input v-model:value="dataModel.contactEmail" placeholder=""/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row justify="start">
        <a-col flex="530px">
          <a-form-item
            label="工作性质"
            :label-col="{ span: 5 }"
            name="contactName"
          >
            <a-select ref="select" v-model:value="dataModel.workNatureID" :options="jobHuntList"
                      :field-names="{ label: 'name', value: 'value' }" :allow-clear="false"></a-select>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
        </a-col>
        <a-col flex="400px">
        </a-col>
      </a-row>
      <a-divider orientation="left">岗位标签</a-divider>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-button style="margin: 0px 5px 5px 0px;color: black;border: 1px solid rgb(217, 217, 217);" v-for="item in dataModel.listLabel">
            {{ item.labelName }}
          </a-button>
          <a-button @click="onSelectModel">
            <plus-circle-outlined/>选择标签
          </a-button>
        </a-col>
      </a-row>
      <a-divider orientation="left">其他信息</a-divider>
      <a-row justify="start">
        <a-col flex="300px" >
          <a-form-item
            label="岗位月薪(元)"
            name="minSalary"
            :label-col="{span:9}"
            :rules="[{ required: false, message: '请输入金额!' }]"
          >
            <a-input-number style="width: 100%" :controls="false" :min="1720" v-model:value="dataModel.minSalary"
                            placeholder="请输入金额" @blur="salaryChange"/>
          </a-form-item>
        </a-col>
        <label style="margin:5px 8px 5px 8px;">至</label>
        <a-col flex="200px">
          <a-form-item
            :label-col="{ span: 6 }"
            name="maxSalary"
            :rules="[{ required: false, message: '请输入金额' }]"
          >
            <a-input-number style="width: 100%" :controls="false" :min="dataModel.minSalary"
                            v-model:value="dataModel.maxSalary"
                            placeholder="请输入金额" @blur="salaryChange"/>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item
            label="是否有试用期"
            :label-col="{ span: 9 }"
            name="isTrail"
            :rules="[{ required: false, message: '请选择是否试用期!' }]"
          >
            <a-select
              ref="select"
              v-model:value="dataModel.isTrail"
              :options="trialStatusList"
              :field-names="{ label: 'name', value: 'value' }"
              @change="isTrailChange"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col flex="400px" >
          <a-form-item
            label="试用期(月)"
            :label-col="{ span: 7 }"
            name="trailMonths"
            :rules="[{ required: false, message: '请选择试用期时长!' }]"
          >
            <a-input-number :min="0" :disabled="!dataModel.isTrail" style="width: 100%" :controls="false"
                            v-model:value="dataModel.trailMonths" placeholder="" @change="trailMonthsChange"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row justify="start">
        <a-col flex="300px">
          <a-form-item
            label="试用期月薪(元)"
            name="trailMaxSalary"
            :label-col="{span:9}"
            :rules="[{ required: false, message: '请输入金额!' }]"
          >
            <a-input-number style="width: 100%" :controls="false" :min="1720" v-model:value="dataModel.trailMinSalary"
                            placeholder="请输入金额" @blur="trailSalaryChange"/>
          </a-form-item>
        </a-col>
        <label style="margin:5px 8px 5px 8px;">至</label>
        <a-col flex="200px">
          <a-form-item
            :label-col="{ span: 6 }"
            name="trailMinSalary"
            :rules="[{ required: false, message: '请输入金额' }]"
          >
            <a-input-number style="width: 100%" :controls="false" :min="dataModel.trailMinSalary"
                            v-model:value="dataModel.trailMaxSalary"
                            placeholder="请输入金额" @blur="trailSalaryChange"/>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item
            label="工作年限要求"
            :label-col="{ span: 9 }"
            name="workYear"
            :rules="[{ required: false, message: '请选择工作年限!' }]"
          >
            <a-select
              ref="select"
              v-model:value="dataModel.workYear"
              :options="workYearTypeList"
              :field-names="{ label: 'name', value: 'value' }"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col flex="400px">
          <a-form-item
            label="学历要求"
            :label-col="{ span: 7 }"
            name="cultureRank"
            :rules="[{ required: false, message: '请选择学历要求!' }]"
          >
            <a-select
              ref="select"
              v-model:value="dataModel.cultureRank"
              :options="cultureLevelList"
              :field-names="{ label: 'name', value: 'value' }"
            >
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
<!--      <a-row :gutter="24">-->
<!--        <a-col :span="24">-->
<!--          <a-form-item label="标签" :label-col="{ span: 2 }" name="listLabel" >-->
<!--            <a-button style="margin: 0px 5px 5px 0px;color: black;border: 1px solid rgb(217, 217, 217);" v-for="item in dataModel.listLabel">-->
<!--              {{ item.labelName }}-->
<!--            </a-button>-->
<!--            <a-button @click="onSelectModel">-->
<!--              <plus-circle-outlined/>选择标签-->
<!--            </a-button>-->
<!--          </a-form-item>-->
<!--        </a-col>-->
<!--      </a-row>-->
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="福利待遇"
            :label-col="{ span: 24 }"
            name="welfare"
          >
            <a-textarea :auto-size="{ minRows: 4, maxRows: 10 }"  v-model:value="dataModel.welfare"  placeholder="福利待遇" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="其他要求"
            :label-col="{ span: 24 }"
            name="postDesc"
          >
            <a-textarea :auto-size="{ minRows: 4, maxRows: 10 }"  v-model:value="dataModel.postDesc"  placeholder="其他要求" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
<!--      <a-row justify="start">-->
<!--        <a-col flex="1330px">-->
<!--          <a-form-item :label-col="{span:2}" name="welfare" label="福利待遇">-->
<!--            <a-textarea v-model:value="dataModel.welfare" placeholder="" :rows="4"/>-->
<!--          </a-form-item>-->
<!--        </a-col>-->
<!--      </a-row>-->
<!--      <a-row justify="start">-->
<!--        <a-col flex="1330px">-->
<!--          <a-form-item :label-col="{span:2}" name="postDesc" label="其他要求">-->
<!--            <a-textarea v-model:value="dataModel.postDesc" placeholder="" :rows="4"/>-->
<!--          </a-form-item>-->
<!--        </a-col>-->
<!--      </a-row>-->
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item class="buttom-btns">
            <a-button @click="onClose">取消</a-button>
            <a-button type="primary" html-type="submit">提交</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <select-label ref="refSelectModel" :list-label="dataModel.listLabel" :big-type="3" @selectLabelChecks="selectLabelChecks"></select-label>
  </div>
</template>

<script lang="ts">
import {defineComponent, reactive, ref, toRefs, watch} from 'vue';
import {useRouter} from 'vue-router';
import {getPostByID, savePost} from '@/api/companyService/post';
import type {CascaderProps, SelectProps} from 'ant-design-vue';
import {message} from "ant-design-vue";
import {useTabsViewStore} from '@/store/modules/tabsView';
import BUploadFile from '@/components/file/uploadFile.vue';
import {get} from '@/api/common';
import {getList} from "@/api/companyService/company";
import SelectLabel from "@/views/baseSettings/label/selectLabel.vue";
import dayjs from "dayjs";
import {getProfessionLikeList} from "@/api/jobUserManager/recommendMgt";
import {debounce} from "lodash-es";
import {getSysDictionaryList} from "@/api/system/dictionary";
import {getCreditRecordListByCompanyID} from "@/api/companyService/creditRecord";

interface postModel {
  dataModel: any;
}


export default defineComponent(
  {
    name: 'PostEdit',
    components: {BUploadFile,SelectLabel},
    setup() {
      const postCompany = reactive<postModel>({dataModel:{}});
      const router = useRouter();

      const fullpath = router.currentRoute.value.fullPath;
      const tabsViewStore = useTabsViewStore();
      const isEdit = ref<boolean>(false);

      const cultureLevelList = ref<SelectProps['options']>();
      const workYearTypeList = ref<SelectProps['options']>();
      const jobHuntList = ref<SelectProps['options']>();
      const companyList = ref<Array<any>>([]);
      const postStatusList = [{name: '启用', value: 1}, {name: '停用', value: 0}];
      const trialStatusList = [{name: '是', value: true}, {name: '否', value: false}];
      const firstProfessionList = ref<CascaderProps['options']>([]);
      const firstWorkCategoryList = ref<CascaderProps['options']>([]);
      const refSelectModel = ref();
      const professionList = ref<Array<any>>([]);
      const creditRecordMessage = ref("");

      const onJobFindDateChange = (dateString: any) => {
        postCompany.dataModel.jobFindDate = dateString;
        postCompany.dataModel.startTime = dateString ? dateString[0].format("YYYY-MM-DD") : '';
        postCompany.dataModel.endTime = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      }

      const getFirstWorkCategoryList = () => {
        get("workCategory/getFirstWorkCategoryList", null).then(data=>{
          firstWorkCategoryList.value = (data as any[]).map((x) => {
            return {
              label: x.name,
              value: x.code,
              isLeaf: false,
              tag: 'firstLevel'
            }
          });
        });
        console.log(firstWorkCategoryList.value);
      }

      const getFirstProfessionList = () => {
        get("profession/getFirstProfessionList", null).then(data=>{
          firstProfessionList.value = (data as any[]).map((x) => {
            return {
              label: x.name,
              value: x.code,
              isLeaf: false,
              tag: 'firstLevel'
            }
          });
        });
      }


      const loadLevelWorkCategoryList = (selectedOptions) => {
        let targetOption = selectedOptions[selectedOptions.length - 1];
        if (targetOption) {
          if (targetOption.tag == 'firstLevel') {
            get("workCategory/getListByParentWorkCategoryID",{parentWorkCategoryID:targetOption.value}).then(data => {
              targetOption.loading = false;
              targetOption.children = (data as any[]).map((x) => {
                return {
                  label: x.name,
                  value: x.code,
                  isLeaf: false,
                  tag: 'secondLevel'
                };
              });
            });
            postCompany.dataModel.hasWorkCategory = false;
          } else if (targetOption.tag == "secondLevel") {
            get("workCategory/getListByParentWorkCategoryID",{parentWorkCategoryID:targetOption.value}).then(data => {
              targetOption.loading = false;
              targetOption.children = (data as any[]).map((x) => {
                return {
                  label: x.name,
                  value: x.code,
                  isLeaf: true,
                  tag: 'thirdLevel'
                };
              });
            });
            postCompany.dataModel.hasWorkCategory = false;
          }

        }
      };

      const loadLevelProfessionList = (selectedOptions) => {
        let targetOption = selectedOptions[selectedOptions.length - 1];
        if (targetOption) {
          if (targetOption.tag == 'firstLevel') {
            get("profession/getListByParentProfessionID",{parentProfessionID:targetOption.value}).then(data => {
              targetOption.loading = false;
              targetOption.children = (data as any[]).map((x) => {
                return {
                  label: x.name,
                  value: x.code,
                  isLeaf: false,
                  tag: 'secondLevel'
                };
              });
            });
            postCompany.dataModel.hasProfession = false;
          } else if (targetOption.tag == "secondLevel") {
            get("profession/getListByParentProfessionID",{parentProfessionID:targetOption.value}).then(data => {
              targetOption.loading = false;
              targetOption.children = (data as any[]).map((x) => {
                return {
                  label: x.name,
                  value: x.code,
                  isLeaf: true,
                  tag: 'thirdLevel'
                };
              });
            });
            postCompany.dataModel.hasProfession = false;
          }

        }
      };

      watch(() => postCompany.dataModel.workCategoryName, (selectedValues) => {
        console.log("dd",selectedValues);
        if (selectedValues && selectedValues.length == 3) {
          postCompany.dataModel.workCode = selectedValues[2];
          postCompany.dataModel.hasWorkCategory = true;
        }
        console.log("dsd1",postCompany.dataModel);
      });

      const getCultureLevelList = async function () {
        const data: any = await getSysDictionaryList("CultureLevel");
        cultureLevelList.value = data;
      }

      const getWorkYearTypeList = async function () {
        const data: any = await getSysDictionaryList("WorkYearType");
        workYearTypeList.value = data;
      }

      const getJobHuntList = async function () {
        const data: any = await getSysDictionaryList("WorkNature");
        jobHuntList.value = data;
      }

      const onClose = () => {
        tabsViewStore.closeCurrentTabByPath('/companyService/post/add');
        tabsViewStore.closeCurrentTabByPath('/companyService/post/edit');
        router.push({name: '/companyService/post/index'});
      };

      const inputDataValidate = () => {
        const mobileReg = /^1[3|4|5|6|7|8|9]\d{9}$/;
        // const landlineReg = /[0-9-()（）]{7,18}/;
        const landlineReg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
        if (postCompany.dataModel.contactMobile != null && postCompany.dataModel.contactMobile.trim() != '') {
          if (!mobileReg.test(postCompany.dataModel.contactMobile) && !landlineReg.test(postCompany.dataModel.contactMobile)) {
            message.error("联系人电话格式有误!");
            return false;
          }
        }
        const emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
        if (postCompany.dataModel.contactEmail != null && postCompany.dataModel.contactEmail.trim() != '') {
          if (!emailReg.test(postCompany.dataModel.contactEmail)) {
            message.error("联系人邮箱格式有误!");
            return false;
          }
        }
        return true;
      };

      const onFinish = () => {
        if(!postCompany.dataModel.hasProfession){
          message.error("请选择完整的岗位信息！")
        }
        if(!postCompany.dataModel.hasWorkCategory){
          message.error("请选择完整的工种信息！");
          return;
        }
        console.log("当前岗位信息",postCompany.dataModel);
        if (inputDataValidate()) {
          postCompany.dataModel.workCategoryName = null;
          savePost(postCompany.dataModel).then((result) => {
            if (result) {
              tabsViewStore.closeCurrentTab(fullpath);
              tabsViewStore.addTabByPath('/companyService/post/index', {reload: 1});
            }
          }).finally(() => {
            // 重新填充，防止请求失败
            postCompany.dataModel.professionName = postCompany.dataModel.postName;
            postCompany.dataModel.workCategoryName = postCompany.dataModel.workName;
          });
        }
      }

      const onSelectModel = ()=>{
        refSelectModel.value.show();
      }

      const selectLabelChecks = (list: any)=>{
        postCompany.dataModel.listLabel = list;
      }

      const loadData = (id: any) => {
        isEdit.value = id != null;
        getWorkYearTypeList();
        getCultureLevelList();
        getJobHuntList();
        getFirstWorkCategoryList();
        getFirstProfessionList();
        getPostByID(id,true).then(result => {
          console.log("初始化岗位信息",result);
          postCompany.dataModel = result;
          postCompany.dataModel.hasProfession = true;
          postCompany.dataModel.hasWorkCategory = true;
          postCompany.dataModel.jobFindDate = [];
          if (postCompany.dataModel.startTime && postCompany.dataModel.endTime) {
            postCompany.dataModel.jobFindDate.push(dayjs(postCompany.dataModel.startTime, 'YYYY-MM-DD'));
            postCompany.dataModel.jobFindDate.push(dayjs(postCompany.dataModel.endTime, 'YYYY-MM-DD'));
          }
        })
      };


      // 行业工种选择变更事件
      function workCategoryChange(value: any, selectedOptions: any) {
        if (value.length >= 3) {
          postCompany.dataModel.workName = selectedOptions[2].label;
        }
      }

      // 岗位名称选择变更事件
      // function professionChange(value: any, selectedOptions: any) {
      //   if (value.length >= 3) {
      //     postCompany.dataModel.postName = selectedOptions[2].label;
      //   }
      // }

      // 企业名称选择变更事件
      function companyChange(value: any) {
        postCompany.dataModel.companyName = value;
        creditRecordMessage.value = "";
        setCompanyId(value);
      }

      const selectCompanyList = (companyName: string) => {
        console.log(companyName)
        if (companyName) {
          getList({
            pageIndex: 1,
            pageSize: 30,
            companyName,
          }).then((result: any) => {
            companyList.value = result.list;
            setCompanyId(companyName);
          })
        }
      }

      function setCompanyId(value: any) {
        const company = companyList.value.find(x => x.companyName === value);
        if (company) {
          postCompany.dataModel.companyID = company.companyID;
          getCreditRecordListByCompanyID({companyID: company.companyID, isEffective: 0}).then((result: any) => {
            if (result && result.length > 0) {
              creditRecordMessage.value = `该企业存在信用问题：${result[0].creditRecordContent}，列入日期：${dayjs(result[0].includeTime).format('YYYY-MM-DD')}，到期日期：${dayjs(result[0].expirationTime).format('YYYY-MM-DD')}`;
            }
          })
        } else {
          postCompany.dataModel.companyID = null;
        }
      }

      // 月薪变更
      function salaryChange() {
        if (postCompany.dataModel.maxSalary < postCompany.dataModel.minSalary) {
          postCompany.dataModel.maxSalary = postCompany.dataModel.minSalary;
        }
      }

      // 试用期月薪变更
      function trailSalaryChange() {
        if (postCompany.dataModel.trailMaxSalary < postCompany.dataModel.trailMinSalary) {
          postCompany.dataModel.trailMaxSalary = postCompany.dataModel.trailMinSalary;
        }
      }

      // 是否有试用期
      function isTrailChange() {
        if (!postCompany.dataModel.isTrail) {
          postCompany.dataModel.trailMonths = 0;
        }
      }

      // 试用期取证
      function trailMonthsChange() {
        postCompany.dataModel.trailMonths = Math.floor(postCompany.dataModel.trailMonths);
      }

      // 招聘人数变更
      function recruitCountChange() {
        if (postCompany.dataModel.recruitCount) {
          postCompany.dataModel.recruitCount = Math.floor(postCompany.dataModel.recruitCount);
        }
      }

      // 查询岗位
      const selectProfessionList = debounce((professionName: string) => {
        if (professionName) {
          getProfessionLikeList({
            pageIndex: 1,
            pageSize: 30,
            professionName,
          }).then(data => {
            professionList.value = data.list;
          })
        }
      }, 200)

      // 岗位名称变更
      function professionChange(value: any) {
        console.log(value)
        postCompany.dataModel.professionName = value;
        const profession = professionList.value.find(prof => prof.professionName === value);
        if (profession) {
          postCompany.dataModel.professionID = profession.professionID;
        } else {
          postCompany.dataModel.professionID = "";
        }
      }

      // 名称搜索关键字高亮
      const formatStr = (str: any, isPostName) => {
        if (!str) {
          return "";
        }
        if (isPostName) {
          return str.replace(postCompany.dataModel.professionName, '<span style="color: coral">' + postCompany.dataModel.professionName + '</span>');
        }
        return str.replace(postCompany.dataModel.companyName, '<span style="color: coral">' + postCompany.dataModel.companyName + '</span>');
      }

      return {
        ...toRefs(postCompany),
        onJobFindDateChange,
        loadData,
        onClose,
        onFinish,
        loadLevelWorkCategoryList,
        loadLevelProfessionList,
        onSelectModel,
        selectLabelChecks,
        refSelectModel,
        firstProfessionList,
        firstWorkCategoryList,
        companyList,
        postStatusList,
        trialStatusList,
        cultureLevelList,
        workYearTypeList,
        postCompany,
        isEdit,
        jobHuntList,
        workCategoryChange,
        professionChange,
        companyChange,
        salaryChange,
        trailSalaryChange,
        isTrailChange,
        trailMonthsChange,
        recruitCountChange,
        professionList,
        selectProfessionList,
        formatStr,
        selectCompanyList,
        creditRecordMessage
      }
    },
    created() {
      const id = history.state.params?.id;
      if (id) this.isEdit = true;
      this.loadData(id);
    }
  })
</script>

<style>
h1 {
  font-size: 18px;
  font-weight: bold;
  color: rgb(24, 144, 255);
  margin-bottom: 10px;
}
</style>

