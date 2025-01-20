<template>
  <ion-page>
    <ion-content class="tab-work-content">
      <div class="work-toolbar">
        <div class="work-body">
          <div class="info-title">
            <h3>工作台</h3>
          </div>
        </div>
      </div>
      <ion-segment :value="type" mode="md" @ionChange="typeChange">
        <ion-segment-button value="1">
          <ion-label>待办</ion-label>
        </ion-segment-button>
        <ion-segment-button value="2">
          <ion-label>通知</ion-label>
        </ion-segment-button>
      </ion-segment>
      <ion-list v-if="type==1">
        <ion-item-sliding v-for="(record,key) in taskList" :key="key">
          <ion-item detail @click="onTaskClick(record)">
            <ion-label>
              <div> ●<span style="padding-left: 3px;">{{ record.title }}</span></div>
              <p>推送时间：{{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm') }}</p>
            </ion-label>
          </ion-item>
        </ion-item-sliding>
      </ion-list>
      <ion-list v-if="type==2">
        <ion-item-sliding v-for="(record,key) in taskList" :key="key">
          <ion-item @click="visible=true;annContent=record.content;annTitle=record.title;">
            <ion-label>
              <div>
                ●<span style="padding-left: 3px;padding-right: 5px;">{{ record.title }}</span>
                <ion-icon v-if="record.recordStatus==2" color="primary" :icon="flowerOutline"></ion-icon>
              </div>
              <p>
                有效时间：{{ dayjs(record.startTime).format('YYYY-MM-DD') }}至{{ dayjs(record.endTime).format('YYYY-MM-DD') }}</p>
            </ion-label>
          </ion-item>
        </ion-item-sliding>
      </ion-list>
      <div v-show="taskList.length==0" class="grid-content" style="padding:10px 10px">
        <ion-text color="tertiary">
          <h6>查询无数据</h6>
        </ion-text>
      </div>
      <ion-infinite-scroll threshold="100px" @ionInfinite="onScroll($event)">
        <ion-infinite-scroll-content
            :loadingText="total>searchParamsState.page*searchParamsState.rows?'正在加载...':'暂无更多'"
            loadingSpinner="bubbles">
        </ion-infinite-scroll-content>
      </ion-infinite-scroll>
      <ion-loading :is-open="isOpen" message="Loading..." duration="3000"></ion-loading>
    </ion-content>
    <ion-modal ref="modal" :is-open="visible" @ionModalDidDismiss="visible=false"
               :initial-breakpoint="0.6"
               :breakpoints="[0, 0.3, 0.5, 0.75]"
               handle-behavior="cycle">
      <ion-header>
        <ion-toolbar>
          <ion-title>{{ annTitle }}</ion-title>
          <ion-buttons slot="end">
            <ion-button :strong="true" @click="visible=false">关闭</ion-button>
          </ion-buttons>
        </ion-toolbar>
      </ion-header>
      <ion-content class="ion-padding">
        <div class="detail-content">
          <p v-html="annContent"></p>
        </div>
      </ion-content>
    </ion-modal>
  </ion-page>
</template>

<script lang="ts">
import {ref, reactive, toRefs, defineComponent} from "vue";
import BEmpty from "@/components/empty.vue"
import {getTaskList} from "@/api/approve";
import {useRouter} from "vue-router";
import {onIonViewDidEnter} from "@ionic/vue";
import dayjs from "dayjs";
import {get} from "@/api/common";
import {flowerOutline} from "ionicons/icons";

export default defineComponent({
  name: 'tabMain',
  setup() {

    const type = ref(1);
    const router = useRouter();
    const total = ref(15);
    const isOpen = ref(false);
    const announcement = reactive({visible: false, annContent: '', annTitle: ''});

    const typeChange = (event: any) => {
      type.value = parseInt(event.detail.value);
      taskList.value = [];
      searchParamsState.page = 1;
      if (type.value == 1) {
        loadTaskList();
      } else {
        loadNoticeList();
      }
    }
    const searchParamsState = reactive({page: 1, rows: 10, recordStatus: null})

    const taskList = ref([]);

    const loadTaskList = () => {
      isOpen.value = true;
      getTaskList(searchParamsState.page, searchParamsState.rows).then(data => {
        taskList.value = taskList.value.concat(data.list);
        total.value = data.total;
        isOpen.value = false;
      });
    };
    const loadNoticeList = () => {
      isOpen.value = true;
      get('system/announcement/getUserAnnouncementList', {
        page: searchParamsState.page,
        rows: searchParamsState.rows
      }).then((data: any) => {
        taskList.value = taskList.value.concat(data.list);
        total.value = data.total;
        isOpen.value = false;
      });
    };
    const reload = () => {
      taskList.value = [];
      searchParamsState.page = 1;
      loadTaskList();
    }
    const map: Map<string, string> = new Map<string, string>([
      ['/attendance/makeup/detail', 'tattendancemakeupDetail'],
      ['/attendance/timeoff/detail', 'tattendancetimeoffDetail'],
      ['/practice/change/detail', 'tpracticechangeDetail'],
      ['/practiceescape/detail', 'tpracticeescapeDetail'],
      ['/selfcontact/manage/detail', 'tselfcontactDetail'],
      ['/practicemakeup/detail', 'tpracticemakeupDetail'],
    ]);
    const onTaskClick = (record: any) => {
      router.push({name: map.get(record.url), query: {id: record.keyID, isApprove: 1}});
    };
    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (total.value > searchParamsState.page * searchParamsState.rows) {
          searchParamsState.page += 1;
          loadTaskList();
        }
      }, 500);
    }
    onIonViewDidEnter(() => {
      reload();
    });
    return {
      ...toRefs(announcement),
      confirm,
      reload,
      taskList,
      onScroll, isOpen,
      router, dayjs,
      searchParamsState,
      total, type,
      loadTaskList,
      typeChange, onTaskClick, BEmpty,flowerOutline
    }
  }
});
</script>

<style lang="less">
.tab-work-content {
  --background: #ffffff !important;

  .info-title {
    text-align: center;
  }

  ion-router-link {
    color: #1890ff;
  }

  ion-item {
    div {
      white-space: normal;
      word-break: break-all;
      font-size: 14px;
    }

    p {
      margin-top: 10px;
      font-size: 10px;
    }
  }
}

</style>
