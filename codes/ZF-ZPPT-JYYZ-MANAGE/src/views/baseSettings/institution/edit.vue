<template>
  <div class="card-edit">
    <a-form :model="dataModel"  autocomplete="off" @finish="onFinish">
      <a-divider orientation="left">运营机构信息</a-divider>
      <a-row type="flex">
        <a-col :span="8">
          <a-form-item label="企业名称"  name="companyName" :label-col="{span:8}"  :rules="[{ required: true, message: '请输入企业名称!' }]">
            <label v-if="opCategory==3">{{dataModel.companyName }}</label>
            <a-input v-model:value="dataModel.companyName" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="7">
          <a-form-item label="企业地址" name="companyAddress" :label-col="{span:7}" :rules="[{ required: true, message: '请输入企业地址!' }]">
            <label v-if="opCategory==3">{{dataModel.companyAddress }}</label>
            <a-input v-model:value="dataModel.companyAddress" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="负责人姓名" name="fzrName" :label-col="{span:8}" :rules="[{ required: true, message: '请输入负责人姓名!' }]">
            <label v-if="opCategory==3">{{dataModel.fzrName}}</label>
            <a-input v-model:value="dataModel.fzrName" placeholder=""/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row type="flex">
        <a-col :span="8">
          <a-form-item label="负责人电话"  name="fzrMobile" :label-col="{span:8}" :rules="[{ required: true, message: '请输入负责人电话!' }]">
            <label v-if="opCategory==3">{{dataModel.fzrMobile }}</label>
            <a-input v-model:value="dataModel.fzrMobile" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="7">
          <a-form-item label="法人姓名"  name="frName" :label-col="{span:7}" :rules="[{ required: true, message: '请输入法人姓名!' }]">
            <label v-if="opCategory==3">{{dataModel.frName }}</label>
            <a-input v-model:value="dataModel.frName" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="法人电话"  name="frMobile" :label-col="{span:8}" :rules="[{ required: true, message: '请输入法人电话!' }]">
            <label v-if="opCategory==3">{{dataModel.frMobile }}</label>
            <a-input v-model:value="dataModel.frMobile" placeholder=""/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row type="flex">
        <a-col :span="8">
          <a-form-item label="企业机构代码"  name="companyCode" :label-col="{span:8}" :rules="[{ required: true, message: '请输入企业机构代码!' }]">
            <label v-if="opCategory==3">{{dataModel.companyCode }}</label>
            <a-input v-model:value="dataModel.companyCode" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="7">
          <a-form-item
            label="经济类型"
            :label-col="{ span: 7 }"
            name="economicTypeID"
          >
            <a-select
              ref="select"
              style="width: 100%"
              v-model:value="dataModel.economicTypeID"
              :options="companyTypeList"
              :field-names="{ label: 'name', value: 'value' }"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item
            label="注册地址行政区划"
            :label-col="{ span: 8 }"
            name="signInPoliticalArea"
          >
            <a-input v-model:value="dataModel.signInPoliticalArea"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row type="flex">
        <a-col :span="8">
          <a-form-item
            label="营业执照有效期"
            :label-col="{ span: 8 }"
            name="validDate"
          >
            <a-radio-group v-model:value="showPicker" name="showPicker">
              <a-radio value="1">至长期</a-radio>
              <a-radio value="2">
                <a-date-picker
                  v-model:value="showDateTime"
                  picker="date"
                  value-format="YYYY-MM-DD"
                  :disabled="showPicker === '1'"
                />
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :span="7">
        </a-col>
        <a-col :span="8">
        </a-col>
      </a-row>
      <a-divider orientation="left" v-if="opCategory==2">管理驿站 共计：{{manageSites.length}}</a-divider>
      <!--      <a-row>
        <a-col :span="24" style="text-align: right; margin-top: 20px; margin-right: 20px">
          <Space>
            <a-button @click="addSite">
              <template #icon>
                <plus-circle-outlined/>
              </template>
              新增
            </a-button>
          </Space>
        </a-col>
      </a-row>-->
      <a-row  v-if="opCategory==2">
        <a-col style="margin-bottom: 20px;">
          <a-table :columns="columns" :data-source="manageSites" :pagination="pagination"
                   :loading="formState.loading"
                   @change="handleTableChange"
                   :row-selection="{ selectedRowKeys: formState.selectedRowKeys, onChange: onSelectChange}"
                   :row-key="record=>record.siteId"
                   bordered>
            <template #bodyCell="{ column ,index}">
              <template v-if="column.key === 'siteName'">
                <div>
                  <a-input v-model:value="manageSites[index][column.key]" style="border:none;text-align: center;"></a-input>
