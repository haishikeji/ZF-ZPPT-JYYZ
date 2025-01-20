<template>
  <div class="card-search">
    <a-form ref="formRef" :model="searchParams" class="ant-advanced-search-form" name="advanced_search">
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="任务名称" name="name">
            <a-input v-model:value="searchParams.name" placeholder=""/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="发布时间" name="orderDate">
            <a-range-picker v-model:value="orderDate" :placeholder="['开始日期', '结束日期']" format="YYYY-MM-DD"
                            @change="onRangeChange"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :label-col="{span:6}" label="所属县区" name="regionCode">
            <a-select
              ref="select"
              v-model:value="searchParams.regionCode"
              :allow-clear="true"
              :field-names="{ label: 'name', value: 'code' }"
              :options="regionList"
              @change="changeCity"
            >
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
          <a-form-item :label-col="{span:6}" label="任务类型" name="workTypeId">
            <a-select
              ref="select"
              v-model:value="searchParams.workTypeId"
              :allow-clear="true"
              :field-names="{ label: 'name', value: 'value' }"
              :options="taskTypeList"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
        </a-col>
        <a-col :span="6">
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
            <a-button functioncode="T01040102" html-type="submit" type="primary" @click='onAdd'>新增</a-button>
            <BExportExcel :filename="'任务信息'"
                          :params="{...exportSearchParams, isExport: true, rows:10000,doTaskIDList:formState.selectedRowKeys.join(',')}"
                          :title="'导出'"
                          :url="'taskAndLog/doTask/export'"></BExportExcel>
          </div>
        </a-col>
      </a-row>
    </a-form>
    <div class="search-result-list">
      <a-table :columns="columns" :data-source="dataList" :loading="formState.loading" :pagination="pagination"
               :row-key="(record)=>record.doTaskID"
               :row-selection="{ selectedRowKeys: formState.selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x:'100%', y: 500 }"
               bordered
               @change="handleTableChange">
        <template #bodyCell="{ column, text, record }">
          <template v-if="column.key === 'completeProgress'">
            <div :style="{ color: getProgressColor(computeProgress(record)) }">
              {{ computeProgress(record) }}%
            </div>
          </template>
          <template v-if="column.key === 'operation'">
            <div class="table-operation">
              <a-button functioncode="T01040101" size="small" type="link" @click='onDetail(record)'>查看</a-button>
              <a-button functioncode="T01040103" size="small" type="link" @click='onEdit(record)'>编辑</a-button>
              <a-button functioncode="T01040104" size="small" type="link" @click="onDel(record)">删除</a-button>
            </div>
          </template>
        </template>
      </a-table>
    </div>
  </div>
</template>

<script lang="ts">
import {get} from '@/api/common';
import {computed, createVNode, defineComponent, reactive, ref} from 'vue';
import {DownOutlined, ExclamationCircleOutlined, UpOutlined} from '@ant-design/icons-vue';
import type {FormInstance, SelectProps, TableProps} from 'ant-design-vue';
import {message, Modal} from "ant-design-vue";
import {del, getList} from '@/api/taskAndLog/dotask';
import BExportExcel from "@/components/basic/excel/exportExcel/exportExcel.vue";
import {getPaginationTotalTitle} from "@/utils/common";
import dayjs from 'dayjs';
import {useTabsViewStore} from "@/store/modules/tabsView";
import BImportExcel from "@/components/basic/excel/importExcel/importExcel.vue";
import ColumnsSetting from "@/components/common/ColumnsSetting.vue";


