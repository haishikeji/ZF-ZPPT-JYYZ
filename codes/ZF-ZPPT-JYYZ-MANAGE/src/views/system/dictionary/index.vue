<template>
  <DynamicTable
    ref="dynamicTableRef"
    header-title="字典配置"
    title-tooltip=""
    :data-request="getSysDictionaryList"
    :columns="columns"
  />
</template>

<script lang="tsx" setup>
import { Tag } from 'ant-design-vue';
import type { TableColumn } from '@/components/core/dynamic-table';
import { useTable } from '@/components/core/dynamic-table';
import { useSocket } from '@/core/socket/useSocket';
import { getSysDictionaryList } from '@/api/system/dictionary'

defineOptions({
  name: 'SystemDictionary',
});

const [DynamicTable, dynamicTableInstance] = useTable();

const columns: TableColumn[] = [
  {
    title: '字典序号',
    dataIndex: 'dickey',
    width: 150,
    align: 'center',
  },
  {
    title: '字典值',
    dataIndex: 'dicvalue',
    width: 150,
    align: 'center',
  },
  {
    title: '字典关健字',
    dataIndex: 'dictypekey',
    align: 'center',
  },
  {
    title: '字典类型',
    dataIndex: 'dictypeName',
    width: 150,
    align: 'center',
    hideInSearch: true,
  },
  {
    title: '操作',
    dataIndex: 'ACTION',
    align: 'center',
    actions: ({ record }) => [
      {
        label: '删除',
        // auth: 'sys.online.kick',
        // disabled: record.disable,
        popConfirm: {
          title: '确定删除该用户吗?',
          onConfirm: () => {},
        },
      },
    ]
  },
];


</script>

<style scoped>

</style>
