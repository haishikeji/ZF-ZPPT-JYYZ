<template>
  <a-modal
    :width="1400"
    v-model:visible="visible"
    :title="title"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
    ok-text="确认"
    cancel-text="取消"
    :keyboard="false"
    :mask-closable="false"
  >
    <div>
      <a-form
        ref="formRef"
        name="advanced_search"
        class="ant-advanced-search-form"
        :model="searchParams"
      >
        <!-- 信用问题提示 -->
        <p v-if="creditRecordMessage" style="color: red; margin-bottom: 10px">{{ creditRecordMessage }}</p>
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item label="招聘企业" :label-col="{ span: 8 }" name="companyName">
              <a-input v-model:value="searchParams.companyName" style="color: black;" disabled="true" placeholder=""/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="招聘岗位" :label-col="{ span: 8 }" name="professionName">
              <a-input v-model:value="searchParams.professionName" style="color: black;" disabled="true"
                       placeholder=""/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="求职岗位" :label-col="{ span: 8 }" name="huntProfessionName">
              <a-auto-complete
                v-model:value="searchParams.huntProfessionName"
                :options="professionList"
                @search="selectProfessionList"
                @change="professionChange"
                :field-names="{
                            value:'professionName',
                          }"
              >
                <template #option="item">
                  <span v-html="formatStr(item.professionName)"></span>({{ item.parentProfessionName }})
                </template>
              </a-auto-complete>
            </a-form-item>
          </a-col>

          <a-col :span="6" style="text-align: right">
            <a-button type="primary" html-type="submit" @click="onSearch">查询</a-button>
            <a-button
              style="margin: 0 8px"
              @click="
              () => {
                searchParams.pageIndex = 1;
                searchParams.pageSize = 10;
                searchParams.userName = '';
                searchParams.keyPersonType = null;
                searchParams.parentProfessionID = '';
                searchParams.huntProfessionName = null;
                searchParams.checkParentProfessionID = false;
                searchParams.cultureRank = null;
                searchParams.workYear = null;
                searchParams.regionCode = null;
                searchParams.siteID = null;
                searchParams.minSalary = null;
                searchParams.maxSalary = null;
                loadData();
              }
            ">重置
            </a-button>
            <a style="font-size: 12px" @click="expand = !expand">
              <template v-if="expand">
                <UpOutlined/>
              </template>
              <template v-else>
                <DownOutlined/>
              </template>
              {{ expand ? '收缩' : '展开' }}
            </a>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-show="expand">
          <a-col :span="6">
            <a-form-item label="求职人员姓名" :label-col="{ span: 8 }" name="userName">
              <a-input v-model:value="searchParams.userName" placeholder=""/>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="文化程度" :label-col="{span:8}" name="cultureRank">
              <a-select
                ref="select"
                v-model:value="searchParams.cultureRank"
                :options="cultureLevelList"
                :field-names="{ label: 'name', value: 'value' }"
                :allow-clear="true"
                @change="onSearch"
              >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="重点人员类别" :label-col="{span:8}" name="cultureLevel">
              <a-select
                ref="select"
                v-model:value="searchParams.keyPersonType"
                :options="keyPersonTypeList"
                :field-names="{ label: 'name', value: 'value' }"
                :allow-clear="true"
                @change="onSearch"
              >
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="工作年限" :label-col="{ span: 8 }" name="workYear">
              <a-input v-model:value="searchParams.workYear" placeholder=""/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-show="expand">
          <a-col :span="12" style="padding-left: 37px;">
            <a-form-item label="所属区县" :label-col="{ span: 3 }" name="regionCode">
              <a-select
                v-model:value="curRegionNameList"
                :options="regionList"
                mode="multiple"
                size="middle"
                @change="curRegionNameListChange"
              >
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12" style="padding-left: 37px;">
            <a-form-item label="所属驿站" :label-col="{ span: 3 }" name="siteID">
              <a-select
                v-model:value="curSiteNameList"
                :options="siteList"
                mode="multiple"
                size="middle"
                @change="onSearch"
              >
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>

        <a-row :gutter="24" v-show="expand">
          <a-col :span="9" >
            <a-form-item label="薪资要求" :label-col="{span:5}" name="maxSalary">
              <a-input-group compact>
                <a-input :min="0" :controls="false" v-model:value="searchParams.minSalary"
                         style="width:110px;" placeholder=""/>
                <a-input placeholder="——" disabled style="width: 30px; border-left: 0;border-right: 0;
                       pointer-events: none;background: #fff;margin-left: 1px; "/>
                <a-input :min="searchParams.minSalary" :controls="false" v-model:value="searchParams.maxSalary"
                         style="width:110px;" placeholder=""/>
              </a-input-group>
            </a-form-item>
          </a-col>
          <a-col  :span="15">
            <a-checkbox  v-if="searchParams.type===0"
                         v-model:checked="searchParams.checkParentProfessionID"
                         @change="onSearch">
              相关行业
            </a-checkbox>
          </a-col>
        </a-row>

        <a-row class="edit-operation">
          <a-col :span="24" style="text-align: right">
            <a-button type="primary" html-type="submit" v-if="searchParams.type===0" functioncode="T01020206"
                      @click='onBatchRecommend()'>批量推荐
            </a-button>
          </a-col>
        </a-row>
      </a-form>
      <div class="search-result-list">
        <a-table :columns="columns" :data-source="dataList" :scroll="{ x: '100%', y: 500 }" :pagination="pagination"
                 :loading="formState.loading"
                 :row-selection="{ selectedRowKeys: formState.selectedRowKeys, onChange: onSelectChange }"
                 :row-key="(record) => record.recommendMgtID" bordered @change="handleTableChange">
          <template #bodyCell="{ column, text, record }">
            <template v-if="searchParams.type===0 && column.key === 'operation'">
              <div class="table-operation">
                <a-button type="link" size="small" @click='onRecommend(record)'>推荐
                </a-button>
              </div>
            </template>
            <template v-if="column.key === 'userName'">
              <div style="text-align: center;color:#3a7be0;">
                <a size="small" @click='onShowJobUserInfo(record)' >{{ record.userName }}</a>
              </div>
            </template>
          </template>
        </a-table>
      </div>
      <jobUserDetailModel ref="modalJobUserInfoRef"></jobUserDetailModel>
    </div>
  </a-modal>
