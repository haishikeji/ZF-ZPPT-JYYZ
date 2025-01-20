<template>
  <div class="card-search">
    <a-form
      ref="formRef"
      name="advanced_search"
      class="ant-advanced-search-form"
      :model="searchParamsState"
    >
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item label="姓名" :label-col="{ span: 8 }" name="name">
            <a-input v-model:value="searchParamsState.name" placeholder="" :allow-clear="true"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="所属驿站" :label-col="{ span: 8 }" name="siteID">
            <a-select
              ref="select"
              v-model:value="searchParamsState.siteID"
              :options="siteList"
              :field-names="{ label: 'siteName', value: 'siteID' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="就业状态" :label-col="{ span: 8 }" name="jobStatus">
            <a-select
              ref="select"
              v-model:value="searchParamsState.jobStatus"
              :options="jobStatusList"
              :field-names="{ label: 'name', value: 'value' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6" style="text-align: left">
          <a-button type="primary" html-type="submit" @click="onFinish">查询</a-button>
          <a-button
            style="margin: 0 8px"
            @click="
              () => {
                formRef.resetFields();
                loadData();
              }
            ">重置</a-button>
          <a style="font-size: 12px" @click="expand = !expand">
            <template v-if="expand">
              <UpOutlined />
            </template>
            <template v-else>
              <DownOutlined />
            </template>
            {{ expand ? '收缩' : '展开' }}
          </a>
        </a-col>
      </a-row>
      <a-row :gutter="24" v-show="expand">
        <a-col :span="6">
          <a-form-item label="性别" :label-col="{ span: 8 }" name="sexId">
            <a-select
              ref="select"
              v-model:value="searchParamsState.sexId"
              :options="sexTypeList"
              :field-names="{ label: 'name', value: 'value' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="最高学历" :label-col="{ span: 8 }" name="educationId">
            <a-select
              ref="select"
              v-model:value="searchParamsState.educationTypeId"
              :options="educationTypeList"
              :field-names="{ label: 'name', value: 'value' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="重点人员类别" :label-col="{ span: 8 }" name="emphasisTypeId">
            <a-select
              ref="select"
              v-model:value="searchParamsState.emphasisTypeId"
              :options="emphasisTypeList"
              :field-names="{ label: 'name', value: 'value' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24" v-show="expand">
        <a-col :span="6">
          <a-form-item label="所属县区" :label-col="{span:8}" name="regionCode">
            <a-select
                ref="select"
                v-model:value="searchParamsState.regionCode"
                :options="regionList"
                :field-names="{ label: 'name', value: 'code' }"
                :allow-clear="true"
                @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="登记时间" :label-col="{ span: 8 }">
            <a-range-picker v-model:value="createDate" :placeholder="['开始日期', '结束日期']" format="YYYY-MM-DD"
                            @change="onCreateTimeChange"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="创建人" :label-col="{span:8}" name="createUserName">
            <a-input v-model:value="searchParamsState.createUserName" placeholder="" :allow-clear="true"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="edit-operation">
        <a-col :span="24" class="flex-space-between">
          <div>
            <!-- 表格字段筛选按钮 -->
            <ColumnsSetting :table-columns="originalColumns" :checked-table-columns="columns"
                            @on-check="columnsCheckSub"></ColumnsSetting>
          </div>
          <div>
            <a-button type="primary" html-type="submit" @click='openClaimModal' functioncode="T01030109">认领求职人员
            </a-button>
            <a-button type="primary" html-type="submit" @click='onAdd' functioncode="T01030102">新增</a-button>
            <BImportExcel functioncode="T01030105"
                          :options="importOptions"
                          @success="loadData">
            </BImportExcel>
            <BExportExcel :title="'导出'" :filename="'求职人员信息'" v-show="false"
                          :url="'jobUserService/jobUser/export'"
                          :params="{...exportSearchParams, isExport: true, rows:10000,jobUserIDList:formState.selectedRowKeys.join(',')}"></BExportExcel>
          </div>
        </a-col>
      </a-row>
    </a-form>
    <div class="search-result-list">
      <a-table
        :columns="columns"
        :data-source="dataList"
        :scroll="{ x: '100%', y: 500 }"
        :pagination="pagination"
        :loading="formState.loading"
        :row-selection="{ selectedRowKeys: formState.selectedRowKeys, onChange: onSelectChange }"
        :row-key="(record) => record.jobUserID"
        bordered
        @change="handleTableChange">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'operation'">
            <div class="table-operation">
              <a-button type="link" size="small" functioncode="T01030106" @click='onVitae(record)'>个人简历</a-button>
              <a-button type="link" size="small" functioncode="T01030103" @click='onEdit(record)'>编辑</a-button>
              <a-button type="link" size="small" functioncode="T01030104" @click="onDel(record)">删除</a-button>
              <a-button type="link" size="small" functioncode="T01030108" @click="openServiceModal(record)">服务跟进
              </a-button>
            </div>
          </template>
        </template>
      </a-table>
    </div>

    <!-- 跟进服务记录 -->
    <ServiceTableCom ref="serviceTableComRef"></ServiceTableCom>
    <!-- 认领求职人员 -->
    <ClaimJobUserTableCom ref="claimJobUserTableComRef" @modal-ok="loadData"></ClaimJobUserTableCom>
  </div>
