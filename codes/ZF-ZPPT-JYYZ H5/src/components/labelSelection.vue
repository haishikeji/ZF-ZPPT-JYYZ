<template>
  <ion-button style="color: #02a6f1;font-size: 15px;" fill="clear" @click="onOpen()">选择标签
  </ion-button>
  <ion-page>
    <ion-content class="ion-padding">
      <ion-modal :is-open="searchParams.isOpen"
                 @willDismiss="onWillDismiss()" @willPresent="onWillPresent()">
        <ion-header class="header-theme2">
          <ion-toolbar>
            <ion-buttons slot="start">
              <ion-icon :icon="arrowBackOutline" @click="onClose()"></ion-icon>
            </ion-buttons>
            <ion-title>{{ searchParams.title }}</ion-title>
            <ion-buttons slot="end">
              <ion-button fill="clear" @click="onConfirm()">确定</ion-button>
            </ion-buttons>
          </ion-toolbar>
        </ion-header>
        <ion-content class="ion-padding">
          <div class="bw-vue-list">
            <div class="list-content">
              <ion-item style="margin: 0px 0px;--padding-start:0px;--inner-padding-end:0px;">
                <ion-label>
                  <div class="multi-title">
                    <h2 style="font-weight:600;">基础标签</h2>
                  </div>
                  <p>
                    <ion-grid>
                      <ion-row>
                        <ion-col size="auto" v-for="(record,key) in basicsLabelList" :key="key">
                          <ion-button size="small" :color="record.checked?'primary':'light'"
                                      @click="record.checked=!record.checked">{{ record.labelName }}
                          </ion-button>
                        </ion-col>
                      </ion-row>
                    </ion-grid>
                  </p>
                </ion-label>
              </ion-item>
              <ion-item style="margin: 0px 0px;--padding-start:0px;--inner-padding-end:0px;">
                <ion-label>
                  <div class="multi-title">
                    <h2 style="font-weight:600;">自定义标签</h2>
                  </div>
                  <p>
                    <ion-grid>
                      <ion-row>
                        <ion-col size="auto" v-for="(record,key) in customLabelList" :key="key">
                          <ion-button size="small" :color="record.checked?'primary':'light'"
                                      @click="record.checked=!record.checked">{{ record.labelName }}
                          </ion-button>
                        </ion-col>
                      </ion-row>
                    </ion-grid>
                  </p>
                </ion-label>
              </ion-item>
            </div>
          </div>
        </ion-content>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">

import {defineComponent, nextTick, reactive, ref} from 'vue';
import {arrowBackOutline, searchOutline} from 'ionicons/icons';
import {IonIcon} from '@ionic/vue';
import {getLabelList} from "@/api/label";

export default defineComponent({
  // name: 'labelSelection',
  components: {IonIcon},
  props: {
    listLabel: {
      type: Array,
      default: null
    },
    bigType: {
      type: Number,
      default: null
    },
    title: {
      type: String,
      default: null
    }
  },
  emits: ["result-back"],
  setup(props, {emit}) {

    const loading = ref(true);
    const isOpen = ref(false);
    const basicsLabelList = ref<any>([]);
    const customLabelList = ref<any>([]);
    const searchParams = reactive({
      pageIndex: 1, pageSize: 9999, bigType: null,title:'',isOpen:false
    });

    const loadData = async function () {
      searchParams.title = props.title;
      searchParams.bigType = props.bigType as any;
      loading.value = true;

      getLabelList(searchParams).then((result: any) => {
        result.list.forEach((x: any) => {
          if (props.listLabel) {
            x.checked = props.listLabel.findIndex((f: any) => f.labelID == x.labelID) >= 0;
          }
        })
        basicsLabelList.value = result.list.filter((x: any)=>x.labelType === 1);
        customLabelList.value = result.list.filter((x: any)=>x.labelType === 2);
      })
      loading.value = false;
    }

    const onOpen = () => {
      searchParams.isOpen = true;
    }

    const onClose = () => {
      searchParams.isOpen = false;
    }

    const onConfirm = () => {
      searchParams.isOpen = false;
      const selects = basicsLabelList.value.filter((x: any) => x.checked == true)
          .concat(customLabelList.value.filter((x: any) => x.checked == true));
      emit("result-back", selects);
    }

    const onWillPresent = ()=>{
      loadData();
    }

    const onWillDismiss = ()=>{
      basicsLabelList.value = [];
      customLabelList.value = [];
      searchParams.title='';
      searchParams.bigType=null;
    }

    return {
      searchOutline,
      arrowBackOutline,
      loading,
      isOpen,
      searchParams,
      basicsLabelList,
      customLabelList,
      onOpen,
      onClose,
      onConfirm,
      loadData,
      onWillPresent
      ,onWillDismiss
    }
  }
});
</script>