export default defineComponent({
  name: 'doTaskList',
  components: {ColumnsSetting, BImportExcel, DownOutlined, UpOutlined, BExportExcel},
  setup() {
    const formRef = ref<FormInstance>();
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 20,
      recordStatus: 1,
      startTime: '',
      endTime: '',
      finishStartTime: '',
      finishEndTime: '',
      workTypeId: '',
      regionCode: '',
      streetCode: '',
      startDate: '',
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
        width: 120,
        align: "center",
        key: 'institutionID',
        customRender: item => `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`,
        isDisabled: true
      },
      {title: '任务名称', dataIndex: 'doTaskName', key: 'doTaskName', align: "center"},
      {title: '任务类型', dataIndex: 'workTypeName', key: 'workTypeName', width: 120, align: "center"},
      {title: '发布县区', dataIndex: 'regionName', key: 'regionName', align: "center"},
      {
        title: '完成进度', dataIndex: 'completeProgress', key: 'completeProgress', width: 120, align: "center"
      },
      {
        title: '发布时间', dataIndex: 'createTime', key: 'createTime', align: "center", customRender: (item) => {
          return item.record.createTime == null ? "" : (dayjs(item.record.createTime).format('YYYY-MM-DD'))
        }
      },
      {title: '发布人', dataIndex: 'createName', key: 'createName', align: "center", isDefaultClose: false},
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

    const tabsViewStore = useTabsViewStore();
    const regionList = ref<SelectProps['options']>();
    const streetList = ref<SelectProps['options']>();
    const taskTypeList = ref<SelectProps['options']>();
    const orderDate = ref([]);
    const finishDate = ref([]);
    const createDate = ref([]);
    const dataList = ref([]);
    const expand = ref(false);

    const onAdd = () => {
      tabsViewStore.addTabByPath('/workTask/add', null);
    };
    const onEdit = (item: any) => {
      tabsViewStore.addTabByPath('/workTask/edit', {id: item.doTaskID});
    };
    const onDetail = (item: any) => {
      tabsViewStore.addTabByPath('/workTask/detail', {id: item.doTaskID});
    };

    get('system/area/getCityList', {}).then(data => {
      // console.log(data);
      regionList.value = data;
      // console.log(regionList.value);
    });

    get('system/dictionary/getDictionaryItemByCodeList', {code: 'TaskType'}).then(data => {
      // console.log(data);
      taskTypeList.value = data;
    });

    const changeCity = () => {
      if (searchParams.regionCode) {
        get('system/area/getAreaList', {code: searchParams.regionCode}).then(data => {
          streetList.value = data;
        })
      }
      loadData();
    }

    const loadData = async function () {
      formState.loading = true;
      const result: any = await getList(searchParams);
      dataList.value = result.list;
      // console.log(dataList.value);
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
      orderDate.value = dateString;
      searchParams.startTime = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParams.endTime = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }

    const onFinishChange = (dateString) => {
      finishDate.value = dateString;
      searchParams.finishStartTime = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParams.finishEndTime = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }

    const onCreateTimeChange = (dateString) => {
      createDate.value = dateString;
      searchParams.startDate = dateString ? dateString[0].format("YYYY-MM-DD") : '';
      searchParams.endDate = dateString ? dateString[1].format("YYYY-MM-DD") : '';
      loadData();
    }

    const onReset = () => {
      orderDate.value = [];
      finishDate.value = [];
      searchParams.startTime = '';
      searchParams.endTime = '';
      searchParams.finishStartTime = '';
      searchParams.finishEndTime = '';
      loadData();
    };


    const onSearch = () => {
      loadData();
    }

    const onDel = (item: any) => {
      formState.selectedRowKeys = [];
      if (item) {
        formState.selectedRowKeys.push(item.doTaskID as never)
      }

      if (formState.selectedRowKeys.length <= 0) {
        message.warning('请选择需要删除的数据！');
        return false;
      }
      Modal.confirm({
        title: '确认删除选中的任务信息?',
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
      });

    };

    // 字段展示列选择完毕
    function columnsCheckSub(columnsKeys: Array<string>) {
      // 从原始表格定义数据中过滤出已选择的字段
      columns.value = originalColumns.filter((item: any) => columnsKeys.includes(item.key));
    }

    // 计算完成度
    function computeProgress(record: any) {
      if (record.completeCount == 0) {
        return 0;
      }
      let num: number = record.completeCount / record.userCount;
      return (Number(num) * 100).toFixed(2);
    }

    // 按进度显示时间
    function getProgressColor(progress: any) {
      if (progress < 30) {
        return 'red';
      } else if (progress >= 30 && progress < 80) {
        return 'yellow';
      } else {
        return 'green';
      }
    }

    return {
      expand,
      orderDate,
      finishDate,
      regionList,
      streetList,
      taskTypeList,
      formRef,
      searchParams,
      formState,
      columns,
      originalColumns,
      columnsCheckSub,
      pagination,
      dataList,
      handleTableChange,
      onDel,
      onReset,
      onSearch,
      onSelectChange,
      onRangeChange,
      onFinishChange,
      changeCity,
      loadData,
      onAdd,
      onEdit,
      dayjs,
      onDetail,
      exportSearchParams,
      onCreateTimeChange,
      createDate,
      computeProgress,
      getProgressColor
    };
  },
  created() {
    this.loadData();
  },
  activated() {
    if (history.state.params?.reload)
      this.loadData();
  }
});
</script>
<style lang="less" scoped></style>
