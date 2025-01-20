<template>
  <div class="card-search">
    <a-form ref="formRef" :model="searchParams" class="ant-advanced-search-form" name="advanced_search">
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="姓名" name="userName">
            <a-input v-model:value="searchParams.userName" :allow-clear="true" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="备忘日期" name="workDate">
            <a-range-picker v-model:value="workDate" :placeholder="['开始日期', '结束日期']"
                            format="YYYY-MM-DD" @change="onRangeChange"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="备忘类型" name="doTypeID">
            <a-select ref="select"
                      v-model:value="searchParams.doTypeID" :allow-clear="true"
                      :field-names="{ label: 'name', value: 'value' }" :options="logTypeList" @change="onSearch">
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6" style="text-align: left">
          <a-button html-type="submit" type="primary" @click="onSearch">查询</a-button>
          <a-button style="margin: 0 8px" @click="formRef.resetFields();onReset();">重置</a-button>
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
      <a-row v-show="expand" :gutter="24">
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="所属县区" name="regionCode">
            <a-select
              ref="select"
              v-model:value="searchParams.regionCode"
              :allow-clear="true"
              :field-names="{ label: 'name', value: 'code' }"
              :options="regionList"
              @change="changeRegion"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="所属街道" name="streetCode">
            <a-select
              ref="select"
              v-model:value="searchParams.streetCode"
              :allow-clear="true"
              :field-names="{ label: 'name', value: 'code' }"
              :options="streetList"
              @change="onSearch"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="所属驿站" name="streetCode">
            <a-select
              ref="select"
              v-model:value="searchParams.siteID"
              :options="siteList"
              :field-names="{ label: 'siteName', value: 'siteID' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row v-show="expand" :gutter="24">
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="登记时间" name="regionCode">
            <a-range-picker v-model:value="createDate" :placeholder="['开始日期', '结束日期']" format="YYYY-MM-DD"
                            @change="onCreateTimeChange"/>
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
            <a-button functioncode="T01040202" html-type="submit" type="primary" @click='onAdd'>新增</a-button>
            <BExportExcel :filename="'备忘信息'"
                          :params="{...exportSearchParams, rows:100000,doWorkIDList:formState.selectedRowKeys.join(',')}"
                          :title="'导出'"
                          :url="'taskAndLog/doWork/export'"></BExportExcel>
          </div>
        </a-col>
      </a-row>
    </a-form>
    <div class="search-result-list">
      <a-table :columns="columns" :data-source="dataList" :loading="formState.loading" :pagination="pagination"
               :row-key="record=>record.doWorkID"
               :row-selection="{ selectedRowKeys: formState.selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x:'100%', y: 500 }"
               bordered
               @change="handleTableChange">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'RecordsCompanyNum'">
            <div>
              <a-button type="link" size="small" @click='onRecordsCompanyInfo(record)'>{{
                  record.RecordsCompanyNum
                }}
              </a-button>
            </div>
          </template>
          <template v-if="column.key === 'RecordsJobuserNum'">
            <div >
              <a-button type="link" size="small" @click='onRecordsJobUserInfo(record)'>{{
                  record.RecordsJobuserNum
                }}
              </a-button>
            </div>
          </template>
          <template v-if="column.key === 'operation'">
            <div class="table-operation">
              <a-button functioncode="T01040201" size="small" type="link" @click='onDetail(record.doWorkID)'>查看
              </a-button>
              <a-button functioncode="T01040203" size="small" type="link" @click='onEdit(record.doWorkID)'>编辑
              </a-button>
              <a-button functioncode="T01040204" size="small" type="link" @click="onDel(record)">删除</a-button>
            </div>
          </template>
        </template>
      </a-table>

      <!-- 企业数据表格对话框 -->
      <CompanyTableCom ref="companyTableComRef"></CompanyTableCom>
      <!-- 求职人员数据表格对话框 -->
      <JobUserTableCom ref="jobUserTableComRef"></JobUserTableCom>
    </div>
  </div>
</template>

