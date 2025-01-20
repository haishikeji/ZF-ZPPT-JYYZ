<template>
  <ion-button style="color: #02a6f1;font-size: 15px;" fill="clear" @click="onOpen()">选择
  </ion-button>
  <ion-page>
    <ion-content class="ion-padding">
      <ion-modal :is-open="isOpen" @willPresent="onRest()">
        <ion-header class="header-theme2">
          <ion-toolbar>
            <ion-buttons slot="start">
              <ion-icon :icon="arrowBackOutline" @click="onOpen()"></ion-icon>
            </ion-buttons>
            <ion-title>岗位名称</ion-title>
            <ion-buttons slot="end">
              <ion-button fill="clear" @click="onConfirm()">确定</ion-button>
            </ion-buttons>
          </ion-toolbar>
        </ion-header>
        <ion-content class="ion-padding">
          <ion-item class="search-item" style="--border-style: unset; --inner-padding-end: 0px;--padding-start: 0px;">
            <ion-input placeholder="请输入岗位名称" class="custom"
                       v-model="searchParams.professionName"
                       @input="reload()" :clear-on-edit="true"
                       style="border: 1px solid #f2f2f5;border-radius: 14px;--padding-start: 10px;height: 35px;">
              <ion-icon slot="start" :icon="searchOutline" aria-hidden="true"></ion-icon>
            </ion-input>
          </ion-item>
          <div class="bw-vue-list">
            <div class="list-content">
              <ion-list>
                <ion-item v-for="(record,key) in dataList" :key="key">
                  <ion-label @click="onSelect(record)">
                    <span v-html="formatStr(record.professionName)"></span>({{ record.parentProfessionName }})
                  </ion-label>
                </ion-item>
              </ion-list>
              <b-empty v-if="dataList.length<=0" :loading="loading"/>
              <ion-infinite-scroll threshold="100px" @ionInfinite="onScroll($event)">
                <ion-infinite-scroll-content
                    :loadingText="pagination.total>pagination.current*pagination.pageSize?'正在加载...':'暂无更多'"
                    loadingSpinner="bubbles">
                </ion-infinite-scroll-content>
              </ion-infinite-scroll>
            </div>
          </div>
        </ion-content>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">

import {computed, defineComponent, reactive, ref} from 'vue';
import {arrowBackOutline, searchOutline} from 'ionicons/icons';
import {IonIcon} from '@ionic/vue';
import BEmpty from "@/components/empty.vue";
import {getProfessionLikeList} from "@/api/recommendmgt";

export default defineComponent({
  name: 'postSelectLike',
  components: {IonIcon, BEmpty},
  props: {},
  setup(props, context) {
    const total = ref(20);
    const loading = ref(true);
    const pagination = computed(() => ({
      total: total,
      current: searchParams.pageIndex,
      pageSize: searchParams.pageSize
    }));
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 10,
      professionName: '',
    });
    const isOpen = ref(false);
    const dataList = ref<any>([]);
    const resultInfo = reactive({value: '', text: ''})

    const loadData = async function () {
      loading.value = true;
      getProfessionLikeList(searchParams).then(data => {
        dataList.value = dataList.value.concat(data.list);
        total.value = data.total;
        loading.value = false;
      })
    }

    const reloadBool = ref(true);
    const reload = () => {
      if(searchParams.professionName==''){
        dataList.value = [];
        return;
      }

      setTimeout(()=>{
        searchParams.pageIndex = 1;
        if (reloadBool.value == false) {
          return;
        }

        reloadBool.value = false;
        loading.value = true;
        getProfessionLikeList(searchParams).then(data => {
          dataList.value = data.list;
          total.value = data.total;
          reloadBool.value = true;
          loading.value = false;
        })
      },1000)
    }

    const onScroll = (e: any) => {
      setTimeout(() => {
        e.target.complete();
        if (pagination.value.total.value > pagination.value.current * pagination.value.pageSize) {
          searchParams.pageIndex += 1;
          loadData();
        }
      }, 500);
    }

    const onConfirm = () => {
      if (searchParams.professionName) {
        resultInfo.text = searchParams.professionName;
        context.emit("resultInfo", resultInfo);
      }
      onOpen();
    }

    const onSelect = (item: any) => {
      resultInfo.text = item.professionName;
      resultInfo.value = item.professionID;
      context.emit("resultInfo", resultInfo);
      onOpen();
    }

    const onOpen = () => {
      isOpen.value = !isOpen.value;
    }

    const onRest = () => {
      dataList.value = [];
      searchParams.professionName = '';
      searchParams.pageIndex = 1;
      resultInfo.text = '';
      resultInfo.value = '';
    }

    const formatStr = (str: any) => {
      if (!str) {
        return "";
      }

      return str.replace(searchParams.professionName, '<span style="color: coral">' + searchParams.professionName + '</span>');
    }

    return {
      searchOutline,
      arrowBackOutline,
      total,
      loading,
      isOpen,
      dataList,
      pagination,
      searchParams,
      onConfirm,
      onSelect,
      onOpen,
      onScroll,
      loadData,
      reload,
      onRest,
      formatStr
    }
  }
});
</script>