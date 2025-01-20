<template>
  <div class="card-search">
    <!-- 查询表单 -->
    <a-form
      ref="formRef"
      name="advanced_search"
      class="ant-advanced-search-form"
      :model="searchParams"
    >
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item label="查询年份" :label-col="{span:6}" name="reportDate">
            <a-date-picker format="YYYY" picker="year" v-model:value="pickerDate" :allow-clear="false"
                           @change="pickerDateChange"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="运营机构" :label-col="{span:6}" name="institutionID">
            <a-select
              ref="select"
              v-model:value="searchParams.institutionID"
              :options="allInstitutions"
              :field-names="{ label: 'text', value: 'value' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="县区" :label-col="{span:6}" name="regionCode">
            <a-select
              ref="select"
              v-model:value="searchParams.regionCode"
              :options="regionList"
              :field-names="{ label: 'name', value: 'code' }"
              :allow-clear="true"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6" style="text-align: left">
          <a-button type="primary" html-type="submit" @click="loadData" :loading="tableLoading">查询</a-button>
          <a-button style="margin: 0 8px" @click="onReset">重置</a-button>
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
          <a-form-item label="驿站" :label-col="{span:6}" name="siteID">
            <a-select
              ref="select"
              v-model:value="searchParams.siteID"
              :options="allSites"
              @change="loadData"
              :field-names="{ label: 'siteName', value: 'siteID' }"
              :allow-clear="true"
            >
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <!-- 操作按钮 -->
    <a-row class="edit-operation" style="margin-bottom: 20px">
      <a-col :span="24" class="flex-space-between">
        <div>
          <a-radio-group v-model:value="dataType" button-style="solid" @change="dataTypeChange">
            <a-radio-button value="table">表格</a-radio-button>
            <a-radio-button value="imageTable">图表</a-radio-button>
          </a-radio-group>
        </div>
        <div>
          <BExportExcel :filename="searchParams.year + '系统使用情况统计'"
                        :params="{...exportSearchParams}"
                        :title="'导出'"
                        :url="'statistics/export/yearSystemApplyCount'"></BExportExcel>
        </div>
      </a-col>
    </a-row>
    <!-- 数据展示 -->
    <div v-show="dataType == 'table'">
      <a-table :columns="originalColumns" :data-source="systemApplyCount" :scroll="{ x:'1300' }"
               :loading="tableLoading"
               :pagination="false"
               bordered>
      </a-table>
    </div>
    <div v-show="dataType == 'imageTable'" class="image-table-box">
      <div id="companyImageTable" class="echarts-main"></div>
      <div id="postImageTable" class="echarts-main"></div>
      <div id="jobUserImageTable" class="echarts-main"></div>
      <div id="yearCountImageTable" class="echarts-main"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {getMonthSystemDataCount, getYearSystemDataCount} from "@/api/statistics";
import dayjs from "dayjs";
import BExportExcel from "@/components/basic/excel/exportExcel/exportExcel.vue";
import {initBarImageTable, initLineImageTable} from "@/utils/echartsUtil";
import {getInstitutionDataList} from "@/api/baseSettings/institution";
import {getRegionCodeList} from "@/api/system/area/index";
import type {SelectProps} from "ant-design-vue";
import {DownOutlined, UpOutlined} from "@ant-design/icons-vue";
import {getSiteList} from "@/api/baseSettings/siteInfo";

const expand = ref(false);