<!--                  <a-select
                    ref="select"
                    v-model:value="manageSites[index][column.key]"
                    :options="allSites"
                    @change="manageSitesChange(index,manageSites[index][column.key])"
                    :field-names="{ label: 'siteName', value: 'siteID' }" style="width: 200px" >
                  </a-select>-->
                </div>
              </template>
              <template v-if="column.key === 'detailAddress'">
                   {{manageSites[index][column.key]}}
              </template>
              <!--              <template v-if="column.key === 'operation'">
                <a-button type="link" size="small" @click="delSite(index)">删除</a-button>
              </template>-->
            </template>
          </a-table>
        </a-col>
      </a-row>
      <a-divider orientation="left">其他</a-divider>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="企业简介"
            :label-col="{ span: 24 }"
            name="companyDesc"
          >
            <a-textarea :auto-size="{ minRows: 4, maxRows: 10 }"  v-model:value="dataModel.companyDesc" :disabled="opCategory==3" placeholder="企业简介" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="备注"
            :label-col="{ span: 24 }"
            name="remark"
          >
            <a-textarea :auto-size="{ minRows: 4, maxRows: 10 }"  v-model:value="dataModel.remark" :disabled="opCategory==3" placeholder="备注" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-form-item class="buttom-btns">
        <a-button @click="onClose">取消</a-button>
        <a-button type="primary" html-type="submit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts">
import {computed, defineComponent, reactive, ref, toRefs} from "vue";
import {useTabsViewStore} from "@/store/modules/tabsView";
import {getInstitutionByID, saveInstitution} from "@/api/baseSettings/institution";
import {getSiteList,getListByInstitutionID,getSiteByID} from "@/api/baseSettings/siteInfo";
import BUploadFile from "@/components/file/uploadFile.vue";
import {message, type SelectProps, TableColumnsType, TableProps} from "ant-design-vue";
import {getPaginationTotalTitle} from "@/utils/common";
import {getSysDictionaryList} from "@/api/system/dictionary";
import dayjs from "dayjs";

