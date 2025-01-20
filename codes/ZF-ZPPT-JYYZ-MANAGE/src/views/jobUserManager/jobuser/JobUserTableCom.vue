<template>
  <a-modal
    :width="1100"
    v-model:visible="visible"
    title="已录入企业信息"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
    ok-text="确认"
    cancel-text="取消"
    :keyboard="false"
    :mask-closable="false"
  >
    <div class="card-search">
      <a-form
        ref="formRef"
        name="advanced_search"
        class="ant-advanced-search-form"
        :model="searchParams"
      >
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item label="姓名" :label-col="{ span: 8 }" name="name">
              <a-input v-model:value="searchParams.name" style="color: black;" placeholder=""/>
            </a-form-item>
          </a-col>
          <a-col :span="4" style="text-align: left">
            <a-button type="primary" html-type="submit" @click="onSearch">查询</a-button>
            <a-button
              style="margin: 0 8px"
              @click="
              () => {
                searchParams.pageIndex = 1;
                searchParams.pageSize = 10;
                searchParams.name = '';
                loadData();
              }
            ">重置
            </a-button>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-table :columns="columns" :data-source="dataList" :scroll="{ x:'100%', y: 500 }" :pagination="pagination"
             :loading="formState.loading"
             @change="handleTableChange"
             bordered>
    </a-table>
  </a-modal>
</template>

<script setup lang="ts">
import {computed, reactive, ref} from "vue";
import type {TableColumnsType, TableProps} from "ant-design-vue";
import {getPaginationTotalTitle} from "@/utils/common";
import {getList} from "@/api/jobUserManager/jobuser";

// 对话框显示隐藏开关
const visible = ref(false);
// 加载动画开关
const confirmLoading = ref(false);
// 企业数据
const dataList = ref<Array<any>>();
// 企业信息表格数据定义
const columns: TableColumnsType = [
  {
    title: '序号',
    align: 'center',
    width: 80,
    key: 'jobUserID',
    customRender: (item) =>
      `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`,
  },
  {title: '姓名', dataIndex: 'name', key: 'name', width: 100, align: "center"},
  {title: '公民身份号码', dataIndex: 'identityNumber', key: 'identityNumber', width: 170, align: "center"},
  {title: '性别', dataIndex: 'genderName', key: 'genderName', width: 80, align: "center"},
  {title: '联系电话', dataIndex: 'userMobile', key: 'userMobile', align: "center"},
  {
    title: '年龄', dataIndex: 'age', key: 'age', align: "center", customRender: item => {
      return calculateAge(item.record.identityNumber);
    }
  },
  {title: '文化程度', dataIndex: 'cultureName', key: 'cultureName', align: "center"},
  {title: '重点人员类别', dataIndex: 'keyTypeName', key: 'keyTypeName', align: "center"},
];
const searchParams = reactive({
  pageIndex: 1,
  pageSize: 20,
  name: '',
  createUserId: "",
  workTime: ""
});
const pagination = computed(() => ({
  total: formState.total,
  current: searchParams.pageIndex,
  pageSize: searchParams.pageSize,
  showSizeChanger: true,
  showTotal: total => getPaginationTotalTitle(total)
}));
const formState = reactive({
  total: 0,
  loading: false
});


// 对话框确定事件
function handleOk() {
  visible.value = false
}

// 显示
function show(record: any) {
  console.log(record)
  visible.value = true;
  searchParams.createUserId = record.userId;
  searchParams.workTime = record.workTime;
  loadData()
}

// 获取数据
async function loadData() {
  formState.loading = true;
  await getList(searchParams).then((result: any) => {
    dataList.value = result.list;
    formState.total = result.total;
  }).finally(() => {
    formState.loading = false;
  })
}

// 表格状态变更事件
const handleTableChange: TableProps['onChange'] = (pag: { pageSize: number; current: number },) => {
  searchParams.pageIndex = pag.current;
  searchParams.pageSize = pag.pageSize;
  loadData();
};

// 计算年龄
function calculateAge(idCardNumber: string) {
  // 假设身份证号中出生日期的格式为YYYY-MM-DD
  const birthYear = parseInt(idCardNumber.substring(6, 10), 10);
  const birthMonth = parseInt(idCardNumber.substring(10, 12), 10);
  const birthDay = parseInt(idCardNumber.substring(12, 14), 10);
  // 获取当前日期
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth() + 1; // 月份从 0 开始
  const currentDay = currentDate.getDate();
  // 计算年龄
  let age = currentYear - birthYear;
  if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDay < birthDay)) {
    age--; // 生日还未过，减去一岁
  }
  return age;
}

// 表单查询事件
function onSearch() {
  loadData()
}

defineExpose({
  show
})
</script>

<style scoped>

</style>