// 查询条件
const searchParams = reactive({
  year: "",
  institutionID: "",
  regionCode: "",
  siteID: ""
})
// 导出Excel查询参数
const exportSearchParams = computed(() => {
  return JSON.parse(JSON.stringify(searchParams));
})
const pickerDate = ref<any>(dayjs());
// 表格加载
const tableLoading = ref(false);
// 展示类型切换
const dataType = ref("table");
// 统计数据
const systemApplyCount = ref<Array<any>>([]);
// 表格定义
const originalColumns = ref<Array<any>>([
  {
    title: '项目', dataIndex: 'regionName', key: 'regionName', align: "center", width: 200, fixed: 'left'
  },
  {
    title: '汇总',
    children: [
      {title: '驿站人员数量', dataIndex: 'siteUserCount', key: 'siteUserCount', width: 120, align: "center"},
      {title: '录入企业数量', dataIndex: 'companyCount', key: 'companyCount', width: 120, align: "center"},
      {title: '收集岗位条数', dataIndex: 'postCount', key: 'postCount', width: 120, align: "center"},
      {title: '收集岗位个数', dataIndex: 'postRecruitCount', key: 'postRecruitCount', width: 120, align: "center"},
      {title: '登记求职人数', dataIndex: 'jobUserCount', key: 'jobUserCount', width: 120, align: "center"},
    ]
  },
  {
    title: '1月',
    children: [
      {title: '录入企业数量', dataIndex: 'companyCount', key: 'companyCount', width: 120, align: "center"},
      {title: '收集岗位条数', dataIndex: 'postCount', key: 'postCount', width: 120, align: "center"},
      {title: '收集岗位个数', dataIndex: 'postRecruitCount', key: 'postRecruitCount', width: 120, align: "center"},
      {title: '登记求职人数', dataIndex: 'jobUserCount', key: 'jobUserCount', width: 120, align: "center"},
    ]
  }
])
// 查询方法未处理的初始数据
const searchOriginalList = ref();
// 运营机构
const allInstitutions = ref<any>([]);
// 所属区县
const regionList = ref<SelectProps['options']>();
// 驿站
const allSites = ref<any>([]);

// 数据加载
function loadData() {
  tableLoading.value = true;
  getYearSystemDataCount(searchParams).then((result: any) => {
    searchOriginalList.value = result;
    if (dataType.value == 'table') {
      analysisByTable();
    }
    if (dataType.value == 'imageTable') {
      analysisByImageTable();
    }
  }).finally(() => {
    tableLoading.value = false;
  })
}

// 解析生成表格内容
function analysisByTable() {
  let arr = new Array<any>();
  // 获取到区县名称信息
  if (searchOriginalList.value && Object.keys(searchOriginalList.value).length > 0) {
    // 获取到查询结果Map中的第一个键值对，解析获取到县区信息，填充进初始数组中
    searchOriginalList.value[Object.keys(searchOriginalList.value)[0]].forEach((item: any) => {
      arr.push({
        regionCode: item.regionCode,
        regionName: item.regionName
      });
    });
  }
  // 初始化表结构
  originalColumns.value = [{
    title: '项目', dataIndex: 'regionName', key: 'regionName', align: "center", width: 200, fixed: 'left'
  }]

  // 解析汇总数据
  initTableData(searchOriginalList.value, '汇总', arr, 'hz', true);
  // 解析第一周到第五周的统计数据
  for (let i = 1; i <= 12; i++) {
    // 填充数据
    initTableData(searchOriginalList.value, i + '月', arr, i + 'z', false);
  }
  systemApplyCount.value = arr;
}

function analysisByImageTable() {
  let regionNameList = new Array<any>();
  let yearNameList = new Array<any>();

  // 获取到区县名称信息
  if (searchOriginalList.value && Object.keys(searchOriginalList.value).length > 0) {
    // 获取到查询结果Map中的第一个键值对，解析获取到县区信息，填充进初始数组中
    searchOriginalList.value[Object.keys(searchOriginalList.value)[0]].forEach((item: any) => {
      if (item.regionCode != 100) {
        regionNameList.push(item.regionName);
      }
    });
  }
  // 初始化折线图数据
  let yearLineData = [
    {
      name: '驿站人员数量',
      type: 'line',
      data: new Array<any>()
    },
    {
      name: '录入企业数量',
      type: 'line',
      data: new Array<any>()
    },
    {
      name: '收集岗位条数',
      type: 'line',
      data: new Array<any>()
    },
    {
      name: '收集岗位个数',
      type: 'line',
      data: new Array<any>()
    },
    {
      name: '登记求职人数',
      type: 'line',
      data: new Array<any>()
    },
  ]

  // 解析1到12月的统计数据
  for (let i = 1; i <= 12; i++) {
    if (searchOriginalList.value[i + '月']) {
      yearNameList.push(i + '月');

      // 查询获取到每周的合计总数，填充进折线图数据中
      const hjData = searchOriginalList.value[i + '月'].find(item => item.regionCode == '100');
      if (hjData) {
        yearLineData[0].data.push(hjData.siteUserCount);
        yearLineData[1].data.push(hjData.companyCount);
        yearLineData[2].data.push(hjData.postCount);
        yearLineData[3].data.push(hjData.postRecruitCount);
        yearLineData[4].data.push(hjData.jobUserCount);
      }
    }
  }

  // 收集图表数据
  let companyImageTableData = new Array<any>();
  let postImageTableData = new Array<any>();
  let jobUserImageTableData = new Array<any>();
  searchOriginalList.value['汇总'].forEach((item: any) => {
    if (item.regionCode != 100) {
      companyImageTableData.push(item.companyCount)
      postImageTableData.push(item.postCount)
      jobUserImageTableData.push(item.jobUserCount)
    }
  })

  // 初始化图表
  initBarImageTable(regionNameList, companyImageTableData, [], "companyImageTable", '录入企业数量');
  initBarImageTable(regionNameList, postImageTableData, [], "postImageTable", "收集岗位条数");
  initBarImageTable(regionNameList, jobUserImageTableData, [], "jobUserImageTable", "登记求职人数");
  initLineImageTable(yearNameList, yearLineData, ["驿站人员数量", "录入企业数量", "收集岗位条数", "收集岗位个数", "登记求职人数"], "yearCountImageTable", "全市就业驿站运行概括");
}

