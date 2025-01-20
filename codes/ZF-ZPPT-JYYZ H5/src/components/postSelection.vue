<template>
  <ion-button style="color: #02a6f1;font-size: 15px;" fill="clear" @click="loadProfessionInfo()">选择
  </ion-button>
  <ion-page >
    <ion-content class="ion-padding">
      <ion-modal class="cascade-model" :backdrop-dismiss="false" @willPresent="onRest()" :is-open="isOpen" :initial-breakpoint="1"
                 :breakpoints="[0, 1]">
        <ion-toolbar>
          <ion-item>
            <ion-buttons slot="start">
              <ion-button fill="clear" @click="onCancel()">取消</ion-button>
            </ion-buttons>
            <ion-label style="font-size: 12px;white-space: pre-wrap; word-wrap: break-word;">
              {{ selectProfessionInfo.text }}
            </ion-label>
            <ion-buttons slot="end">
              <ion-button fill="clear" :disabled="selectProfessionInfo.value==''" @click="onConfirm()">确定</ion-button>
            </ion-buttons>
          </ion-item>
          <ion-item>
            <ion-segment value="0">
              <ion-segment-button v-for="(item,key) in selectModelList" :key="key" @click="onSelectType(item.index)"
                                  :disabled="item.disabled" :value="item.index">
                <ion-label :style="{color:item.isSelect?'#1a65eb':'#000000'}">{{ item.name }}</ion-label>
              </ion-segment-button>
            </ion-segment>
          </ion-item>
        </ion-toolbar>
        <div class="bw-vue-list">
          <div class="list-content">
            <ion-list class="custom-scroll" v-show="selectModelList[selectType.one].isSelect">
              <ion-item v-for="(item, index) in selectModelList[selectType.one].professionList" :key="index">
                <ion-label :style="{color:item.isSelect?'#1a65eb':'#000000'}"
                           @click="onSelect(item,selectType.one)">
                  {{ item.professionName }}
                </ion-label>
              </ion-item>
            </ion-list>
            <ion-list class="custom-scroll" v-show="selectModelList[selectType.two].isSelect">
              <ion-item v-for="(item, index) in selectModelList[selectType.two].professionList" :key="index">
                <ion-label :style="{color:item.isSelect?'#1a65eb':'#000000'}"
                           @click="onSelect(item,selectType.two)">
                  {{ item.professionName }}
                </ion-label>
              </ion-item>
            </ion-list>
            <ion-list class="custom-scroll" v-show="selectModelList[selectType.three].isSelect">
              <ion-item v-for="(item, index) in selectModelList[selectType.three].professionList" :key="index">
                <ion-label :style="{color:item.isSelect?'#1a65eb':'#000000'}"
                           @click="onSelect(item,selectType.three)">
                  {{ item.professionName }}
                </ion-label>
              </ion-item>
            </ion-list>
          </div>
        </div>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import {defineComponent, reactive, ref} from 'vue';
import {getProfessionLevelList} from "@/api/recommendmgt";