</template>

<script lang="ts">
import {computed, createVNode, defineComponent, reactive, ref} from 'vue';
import type {FormInstance, SelectProps, TableProps} from 'ant-design-vue';
import {message, Modal} from "ant-design-vue";
import {del, getList} from '@/api/jobUserManager/jobuser';
import {getSysDictionaryList} from '@/api/system/dictionary';
import {getPaginationTotalTitle} from '@/utils/common';
import {useTabsViewStore} from "@/store/modules/tabsView";
import {DownOutlined, ExclamationCircleOutlined, UpOutlined} from "@ant-design/icons-vue";
import BExportExcel from "@/components/basic/excel/exportExcel/exportExcel.vue";
import BImportExcel from "@/components/basic/excel/importExcel/importExcel.vue";
import {getSiteList} from "@/api/baseSettings/siteInfo";
import {get} from "@/api/common";
import ColumnsSetting from "@/components/common/ColumnsSetting.vue";
import type {ImportProps} from "@/components/basic/excel/importExcel/ImportProps";
import ServiceTableCom from "@/views/jobUserManager/jobuser/ServiceTableCom.vue";
import ClaimJobUserTableCom from "@/views/jobUserManager/jobuser/ClaimJobUserTableCom.vue";

export default defineComponent({
  name: 'JobUserList',
  components: {
    ClaimJobUserTableCom,
    ServiceTableCom, ColumnsSetting, DownOutlined, UpOutlined, BExportExcel, BImportExcel
  },
  setup() {
    const modalRoleUserRef = ref();
    const expand = ref(false);
    const modalRoleEditRef = ref();
    const formRef = ref<FormInstance>();
    const serviceTableComRef = ref();
    const claimJobUserTableComRef = ref();
    const searchParamsState = reactive({
      pageIndex: 1,
      pageSize: 20,
      name: null,
      siteId: null,
      jobStatus: null,
      sexId: null,
      educationTypeId: null,
      emphasisTypeId: null,
      siteID: "",
      startDate: "",
      endDate: "",
      regionCode: "",
      createUserName: ""
    });
    // 导出Excel查询参数
    const exportSearchParams = computed(() => {
      let data = JSON.parse(JSON.stringify(searchParamsState));
      data.pageSize = formState.total;
      return data;
    })
    const formState = reactive({
      total: 0,
      selectedRowKeys: [],
      loading: false,
    });
    // 原始表格定义数据
    const originalColumns = [
      {
        title: '序号',
        align: 'center',
        width: 80,
        key: 'jobUserID',
        customRender: (item) =>
          `${searchParamsState.pageSize * (searchParamsState.pageIndex - 1) + item.index + 1}`,
        isDisabled: true
      },
      {title: '姓名', dataIndex: 'name', key: 'name', width: 100,align: "center"},
      {title: '性别', dataIndex: 'genderName', key: 'genderName', width: 80,align: "center"},
      {title: '联系电话', dataIndex: 'userMobile', key: 'userMobile', align: "center"},
      {title: '所属驿站', dataIndex: 'siteName', key: 'siteName', align: "center"},
      {title: '年龄', dataIndex: 'age', key: 'age', align: "center"},
      {title: '就业状态', dataIndex: 'jobStatusName', key: 'jobStatusName', align: "center"},
      {title: '重点人员类别', dataIndex: 'keyTypeName', key: 'keyTypeName', align: "center"},
      {title: '毕业日期', dataIndex: 'finishDate', key: 'finishDate', align: "center", isDefaultClose: true},
      {title: '民族', dataIndex: 'nationName', key: 'nationName', width: 80, align: "center", isDefaultClose: true},
      {title: '最高学历', dataIndex: 'cultureName', key: 'cultureName', align: "center", isDefaultClose: true},
      {title: '地址', dataIndex: 'address', key: 'address', align: "center", isDefaultClose: true},
      {title: '操作', key: 'operation', width: 240, align: 'center', isDisabled: true},
    ];
    // 响应式表格定义
    const columns = ref<Array<any>>(originalColumns.filter(item => !item.isDefaultClose));
    const createDate = ref([]);
    const pagination = computed(() => ({
      total: formState.total,
      current: searchParamsState.pageIndex,
      pageSize: searchParamsState.pageSize,
      showSizeChanger: true,
      showTotal: (total) => getPaginationTotalTitle(total),
    }));
    const importOptions = ref<ImportProps>({
      title: '导入',
      url: '/jobUserService/jobUser/importJobUser',
      columns: [
        {cnName: '姓名', enName: 'name', width: 100},
        {cnName: '出生日期', enName: 'birthDayStr', width: 100},
        {cnName: '性别', enName: 'sexName', width: 80},
        {cnName: '重点人员类别', enName: 'keyPersonTypeName', width: 150},
        {cnName: '就业状态', enName: 'jobStatusName', width: 100},
        {cnName: '所属驿站', enName: 'siteName', width: 100},
        {cnName: '地址', enName: 'address', width: 150},
        {cnName: '联系电话', enName: 'userMobile', width: 120},
        {cnName: '市/县', enName: 'regionName', width: 100},
        {cnName: '身份证号码', enName: 'identityNumber', width: 180},
        {cnName: '镇街', enName: 'streetName', width: 100},
        {cnName: '省份', enName: 'provinceName', width: 100},
        {cnName: '联系人', enName: 'userName', width: 100},
        {cnName: '毕业日期', enName: 'finishDate', width: 100},
        {cnName: '籍贯', enName: 'nativePlace', width: 100},
        {cnName: '社会保障卡号', enName: 'socialSecurityCard', width: 150},
        {cnName: '毕业院校', enName: 'finishSchool', width: 150},
        {cnName: '专业', enName: 'profession', width: 150},
        {cnName: '是否全日制', enName: 'isFullTimeName', width: 100},
        {cnName: '职业资格类别', enName: 'occupationalCategoryName', width: 150},
        {cnName: '职业资格等级', enName: 'occupationalLevelName', width: 200},
        {cnName: '民族', enName: 'nationName', width: 80},
        {cnName: '政治面貌', enName: 'politicsStatusName', width: 100},
        {cnName: '出生地', enName: 'birthPlace', width: 100},
        {cnName: '户口性质', enName: 'familyNatureName', width: 100},
        {cnName: '最高学历', enName: 'cultureRankName', width: 100},
        {cnName: '健康状况', enName: 'healthName', width: 100},
        {cnName: '婚姻状况', enName: 'bloodTypeName', width: 100},
        {cnName: '电子邮箱', enName: 'email', width: 100},
        {cnName: '身高(cm)', enName: 'height', width: 100},
        {cnName: '体重(kg)', enName: 'weight', width: 100},
        {cnName: '视力', enName: 'vision', width: 100},
        {cnName: '家庭住址', enName: 'familyAddress', width: 150},
        {cnName: '个人评价', enName: 'hobby', width: 180},
        {cnName: '个人技能', enName: 'personalSkills', width: 180},
      ],
      template: {
        tempFileName: '求职人员导入模板.xlsx',
        url: '',
        params: null,
      },
    });
    const dataList = ref([]);
    const siteList = ref<any>([]);
    const jobStatusList = ref<SelectProps['options']>();
    const sexTypeList = ref<SelectProps['options']>();
    const educationTypeList = ref<SelectProps['options']>();
    const emphasisTypeList = ref<SelectProps['options']>();
    const regionList = ref<SelectProps['options']>();

    const onSelectChange = (selectedRowKeys: any) => {
      formState.selectedRowKeys = selectedRowKeys;
    };

    const handleTableChange: TableProps['onChange'] = (pag: {
      pageSize: number;
      current: number;
    }) => {
      searchParamsState.pageIndex = pag.current;
      searchParamsState.pageSize = pag.pageSize;
      loadData();
    };

    const onFinish = () => {
      loadData();
    };

    get('system/area/getCityList', {}).then(data => {
      regionList.value = data;
    });

    const loadData = async function () {
      formState.loading = true;
      getAllSites();
      const result: any = await getList(searchParamsState);
      dataList.value = result.list;
      //console.log(dataList.value);
      formState.total = result.total;
      formState.loading = false;
      // console.log("userParams",searchParamsState);
    };


    const getAllSites = () => {
      getSiteList({pageIndex:1,pageSize:9999}).then((result :any) => {
        siteList.value = result.list;
      })
    }

    const getJobStatusList = () => {
      getSysDictionaryList('jobStatus').then((data) => {
        jobStatusList.value = data;
      });
    };

    const getSexList = () => {
      getSysDictionaryList('Gender').then((data) => {
        sexTypeList.value = data;
      });
    };

    const getEducationList = () => {
      getSysDictionaryList('HighestDegree').then((data) => {
        educationTypeList.value = data;
      });
    };

    const getEmphasisTypeList = () => {
      getSysDictionaryList('KeyPersonType').then((data) => {
        emphasisTypeList.value = data;
      });
    };

    const tabsViewStore = useTabsViewStore();
    const onAdd = () => {
      tabsViewStore.addTabByPath('/jobusermgr/jobseeker/add', null);
    };
    const onEdit = (item: any) => {
      tabsViewStore.addTabByPath('/jobusermgr/jobseeker/edit', {id: item.jobUserID});
    };
    const onVitae = (item: any) => {
      tabsViewStore.addTabByPath('/jobusermgr/jobseeker/vitae', {id: item.jobUserID});
    };

    const onDel = (item: any) => {
      formState.selectedRowKeys = [];
      if (item) {
        formState.selectedRowKeys.push(item.jobUserID as never)
      }

      if (formState.selectedRowKeys.length <= 0) {
        message.warning('请选择需要删除的数据！');
        return false;
      }
      Modal.confirm({
        title: '确认删除选择的求职人员？',
        icon: createVNode(ExclamationCircleOutlined),
        content: '',
        okText: '确认删除',
        okType: 'danger',
        okButtonProps: {},
        cancelText: '取消',
        onOk() {
          del(formState.selectedRowKeys).then(() => {
            loadData();
          });
        },
        onCancel() {
          formState.selectedRowKeys = [];
        },
      })
    }

    // 登记时间组件调整
    const onCreateTimeChange = (dateString) => {
      createDate.value = dateString;
      searchParamsState.startDate = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParamsState.endDate = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }

    // 字段展示列选择完毕
    function columnsCheckSub(columnsKeys: Array<string>) {
      // 从原始表格定义数据中过滤出已选择的字段
      columns.value = originalColumns.filter((item: any) => columnsKeys.includes(item.key));
    }

    // 开启服务跟进记录对话框
    function openServiceModal(jobUser: any) {
      serviceTableComRef.value.show(JSON.parse(JSON.stringify(jobUser)));
    }

    // 开启认领求职人员对话框
    function openClaimModal() {
      claimJobUserTableComRef.value.show();
    }

    return {
      modalRoleUserRef,
      modalRoleEditRef,
      formRef,
      loadData,
      onAdd,
      openClaimModal,
      onDel,
      onEdit,
      searchParamsState,
      formState,
      columns,
      originalColumns,
      columnsCheckSub,
      pagination,
      importOptions,
      dataList,
      siteList,
      jobStatusList,
      sexTypeList,
      educationTypeList,
      emphasisTypeList,
      onSelectChange,
      handleTableChange,
      onFinish,
      getJobStatusList,
      getSexList,
      getEducationList,
      getEmphasisTypeList,
      onVitae,
      expand,
      exportSearchParams,
      regionList,
      createDate,
      onCreateTimeChange,
      serviceTableComRef,
      claimJobUserTableComRef,
      openServiceModal,
    };
  },
  created() {
    this.loadData();
    this.getJobStatusList();
    this.getSexList();
    this.getEducationList();
    this.getEmphasisTypeList();
  },
  activated() {
    if (history.state.params?.reload) this.loadData();
  },
});
</script>

<style lang="less" scoped></style>
