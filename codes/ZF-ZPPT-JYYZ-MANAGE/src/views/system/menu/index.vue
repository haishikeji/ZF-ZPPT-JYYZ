<template>
  <div class="card-search">
    <a-form
      ref="formRef"
      name="advanced_search"
      class="ant-advanced-search-form"
      :model="formState"
      @finish="onFinish"
    >
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item
            name="menuName"
            label="菜单名称"
            :label-col="{span:6}">
            <a-input v-model:value="formState.menuName" style="width: 200px"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item
            name="menuNo"
            label="菜单编号"
            :label-col="{span:6}">
            <a-input v-model:value="formState.menuNo" style="width: 200px"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="6" style="text-align: left">
          <a-button type="primary" html-type="submit" @click="onFinish">查询</a-button>
          <a-button style="margin: 0 8px" @click="() => {formRef.resetFields();loadData()}">重置</a-button>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24" style="text-align: right">
          <a-button type="primary" style="margin: 0 8px" html-type="button" @click="edit(null)" functioncode="T0199010201">新增</a-button>
          <a-button type="primary" style="margin: 0 8px" html-type="button" @click="setJobUserDes()" >转换身份证&手机号</a-button>
        </a-col>
      </a-row>
    </a-form>
    <div class="search-result-list">
      <a-table :columns="columns" :data-source="data" :pagination="false"
               :row-key="record=>record.menuNo"
               bordered>
        <template #bodyCell="{ column,record }">
          <template v-if="column.key === 'operation'">
            <a-button type="link" size="small" @click="edit(record.menuNo)" functioncode="T0199010202">编辑
            </a-button>
            <a-popconfirm placement="leftTop"
              title="是否删除数据?"
              @confirm="onDelete(record.menuNo)">
              <a-button type="link" size="small" functioncode="T0199010203">删除</a-button>
            </a-popconfirm>
          </template>
        </template>
      </a-table>
    </div>
    <menuEditModal ref="modalRef" @modalClosed="dicModalClosed"></menuEditModal>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref, defineComponent, computed, watch, onActivated} from 'vue';
import type {FormInstance} from 'ant-design-vue';
import type {TableColumnsType, TableProps} from 'ant-design-vue';
import {del, get, postdel} from '@/api/common';
import {useRoute, useRouter} from "vue-router";
import { message} from "ant-design-vue";
import menuEditModal from './edit.vue'

const route = useRoute();
const router = useRouter();
const expand = ref(false);
const formRef = ref<FormInstance>();
const selectedRowKeys = ref([]);
const modalRef = ref();
const data =ref();

const formState = reactive({
  parentMenuNo: '',
  menuNo: '',
  menuName: '',
});

const columns: TableColumnsType = [
  {title: '菜单名称', width: 250, dataIndex: 'menuName', key: 'menuName',},
  {title: '菜单编号', dataIndex: 'menuNo', key: 'menuNo', width: 100},
  {title: '菜单图标', dataIndex: 'icon', key: 'icon', width: 150, align: "center"},
  {title: '是否显示', dataIndex: 'isVisible', key: 'isVisible', align: "center", width: 100,customRender:({record})=>record.isVisible?"是":"否"},
  {title: '排序', dataIndex: 'orderNo', key: 'orderNo', align: "center", width: 100},
  {
    title: '操作', key: 'operation', width: 100, align: "center"
  },
];

const loading = ref(false);


const onFinish = (values: any) => {
  loadData();
}

const loadData = async function () {
  loading.value = true;
  const result: any = await get('system/menu/getMenuTree', formState);

  data.value = result;
  loading.value = false;
}
const onDelete = (menuNo) => {
  del('system/menu/deleteMenu', {menuNo:menuNo}).then(x => {
    loadData();
  })
};
const dicModalClosed = (d) => {
  if (d) {
    loadData();
  }
}
const edit = (key: string) => {
  modalRef.value.show(key);
};
const onSelectChange = (keys: any) => {
  selectedRowKeys.value = keys;
};

const setJobUserDes = async function () {
  get('jobUserService/jobUser/setJobUserDes', {}).then((result: any)=>{
    message.info("转换了"+result+"处数据")
  });
}

loadData();
</script>