export default defineComponent({
  name: "postSelection",
  props: {
    ProfessionID: {type: String, default: ''},
  },
  setup(props, context) {
    const selectType = {
      one: 0,
      two: 1,
      three: 2
    }
    const isOpen = ref(false);
    const selectModelList = reactive([
      {
        index: 0,
        name: '分类一',
        isSelect: false,
        disabled: true,
        professionName: '',
        professionID: '',
        professionList: [] as any
      },
      {
        index: 1,
        name: '分类二',
        isSelect: false,
        disabled: true,
        professionName: '',
        professionID: '',
        professionList: [] as any
      },
      {
        index: 2,
        name: '岗位',
        isSelect: false,
        disabled: true,
        professionName: '',
        professionID: '',
        professionList: [] as any
      }]);
    const selectProfessionInfo = reactive({value: '', text: ''})
    const professionLevelList = ref();

    const loadProfessionInfo = () => {
      getProfessionLevelList().then(data => {
        professionLevelList.value = data;

        if (!props.ProfessionID) {
          selectModelList[selectType.one].isSelect = true;
          selectModelList[selectType.one].disabled = false;
          selectModelList[selectType.one].professionName = '';
          selectModelList[selectType.one].professionID = '';
          selectModelList[selectType.one].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "1")
              .map((x: any) => ({
                ...x,
                isSelect: false
              }));
        } else {

          const threeInfo = professionLevelList.value.find((x: any) => x.professionLevel == "3" && x.professionID == props.ProfessionID);
          const twoInfo = professionLevelList.value.find((x: any) => x.professionLevel == "2" && x.professionID == threeInfo.parentProfessionID);
          const oneInfo = professionLevelList.value.find((x: any) => x.professionLevel == "1" && x.professionID == twoInfo.parentProfessionID);

          selectModelList[selectType.one].isSelect = false;
          selectModelList[selectType.one].disabled = false;
          selectModelList[selectType.one].professionName = oneInfo.professionName;
          selectModelList[selectType.one].professionID = oneInfo.professionID;
          selectModelList[selectType.one].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "1")
              .map((x: any) => ({
                ...x,
                isSelect: false
              }));
          selectModelList[selectType.one].professionList.find((x: any) => x.professionID == oneInfo.professionID).isSelect = true;

          selectModelList[selectType.two].isSelect = false;
          selectModelList[selectType.two].disabled = false;
          selectModelList[selectType.two].professionName = twoInfo.professionName;
          selectModelList[selectType.two].professionID = twoInfo.professionID;
          selectModelList[selectType.two].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "2"
              && x.parentProfessionID == oneInfo.professionID).map((x: any) => ({
            ...x,
            isSelect: false
          }));
          selectModelList[selectType.two].professionList.find((x: any) => x.professionID == twoInfo.professionID).isSelect = true;

          selectModelList[selectType.three].isSelect = true;
          selectModelList[selectType.three].disabled = false;
          selectModelList[selectType.three].professionName = threeInfo.professionName;
          selectModelList[selectType.three].professionID = threeInfo.professionID;
          selectModelList[selectType.three].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "3"
              && x.parentProfessionID == twoInfo.professionID).map((x: any) => ({
            ...x,
            isSelect: false
          }));
          selectModelList[selectType.three].professionList.find((x: any) => x.professionID == threeInfo.professionID).isSelect = true;

          getSelectProsession();
        }
      });
      isOpen.value = true;
    }

    const onSelectType = (type: number) => {
      if (selectType.one == type) {
        selectModelList.map(x => {
          x.isSelect = false,
          x.disabled = true,
          x.professionName = '',
          x.professionID = '',
          x.professionList = []
        });

        selectModelList[selectType.one].isSelect = true;
        selectModelList[selectType.one].disabled = false;
        selectModelList[selectType.one].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "1")
            .map((x: any) => ({
              ...x,
              isSelect: false
            }));
      }

      if (selectType.two == type) {
        selectModelList[selectType.one].isSelect = false;
        selectModelList[selectType.three].isSelect = false;
        selectModelList[selectType.three].professionName = '';
        selectModelList[selectType.three].professionID = '';
        selectModelList[selectType.three].professionList = [];

        selectModelList[selectType.two].isSelect = true;
        selectModelList[selectType.two].disabled = false;
        selectModelList[selectType.two].professionName = '';
        selectModelList[selectType.two].professionID = '';
        selectModelList[selectType.two].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "2"
            && x.parentProfessionID == selectModelList[selectType.one].professionID).map((x: any) => ({
          ...x,
          isSelect: false
        }));
      }

      if (selectType.three == type) {
        selectModelList[selectType.one].isSelect = false;
        selectModelList[selectType.two].isSelect = false;

        selectModelList[selectType.three].isSelect = true;
        selectModelList[selectType.three].disabled = false;
        selectModelList[selectType.three].professionName = '';
        selectModelList[selectType.three].professionID = '';
        selectModelList[selectType.three].professionList = professionLevelList.value.filter((x: any) => x.professionLevel == "3"
            && x.parentProfessionID == selectModelList[selectType.two].professionID).map((x: any) => ({
          ...x,
          isSelect: false
        }));
      }

      getSelectProsession();
    }

    const onSelect = (item: any, type: any) => {
      selectModelList[type].professionList.forEach((x: any) => {
        x.isSelect = x.professionID == item.professionID
      });
      selectModelList[type].professionName = item.professionName;
      selectModelList[type].professionID = item.professionID;

      if (type == selectType.one) {
        onSelectType(selectType.two);
      }else if (type == selectType.two) {
        onSelectType(selectType.three);
      }else{
        getSelectProsession();
      }

    }

    const getSelectProsession = () => {
      selectProfessionInfo.text = selectModelList.filter(x => x.professionName != '').map(x => x.professionName).join("-");
      selectProfessionInfo.value = selectModelList[selectType.three].professionID;
    }

    const onCancel = () => {
      isOpen.value = false;
    }

    const onConfirm = () => {
      isOpen.value = false;
      context.emit("SetProfessionID", selectProfessionInfo);
    }

    const onRest = () => {
      selectModelList.splice(0,1,
        {
          index: 0,
          name: '分类一',
          isSelect: false,
          disabled: true,
          professionName: '',
          professionID: '',
          professionList: [] as any
        });
      selectModelList.splice(1,1,{
          index: 1,
          name: '分类二',
          isSelect: false,
          disabled: true,
          professionName: '',
          professionID: '',
          professionList: [] as any
        });

      selectModelList.splice(2,1,  {
          index: 2,
          name: '岗位',
          isSelect: false,
          disabled: true,
          professionName: '',
          professionID: '',
          professionList: [] as any
        });
      selectProfessionInfo.value = '';
      selectProfessionInfo.text = '';
      professionLevelList.value = [];
    }

    return {
      isOpen,
      selectType,
      selectModelList,
      selectProfessionInfo,
      loadProfessionInfo,
      onSelectType,
      onSelect,
      onCancel,
      onConfirm,
      onRest
    };
  }
})
</script>