<script lang="ts">
import {computed, createVNode, defineComponent, reactive, ref} from 'vue';
import {useTabsViewStore} from "@/store/modules/tabsView";
import {DownOutlined, ExclamationCircleOutlined, UpOutlined} from '@ant-design/icons-vue';
import type {FormInstance, SelectProps, TableProps} from 'ant-design-vue';
import {message, Modal} from "ant-design-vue";
import {delWorkLog, getList} from '@/api/taskAndLog/dowork';
import {getRegionCodeList, getStreetCodeList} from '@/api/system/area/index';
import {getSysDictionaryList} from "@/api/system/dictionary";
import BExportExcel from "@/components/basic/excel/exportExcel/exportExcel.vue";
import {getPaginationTotalTitle} from "@/utils/common";
import dayjs from 'dayjs';
import CompanyTableCom from "@/views/companyService/company/CompanyTableCom.vue";
import JobUserTableCom from "@/views/jobUserManager/jobuser/JobUserTableCom.vue";
import {getSiteList} from "@/api/baseSettings/siteInfo";
import ColumnsSetting from "@/components/common/ColumnsSetting.vue";

export default defineComponent({
  name: 'doTaskList',
  components: {ColumnsSetting, JobUserTableCom, CompanyTableCom, DownOutlined, UpOutlined, BExportExcel},
  setup() {
    const formRef = ref<FormInstance>();
    const tabsViewStore = useTabsViewStore();
    const expand = ref(false);
    const workDate = ref([]);
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 20,
      userName: '',
      workStartDate: '',
      workEndDate: '',
      doTypeID: null,
      regionCode: '',
      streetCode: '',
      siteID: "",
      startDate: "",
      endDate: ''
    });
    // 导出Excel查询参数
    const exportSearchParams = computed(() => {
      let data = JSON.parse(JSON.stringify(searchParams));
      data.pageSize = formState.total;
      return data;
    })
    const formState = reactive({
      total: 0,
      selectedRowKeys: [],
      loading: false
    });
    // 原始表格定义数据
    const originalColumns = [
      {
        title: '序号',
        align: "center",
        key: 'LogId',
        customRender: item => `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`,
        isDisabled: true
      },
      {title: '姓名', dataIndex: 'userName', key: 'userName', align: "center"},
      {
        title: '工作备忘日期', dataIndex: 'workTime', key: 'workTime', width: 120, align: "center",
        customRender: (item) => {
          return item.record.workTime == null ? "" : (dayjs(item.record.workTime).format('YYYY-MM-DD'))
        },
      },
      {title: '备忘类型', dataIndex: 'logTypeName', key: 'logTypeName', align: "center"},
      {title: '工作备忘情况', dataIndex: 'workContent', key: 'workContent', align: "center"},
      {title: '所属驿站', dataIndex: 'siteName', key: 'siteName', align: "center"},
      {title: '所属县区', dataIndex: 'regionName', key: 'regionName', align: "center"},
      {title: '所属街道', dataIndex: 'streetName', key: 'streetName', align: "center", isDefaultClose: true},
      {
        title: '走访企业数量',
        dataIndex: 'RecordsCompanyNum',
        key: 'RecordsCompanyNum',
        align: "center",
        isDefaultClose: true
      },
      {
        title: '登记就业人员人数',
        dataIndex: 'RecordsJobuserNum',
        key: 'RecordsJobuserNum',
        align: "center",
        isDefaultClose: true
      },
      {title: '操作', key: 'operation', fixed: 'right', width: 150, align: "center", isDisabled: true},
    ];
    // 响应式表格定义
    const columns = ref<Array<any>>(originalColumns.filter(item => !item.isDefaultClose));
    const pagination = computed(() => ({
      total: formState.total,
      current: searchParams.pageIndex,
      pageSize: searchParams.pageSize,
      showSizeChanger: true,
      showTotal: total => getPaginationTotalTitle(total)
    }));

    const regionList = ref<SelectProps['options']>();
    const streetList = ref<SelectProps['options']>();
    const logTypeList = ref<SelectProps['options']>();
    const siteList = ref<any>([]);
    const dataList = ref([]);
    const createDate = ref([]);
    // 企业数据表格对话框
    const companyTableComRef = ref();
    // 就业人员表格对话框
    const jobUserTableComRef = ref();

    const getLogTypeList = async function () {
      const logTypeResult: any = await getSysDictionaryList("LogType");
      logTypeList.value = logTypeResult;
    }

    const getRegionList = async function () {
      const regionResult: any = await getRegionCodeList();
      // console.log('regionResult',regionResult);
      regionList.value = regionResult;
    }
    const getAllSites = () => {
      getSiteList({pageIndex: 1, pageSize: 9999}).then((result: any) => {
        siteList.value = result.list;
      })
    }
    const changeRegion = async function () {
      if (searchParams.regionCode) {
        const streetResult: any = await getStreetCodeList(searchParams.regionCode);
        // console.log('streetResult',streetResult);
        streetList.value = streetResult;
      }
      onSearch();
    }

    const loadData = async function () {
      formState.loading = true;
      await getLogTypeList();
      await getRegionList();
      const result: any = await getList(searchParams);
      dataList.value = result.list;
      // console.log("dataList",dataList.value);
      formState.total = result.total;
      formState.loading = false;
    }

    const handleTableChange: TableProps['onChange'] = (pag: { pageSize: number; current: number },) => {
      searchParams.pageIndex = pag.current;
      searchParams.pageSize = pag.pageSize;
      loadData();
    };

    const onSelectChange = (selectedRowKeys: any) => {
      formState.selectedRowKeys = selectedRowKeys;
    };

    const onRangeChange = (dateString) => {
      workDate.value = dateString;
      searchParams.workStartDate = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParams.workEndDate = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }


    const onSearch = () => {
      loadData();
    }

    const onDel = (item: any) => {
      formState.selectedRowKeys = [];
      if (item) {
        formState.selectedRowKeys.push(item.doWorkID as never)
      }

      if (formState.selectedRowKeys.length <= 0) {
        message.warning('请选择需要删除的数据！');
        return false;
      }

      Modal.confirm({
        title: '确认删除选中的备忘信息?',
        icon: createVNode(ExclamationCircleOutlined),
        content: '',
        okText: '确认删除',
        okType: 'danger',
        okButtonProps: {},
        cancelText: '取消',
        onOk() {
          delWorkLog(formState.selectedRowKeys).then(() => {
            loadData();
          });
        },
        onCancel() {
          formState.selectedRowKeys = [];
        },
      });

    };

    const onAdd = () => {
      tabsViewStore.addTabByPath('/workLog/add', {id: null});
    };

    const onEdit = (id: string) => {
      tabsViewStore.addTabByPath('/workLog/edit', {id: id});
    };

    const onDetail = (id: string) => {
      tabsViewStore.addTabByPath('/workLog/detail', {id: id});
    }

    const onReset = () => {
      workDate.value = [];
      searchParams.workStartDate = '';
      searchParams.workEndDate = '';
      loadData();
    };

    // 开启录入企业信息弹窗
    function onRecordsCompanyInfo(record: any) {
      if (record.RecordsCompanyNum == 0) {
        return;
      }
      console.log(companyTableComRef)
      companyTableComRef.value.show(record)
    }

    // 开启录入求职人员信息弹窗
    function onRecordsJobUserInfo(record: any) {
      if (record.RecordsJobuserNum == 0) {
        return;
      }
      console.log(jobUserTableComRef)
      jobUserTableComRef.value.show(record)
    }

    // 登记时间组件调整
    const onCreateTimeChange = (dateString) => {
      createDate.value = dateString;
      searchParams.startDate = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParams.endDate = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }

    // 字段展示列选择完毕
    function columnsCheckSub(columnsKeys: Array<string>) {
      // 从原始表格定义数据中过滤出已选择的字段
      columns.value = originalColumns.filter((item: any) => columnsKeys.includes(item.key));
    }

    return {
      formRef,
      expand,
      workDate,
      logTypeList,
      regionList,
      streetList,
      searchParams,
      formState,
      originalColumns,
      columnsCheckSub,
      columns,
      pagination,
      dataList,
      handleTableChange,
      onSelectChange,
      onSearch,
      onAdd,
      onEdit,
      onDel,
      onReset,
      onRangeChange,
      loadData,
      changeRegion,
      dayjs,
      onDetail,
      onRecordsCompanyInfo,
      onRecordsJobUserInfo,
      companyTableComRef,
      jobUserTableComRef,
      exportSearchParams,
      siteList,
      getAllSites,
      createDate,
      onCreateTimeChange
    };
  },
  created() {
    this.loadData();
    this.getAllSites();
  },
  activated() {
    if (history.state.params?.reload)
      this.loadData();
  }
});
</script>
<style lang="less" scoped></style>
