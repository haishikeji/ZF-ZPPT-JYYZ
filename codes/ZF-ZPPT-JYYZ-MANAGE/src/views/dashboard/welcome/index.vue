<template>
  <div class="card-search">
    <a-row :gutter="24">
      <a-col :span="16">
        <a-card title="通知公告" style="height: 290px;">
          <a-list item-layout="horizontal" :data-source="announcementList" :split="false">
            <template #renderItem="{ item,index }">
              <a-list-item style="padding: 7px 0;">
                <div style="margin-bottom: 4px;">
                  <a-badge color="#000000d9"/>
                </div>
                <a-list-item-meta>
                  <template #title>
                    <p style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;color:#1890ff;cursor:pointer;"
                       @click="annVisible=true;annContent=item.content;annTitle=item.title;">{{
                        item.title
                      }}
                      <CrownOutlined v-if="item.recordStatus==2" :style="{fontSize: '16px', color: 'hotpink'}"/>
                    </p>
                  </template>
                </a-list-item-meta>
                <div style="padding-left: 20px;margin-bottom: 4px;">{{ dayjs(item.createTime).format("YYYY-MM-DD") }}
                </div>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="快捷导航" style="height: 290px;">
          <div style="display: flex;flex-wrap: wrap;text-align: center;">
            <div v-for="item in shortcutMenuList" @click="onShortcutMenuClick(item)"
                 style="flex: 0 0 33%;display: flex;align-items: center;flex-direction: column;cursor: pointer;margin: 10px 0;">
              <div><img :src="require('@/assets/shortcutMenu/'+item.shortcutIcon)" style="width: 50px;height: 50px;"/>
              </div>
              <div><span>{{ item.menuName }}</span></div>
            </div>
            <div v-if="shortcutMenuList.length==0">暂无</div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <br/>
    <a-row :gutter="24">
      <a-col :span="24">
        <a-card title="待办信息">
          <a-table :columns="columns" :data-source="taskList" :pagination="pagination"
                   @change="handleTableChange"
                   :row-key="record=>record.taskID"
                   bordered>
            <template #bodyCell="{ column, text, record }">
              <template v-if="column.key === 'title'">
                <div>
                  <a-button type="link" size="small" @click="onTaskClick(record)">{{ record.title }}</a-button>
                </div>
              </template>
            </template>
          </a-table>
        </a-card>
      </a-col>
    </a-row>
    <a-modal v-model:visible="annVisible" :title="annTitle" @ok="annVisible=false" :footer="null" :style="{width:'70%'}" >
      <p v-html="annContent"></p>
    </a-modal>
  </div>

</template>

<script lang="ts">
import {computed, defineComponent, reactive, ref, toRefs} from 'vue';
import type {TableColumnsType, TableProps} from "ant-design-vue";
import {getTaskList} from "@/api/system/approve";
import {useTabsViewStore} from "@/store/modules/tabsView";
import {getUserAnnouncementList} from "@/api/system/announcement";
import dayjs from 'dayjs';
import {getUserShortcutMenuList} from "@/api/system/user";
import {CrownOutlined} from '@ant-design/icons-vue';

export default defineComponent({
  name: 'Home',
  setup() {
    const tabsViewStore = useTabsViewStore();

    const searchParamsState = reactive({
      page: 1,
      rows: 10
    });
    const formState = reactive({
      total: 0,
      loading: false
    });
    const columns: TableColumnsType = [
      {
        title: '序号',
        align: 'center',
        key: 'practicePositionChangeID', fixed: 'left',
        customRender: item => `${searchParamsState.rows * (searchParamsState.page - 1) + item.index + 1}`,
        width: 80
      },
      {title: '标题', dataIndex: 'title', key: 'title'},
      {title: '发送人', dataIndex: 'createUserName', key: 'createUserName', width: 150},
      {
        title: '发送时间',
        dataIndex: 'createTime',
        key: 'createTime',
        customRender: ({record}) => dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss'),
        width: 200
      }
    ];
    const pagination = computed(() => ({
      total: formState.total,
      current: searchParamsState.page,
      pageSize: searchParamsState.rows
    }));
    const taskList = ref([]);
    const announcementList = ref([]);
    const shortcutMenuList = ref([]);
    const announcement = reactive({annContent: '', annTitle: ''});
    const annVisible = ref<boolean>(false);

    const onTaskClick = (record) => {
      tabsViewStore.addTabByPath(record.url, {approveKeyID: record.keyID});
    };

    const onShortcutMenuClick = (item) => {
      tabsViewStore.addTabByPath(item.url, {});
    };
    const handleTableChange: TableProps['onChange'] = (
      pag: { pageSize: number; current: number },
    ) => {
      searchParamsState.page = pag.current
      loadTaskList();
    };
    const loadTaskList = () => {
      getTaskList(searchParamsState.page, searchParamsState.rows).then(data => {
        taskList.value = data.list;
        formState.total = data.total;
      });
    };

    const loadUserAnnouncementList = () => {
      getUserAnnouncementList({page: 1, rows: 5}).then((res: any) => {
        announcementList.value = res.list;
      });
    }

    const loadUserShortcutMenuList = () => {
      getUserShortcutMenuList({}).then((res: any) => {
        shortcutMenuList.value = res;
      });
    }

    return {
      ...toRefs(announcement),
      CrownOutlined,
      columns,
      formState,
      handleTableChange,
      pagination,
      dayjs,
      taskList, annVisible,
      shortcutMenuList,
      announcementList,
      loadTaskList,
      loadUserAnnouncementList,
      loadUserShortcutMenuList,
      onTaskClick,
      onShortcutMenuClick,
    };
  },
  created() {
    this.loadTaskList();
    this.loadUserAnnouncementList();
    this.loadUserShortcutMenuList();
  },
  activated() {

  }
});
</script>

<style lang="less" scoped>
</style>