// 查询重置
function onReset() {
  searchParams.year = dayjs().format("YYYY");
  pickerDate.value = dayjs();
  searchParams.regionCode = ''
  searchParams.institutionID = ''
  searchParams.siteID = ''
  loadData()
}

// 日期变更事件
function pickerDateChange(date: any, dateStr: string) {
  console.log(date);
  searchParams.year = dateStr;
  loadData();
}

// 表格数据与结构定义初始化
function initTableData(result: any, resultKey: any, arr: any, arrNewKey: any, isShowSiteUser: boolean) {
  if (!result[resultKey]) {
    return;
  }
  // 填充数据
  result[resultKey].forEach((item: any) => {
    let arrItem = arr.find((arrElement: any) => arrElement.regionCode === item.regionCode);
    if (arrItem) {
      // 将item的其他字段重命名，然后将其赋值给arr中对应的元素
      Object.keys(item).forEach(key => {
        if (key !== 'regionCode' && key !== 'regionName') {
          arrItem[key + arrNewKey] = item[key];
        }
      });
    }
  })
  let table = {
    title: resultKey,
    children: [
      {
        title: '录入企业数量',
        dataIndex: 'companyCount' + arrNewKey,
        key: 'companyCount' + arrNewKey,
        width: 120,
        align: "center"
      },
      {
        title: '收集岗位条数',
        dataIndex: 'postCount' + arrNewKey,
        key: 'postCount' + arrNewKey,
        width: 120,
        align: "center"
      },
      {
        title: '收集岗位个数',
        dataIndex: 'postRecruitCount' + arrNewKey,
        key: 'postRecruitCount' + arrNewKey,
        width: 120,
        align: "center"
      },
      {
        title: '登记求职人数',
        dataIndex: 'jobUserCount' + arrNewKey,
        key: 'jobUserCount' + arrNewKey,
        width: 120,
        align: "center"
      },
    ]
  }
  // 按要求进行展示驿站人员数量
  if (isShowSiteUser) {
    table.children.unshift({
      title: '驿站人员数量',
      dataIndex: 'siteUserCount' + arrNewKey,
      key: 'siteUserCount' + arrNewKey,
      width: 120,
      align: "center"
    },)
  }
  // 填充表结构
  originalColumns.value.push(table)
}

function dataTypeChange() {
  if (dataType.value == 'table') {
    analysisByTable();
  }
  if (dataType.value == 'imageTable') {
    setTimeout(() => {
      analysisByImageTable();
    }, 100);
  }
}

const getAllInstitutions = async function () {
  allInstitutions.value = await getInstitutionDataList();
}

const getRegionList = async function () {
  regionList.value = await getRegionCodeList();
}

const getAllSites = () => {
  getSiteList({
    pageIndex: 1,
    pageSize: 9999
  }).then((result: any) => {
    allSites.value = result.list;
  })
}

onMounted(() => {
  searchParams.year = dayjs().format("YYYY");
  loadData();
  getAllInstitutions();
  getRegionList();
  getAllSites();
})
</script>

<script lang="ts">
// 设置页面名称进行组件缓存
export default {
  name: "YearSystemApplyDataCount",
}
</script>

<style lang="less" scoped>
.image-table-box {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;

  .echarts-main {
    height: 280px;
  }
}
</style>