interface InstitutionModel {
  dataModel: any;
}
export default defineComponent({
  name:'InstitutionEditForm',
  components: {BUploadFile},
  props: {
    loadData: {
      type: Function,
      default: null
    }
  },
  setup() {
    const title = ref();
    const opCategory = ref();
    const tabsViewStore = useTabsViewStore();
    const isAllowCommit = ref<boolean>(false);
    const formData = reactive<InstitutionModel>({ dataModel:{}});
    const pageParams = reactive({
      pageIndex: 1,
      pageSize: 10,
      institutionID:''
    });
    const columns: TableColumnsType = [
      {title: '序号', align: "center",key: 'siteID',customRender: item => `${pageParams.pageSize * (pageParams.pageIndex - 1) + item.index + 1}`},
      {title: '驿站名称', dataIndex: 'siteName', key: 'siteName', align: "center"},
      {title: '驿站地址', dataIndex: 'detailAddress', key: 'detailAddress', align: "center"},
     /* {title: '操作', key: 'operation', fixed: 'right',width:170, align: "center"},*/
    ];
    const pagination = computed(() => ({
      total: formState.total,
      current: pageParams.pageIndex,
      pageSize: pageParams.pageSize,
      showSizeChanger: true,
      showTotal: total => getPaginationTotalTitle(total)
    }));
    const onSelectChange = (selectedRowKeys: any) => {
      formState.selectedRowKeys = selectedRowKeys;
    };
    const handleTableChange: TableProps['onChange'] = (pag: { pageSize: number; current: number },) => {
      pageParams.pageIndex = pag.current;
      pageParams.pageSize = pag.pageSize;
    };
    const formState = reactive({
      total: 0,
      selectedRowKeys: [],
      loading: false
    });

    const allSites = ref<any>([]);
    const manageSites = ref<any>([]);
    const showPicker = ref('1');
    const showDateTime = ref<any>();

    const companyTypeList = ref<SelectProps['options']>();

    const getCompanyTypeList = async function () {
      const data: any = await getSysDictionaryList("CompanyType");
      companyTypeList.value = data;
    }

    const loadData = (institutionID: any,operateType:any) => {
      getAllSites();
      getCompanyTypeList();
      opCategory.value = operateType;
      getInstitutionByID(institutionID).then((result: any) => {
        formData.dataModel = result;
        GetManageSites(formData.dataModel.institutionID);
        showPicker.value = formData.dataModel.validDate == '2099-12-31T00:00:00.000+08:00' ? '1' : '2';
        if (showPicker.value === '1') {
          showDateTime.value = dayjs(new Date().toLocaleDateString().replaceAll("/", "-"));
        } else {
          showDateTime.value = formData.dataModel.validDate;
        }
      });
    }

    const getAllSites = () => {
      pageParams.institutionID = '';
      getSiteList(pageParams).then((result :any) => {
        allSites.value = result.list;
        // console.log('allSites',allSites);
      })
    }

    const GetManageSites = (id: string) => {
      pageParams.institutionID = id;
      getListByInstitutionID(pageParams).then((result :any)=> {
          manageSites.value = result.list;
      });
    }

   /* const addSite = () => {
      if(allSites.value.length >0){
        const newSite = allSites.value[0];
        (manageSites.value as any[]).push({siteID:newSite.siteID,siteName:newSite.siteName,detailAddress:newSite.detailAddress});
        validateSameSite();
      }else{
        message.error("未查询到站点信息!");
      }
    }
    const delSite = (site) =>{
      (manageSites.value as any[]).splice(site, 1);
      validateSameSite();
    }*/

    const manageSitesChange = (index:any,siteID:any) =>{
      getSiteByID(siteID).then((result :any)=> {
        manageSites.value[index]= result;
        // console.log("manageSites",manageSites.value);
        /*validateSameSite();*/
      });
    };

    /*const validateSameSite = ()=>{
      if(manageSites.value.length>1){
        manageSites.value.forEach(item=>{
          const sameData = manageSites.value.filter(x => x.siteID == item.siteID);
          // console.log("sameData",sameData);
          if (sameData.length >1){
            isAllowCommit.value = false;
          }
        });
      }
    };*/
    const mobileValidate = ()=>{
      isAllowCommit.value = true;
      const mobileReg = /^1[3|4|5|6|7|8|9]\d{9}$/;
      // const landlineReg = /[0-9-()（）]{7,18}/;
      const landlineReg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
      if(!mobileReg.test(formData.dataModel.frMobile)){
        message.error("输入的法人电话有误!");
        isAllowCommit.value = false;
      }
      if (!mobileReg.test(formData.dataModel.fzrMobile) && !landlineReg.test(formData.dataModel.fzrMobile)) {
        message.error("输入的负责人电话有误!");
        isAllowCommit.value = false;
      }else{
        console.log(formData.dataModel.fzrMobile,mobileReg.test(formData.dataModel.fzrMobile));
        console.log(formData.dataModel.fzrMobile,landlineReg.test(formData.dataModel.fzrMobile));
      }
    };

    const onClose = (reload: any) => {
      tabsViewStore.closeCurrentTabByPath('/baseSettings/institution/add');
      tabsViewStore.closeCurrentTabByPath('/baseSettings/institution/edit');
      tabsViewStore.openTab('/baseSettings/institution/index',{reload:reload});
    };

    const onFinish = () => {
      mobileValidate();
      if (showPicker.value === '1') {
        formData.dataModel.validDate = '2099-12-31T00:00:00.000+08:00';
      } else {
        formData.dataModel.validDate = showDateTime.value;
      }
      if (isAllowCommit.value){
        formData.dataModel.manageSites = manageSites.value;
        saveInstitution(formData.dataModel).then(result => {
          if (result) {
            onClose(1);
          }
        });
      }
    };


    return {
      ...toRefs(formData),
      loadData,
      onClose,
      onFinish,
      title,
      opCategory,
      columns,
      pageParams,
      pagination,
      formState,
      allSites,
      manageSites,
      isAllowCommit,
      companyTypeList,
      showPicker,
      showDateTime,
      getAllSites,
      GetManageSites,
      /*addSite,
      delSite,*/
      handleTableChange,
      onSelectChange,
      manageSitesChange,
    };
  },
  created() {
    const id = history.state.params?.id;
    const operateType = history.state.params?.op;
    this.loadData(id,operateType);
  },
})
</script>