</template>

<script lang="ts">
import {reactive, ref, computed, defineComponent} from 'vue';
import type {FormInstance, TableColumnsType, TableProps} from 'ant-design-vue';
import {getRecommendJobList, addRecommend, getProfessionLikeList} from '@/api/jobUserManager/recommendMgt';
import {getPaginationTotalTitle} from '@/utils/common';
import type {SelectProps} from "ant-design-vue";
import {getSysDictionaryList} from "@/api/system/dictionary";
import {message} from "ant-design-vue";
import {debounce} from "lodash-es";
import {getAllSite} from "@/api/baseSettings/siteInfo";
import {getRegionCodeList} from '@/api/system/area/index';
import JobUserDetailModel from "./jobUserDetail.vue";
import {getCreditRecordListByCompanyID} from "@/api/companyService/creditRecord";
import dayjs from "dayjs";

export default defineComponent({
  name: 'RecommendJobList',
  components:{JobUserDetailModel},
  setup() {
    const visible = ref<boolean>(false);
    const confirmLoading = ref<boolean>(false);
    const formRef = ref<FormInstance>();
    const modalJobUserInfoRef = ref();
    const expand = ref(true);
    const title = ref<string>('');
    const siteList = ref<any>([]);
    const regionList = ref<any>([]);

    const curRegionNameList = ref([]);
    const curSiteNameList = ref([]);
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 10,
      companyName: '',
      professionName: null,
      huntProfessionName:null,
      type: null,
      postID: null,
      userName: '',
      professionID: '',
      keyPersonType: null,
      parentProfessionID: '',
      checkParentProfessionID: false,
      cultureRank: null,
      workYear: null,
      regionCode: null,
      siteID:null,
      minSalary: null,
      maxSalary: null,
      regionNameList:'',
      siteNameList:'',
    });
    const formState = reactive({
      total: 0,
      selectedRowKeys: [],
      loading: false,
    });
    const cultureLevelList = ref<SelectProps['options']>();
    const keyPersonTypeList = ref<SelectProps['options']>();
    const parentProfessionID = ref<string>('');
    const companyID = ref("");
    const creditRecordMessage = ref("");

    const columns: TableColumnsType = [
      {
        title: '序号',
        align: 'center',
        width: 80,
        key: 'recommendMgtID',
        customRender: (item) =>
          `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`,
      },
      {title: '求职人员姓名', dataIndex: 'userName', key: 'userName', align: "center",},
      {title: '求职岗位', dataIndex: 'professionName', key: 'professionName', align: "center",},
      {title: '联系电话', dataIndex: 'userMobile', key: 'userMobile', align: "center",},
      {title: '所属区县', dataIndex: 'regionName', key: 'regionName', align: "center",},
      {title: '所属驿站', dataIndex: 'siteName', key: 'siteName', align: "center",},
      {title: '工作年限', dataIndex: 'workYear', key: 'workYear', align: "center",},
      {
        title: '薪资要求', dataIndex: 'salary', key: 'salary', align: "center",
        customRender: (item) => {
          const salary = showSalary(item.record.minSalary, item.record.maxSalary);
          return salary;
        }
      },
      {title: '文化程度', dataIndex: 'culturerankName', key: 'culturerankName', align: "center",},
      {title: '重点人员类别', dataIndex: 'keypersontypeName', key: 'keypersontypeName', align: "center",},
      {title: '操作', key: 'operation', width: 60, align: 'center'},
    ];
    const pagination = computed(() => ({
      total: formState.total,
      current: searchParams.pageIndex,
      pageSize: searchParams.pageSize,
      showSizeChanger: true,
      showTotal: (total) => getPaginationTotalTitle(total),
    }));
    const dataList = ref([]);
    const addRecommendList = ref([] as any);

    const getRegionList = async function () {
      const regionResult: any = await getRegionCodeList();
      regionResult.map(x=>{
        regionList.value.push({value:x.name});
      });
    };
    const getAllSites = async function () {
      siteList.value = [];
      getAllSite().then((result :any) => {
        result.list.map(x=>{
          if(curRegionNameList.value.length>0){
            curRegionNameList.value.map(m=> {
              if(x.regionName == m){
                siteList.value.push({value:x.siteName});
              }
            });
          }else{
            siteList.value.push({value:x.siteName});
          }
        });
      })
    }

    const onShowJobUserInfo = (item:any) =>{
      modalJobUserInfoRef.value.show(item.jobUserID);
    }

    const onSelectChange = (selectedRowKeys: any) => {
      console.log(selectedRowKeys)
      formState.selectedRowKeys = selectedRowKeys;
    };

    const handleTableChange: TableProps['onChange'] = (pag: {
      pageSize: number;
      current: number;
    }) => {
      searchParams.pageIndex = pag.current;
      searchParams.pageSize = pag.pageSize;
      loadData();
    };
    // 名称搜索关键字高亮
    const formatStr = (str: any) => {
      if (!str) {
        return "";
      }
      return str.replace(searchParams.huntProfessionName, '<span style="color: coral">' + searchParams.huntProfessionName + '</span>');
    }
    const professionList = ref<Array<any>>([]);
    // 查询岗位
    const selectProfessionList = debounce((professionName: string) => {
      if (professionName) {
        getProfessionLikeList({
          pageIndex: 1,
          pageSize: 30,
          professionName
        }).then(data => {
          professionList.value = data.list;
        })
      }
    }, 200)

    // 岗位名称变更
    function professionChange(value: any) {
      searchParams.huntProfessionName = value;
    }

    const curRegionNameListChange = ()=>{
      curSiteNameList.value = [];
      getAllSites();
      onSearch();
    }

    const onSearch = () => {
      searchParams.parentProfessionID = searchParams.checkParentProfessionID ? parentProfessionID.value : '';
      searchParams.regionNameList  = curRegionNameList.value.join(',');
      searchParams.siteNameList  = curSiteNameList.value.join(',');
      loadData();
    }




    const loadData = async function () {
      formState.loading = true;
      await getRegionList();
      await getAllSites();
      await getCreditRecordListByCompanyID({companyID: companyID.value, isEffective: 0}).then((result: any) => {
        if (result && result.length > 0) {
          creditRecordMessage.value = `该企业存在信用问题：${result[0].creditRecordContent}，列入日期：${dayjs(result[0].includeTime).format('YYYY-MM-DD')}，到期日期：${dayjs(result[0].expirationTime).format('YYYY-MM-DD')}`;
        }
      })
      const result: any = await getRecommendJobList(searchParams);
      dataList.value = result.list;
      formState.total = result.total;
      formState.loading = false;
    };

    const showSalary = (minSalary: any, maxSalary: any) => {
      if (minSalary != null) {
        if (maxSalary != null) {
          return minSalary.toString() + "-" + maxSalary.toString();
        } else {
          return "≥" + minSalary.toString();
        }
      } else {
        if (maxSalary != null) {
          return "≤" + maxSalary.toString();
        } else {
          return "";
        }
      }
    }

    const show = (professionID: any, parentID: any, professionName: any, postID: any, companyName: any, companyId: any, type: any, titleName: string) => {
      visible.value = true;
      title.value = titleName;
      parentProfessionID.value = parentID;
      searchParams.checkParentProfessionID = false;
      searchParams.parentProfessionID = "";
      searchParams.postID = postID;
      searchParams.companyName = companyName;
      searchParams.professionID = professionID;
      searchParams.professionName = professionName;
      searchParams.type = type;
      companyID.value = companyId
      loadData();
    }

    const onRecommend = (item: any) => {
      formState.selectedRowKeys = [];
      if(item){
        formState.selectedRowKeys.push(item.recommendMgtID as never);
      }
      addRecommendList.value.push({
        recommendMgtID: item.recommendMgtID,
        jobHuntID: item.jobhuntID,
        postID: searchParams.postID,
        recommendType: 0
      });
      addRecommend(addRecommendList.value).then((data) => {
        if(data!=-1){
          message.info("提交成功！");
          loadData();
          addRecommendList.value = [];
        }
      });
    };

    const onBatchRecommend = () => {
      if (formState.selectedRowKeys.length == 0) {
        message.warn("请选择需要推荐的求职人员！")
        return;
      }
      formState.selectedRowKeys.forEach(id => {
        const item: any = dataList.value.find((x: any) => x.recommendMgtID == id)
        if (item) {
          addRecommendList.value.push({
            recommendMgtID: item.recommendMgtID,
            jobHuntID: item.jobhuntID,
            postID: searchParams.postID,
            recommendType: 0
          });
        }
      })
      addRecommend(addRecommendList.value).then((data) => {
        if(data!=-1){
          message.info("提交成功！");
          loadData();
          addRecommendList.value = [];
        }
      });
    };

    const handleOk = () => {
      visible.value = false;
    };

    const handleCancel = () => {
      visible.value = false;
    };

    const getCultureLevelList = () => {
      getSysDictionaryList('CultureLevel').then((data) => {
        cultureLevelList.value = data;
      });
    };

    const getKeyPersonTypeList = () => {
      getSysDictionaryList('KeyPersonType').then((data) => {
        keyPersonTypeList.value = data;
      });
    };

    return {
      title,
      visible,
      confirmLoading,
      formRef,
      expand,
      searchParams,
      formState,
      columns,
      pagination,
      siteList,
      regionList,
      dataList,
      professionList,
      curRegionNameList,
      curSiteNameList,
      cultureLevelList,
      addRecommendList,
      keyPersonTypeList,
      getAllSites,
      show,
      formatStr,
      professionChange,
      curRegionNameListChange,
      onSearch,
      showSalary,
      onRecommend,
      onBatchRecommend,
      onSelectChange,
      modalJobUserInfoRef,
      onShowJobUserInfo,
      handleTableChange,
      loadData,
      getCultureLevelList,
      getKeyPersonTypeList,
      selectProfessionList,
      handleOk,
      handleCancel,
      creditRecordMessage
    };
  },
  created() {
    this.getCultureLevelList();
    this.getKeyPersonTypeList();
  }
});
</script>

<style lang="less" scoped></style>
