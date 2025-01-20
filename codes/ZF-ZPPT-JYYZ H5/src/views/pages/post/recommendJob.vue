<template>
  <ion-page class="list-page">
    <ion-header class="header-theme2">
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-icon :icon="arrowBackOutline" @click="onBack"></ion-icon>
        </ion-buttons>
        <ion-title>求职人员推荐</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <ion-item class="search-item">
        <ion-label>
          <h2>{{ searchParams.professionName }}</h2>
          <br/>
          <h2>{{ searchParams.companyName }}</h2>
        </ion-label>
        <ion-button id="jobHuntPopoverCheckbox" @click="popoverCheckboxIsOpen = true" slot="end"
                    style="height: 33px;width: 90px;margin-left: 10px;--box-shadow: none;--border-radius: 14px;">求职人员筛选
        </ion-button>
        <ion-popover size="auto" trigger="jobHuntPopoverCheckbox" :is-open="popoverCheckboxIsOpen" :backdrop-dismiss="false"
                     :dismiss-on-select="false">
          <ion-content>
            <ion-list>
              <ion-item v-for="(record,key) in recommendWhereList" :key="key">
                <ion-checkbox :detail="false" v-model="record.check" label-placement="end">{{ record.name }}
                </ion-checkbox>
              </ion-item>
              <ion-item>
                <ion-button @click="onRestRecommendWhere" slot="end" fill="clear" size="small">重置</ion-button>
                <ion-button @click="onPopoverConfirm" slot="end" size="small">确定</ion-button>
              </ion-item>
            </ion-list>
          </ion-content>
        </ion-popover>
      </ion-item>
      <div class="bw-vue-list">
        <div class="list-content">
          <ion-list>
            <ion-item v-for="(record,key) in dataList" :key="key">
              <ion-label>
                <h2>{{ record.userName }}</h2>
                <p>应聘岗位：{{ record.professionName }}</p>
                <p>电话：{{ record.userMobile }}</p>
              </ion-label>
              <ion-avatar aria-hidden="true" class="container" slot="end">
                <ion-button size="small" @click="onRecommend(record)" fill="outline">推荐</ion-button>
              </ion-avatar>
            </ion-item>
          </ion-list>
        </div>
      </div>
      <b-empty v-if="dataList.length<=0" :loading="loading"/>
      <ion-infinite-scroll threshold="100px" @ionInfinite="onScroll($event)">
        <ion-infinite-scroll-content
            :loadingText="pagination.total>pagination.pageIndex*pagination.pageSize?'正在加载...':'暂无更多'"
            loadingSpinner="bubbles">
        </ion-infinite-scroll-content>
      </ion-infinite-scroll>
    </ion-content>
    <ion-footer>
      <ion-button shape="round" expand="block" @click="onBatchRecommend">全部推荐</ion-button>
    </ion-footer>
  </ion-page>
</template>

<script lang="ts">
import {useRoute, useRouter} from "vue-router";
import {computed, defineComponent, reactive, ref} from 'vue';
import {arrowBackOutline, addCircleOutline} from 'ionicons/icons';
import {alertController, IonIcon, onIonViewDidEnter} from '@ionic/vue';
import {getRecommendJobList, addRecommend} from "@/api/recommendmgt";
import {getSysDictionaryList} from "@/api/system/dictionary";
import BEmpty from "@/components/empty.vue";

export default defineComponent({
  name: 'RecommendJobList',
  components: {IonIcon,BEmpty},
  setup() {
    const loading = ref(true);
    const router = useRouter();
    const route = useRoute();
    const total = ref(10);
    const pagination = computed(() => ({
      total: total,
      pageIndex: searchParams.pageIndex,
      pageSize: searchParams.pageSize
    }));
    const searchParams = reactive({
      pageIndex: 1,
      pageSize: 5,
      postID: '',
      professionID: '',
      companyName: '',
      professionName: '',
      type: 0,
      parentProfessionID: '',
      cultureRank: null,
      workYear: null,
      minSalary: null,
      maxSalary: null
    })
    const searchParamsCache = reactive({
      professionID: '',
      parentProfessionID: '',
      cultureRank: null,
      workYear: null,
      minSalary: null,
      maxSalary: null
    });
    const dataList = ref<any>([]);
    const addRecommendList = ref<any>([]);
    const recommendWhereList = ref<any>([]);
    const popoverCheckboxIsOpen = ref<boolean>(false);

    const getRecommendTypeList = () => {
      getSysDictionaryList('RecommendJobHuntWhere').then((data) => {
        recommendWhereList.value = data;
        recommendWhereList.value[0].check = true;
      });
    };

    const loadData = async function () {
      loading.value = true;
      getRecommendJobList(searchParams).then((data: any) => {
        dataList.value = dataList.value.concat(data.list);
        total.value = data.total;
      });
      loading.value = false;
    }

    const onPopoverConfirm = () => {
      const list = recommendWhereList.value.filter((x: any) => x.check == true).map((x: any) => x.value);

      searchParams.professionID = list.findIndex((x: any) => x == 1) >= 0 ? searchParamsCache.professionID : "";
      searchParams.cultureRank = list.findIndex((x: any) => x == 2) >= 0 ? searchParamsCache.cultureRank : null;
      searchParams.workYear = list.findIndex((x: any) => x == 3) >= 0 ? searchParamsCache.workYear : null;
      if (list.findIndex((x: any) => x == 4) >= 0) {
        searchParams.minSalary = searchParamsCache.minSalary;
        searchParams.maxSalary = searchParamsCache.maxSalary;
      } else {
        searchParams.minSalary = null;
        searchParams.maxSalary = null;
      }
      searchParams.parentProfessionID = list.findIndex((x: any) => x == 5) >= 0 ? searchParamsCache.parentProfessionID : "";

      popoverCheckboxIsOpen.value = false;
      reload();
    }

    const onRestRecommendWhere = () => {
      recommendWhereList.value.map((x: any) => x.check = false);
      recommendWhereList.value[0].check = true;
    }

    const onScroll = (e: any) => {
      setTimeout(() => {
        if (pagination.value.total.value > pagination.value.pageIndex * pagination.value.pageSize) {
          searchParams.pageIndex += 1;
          loadData();
        }
        e.target.complete();
      }, 500);
    }

    const onRecommend = (item: any) => {
      addRecommendList.value.push({
        recommendMgtID: item.recommendMgtID,
        jobHuntID: item.jobhuntID,
        postID: searchParams.postID,
        recommendType: 0
      });
      addRecommend(addRecommendList.value).then(() => {
        const index = dataList.value.findIndex((x: any) => x.recommendMgtID == item.recommendMgtID);
        dataList.value.splice(index, 1)
        addRecommendList.value = [];
      });
    };

    const onBatchRecommend = () => {
      if (dataList.value.length == 0) {
        presentAlert("没有需要推荐的求职人员！")
        return;
      }

      dataList.value.forEach((item: any) => {
        addRecommendList.value.push({
          recommendMgtID: item.recommendMgtID,
          jobHuntID: item.jobhuntID,
          postID: searchParams.postID,
          recommendType: 0
        });
      })
      addRecommend(addRecommendList.value).then(() => {
        loadData();
        dataList.value = [];
        addRecommendList.value = [];
      });
    };

    const presentAlert = async (message: string) => {
      const alert = await alertController.create({
        header: '错误！',
        message: message,
        buttons: [
          '确定'
        ],
      });

      await alert.present();
    }

    const onBack = () => {
      router.push({path: './list', query: {reload:1}});
    }

    const reload = () => {
      dataList.value = [];
      searchParams.pageIndex = 1;
      loadData();
    }

    const reloadSearchParams = () => {
      searchParams.postID = route.query.postID as any;
      searchParams.companyName = route.query.companyName as any;
      searchParams.professionID = route.query.professionID as any;
      searchParams.professionName = route.query.professionName as any;
      searchParams.type = route.query.type as any;
      searchParams.parentProfessionID = "";
      searchParams.cultureRank = null;
      searchParams.workYear = null;
      searchParams.minSalary = null;
      searchParams.maxSalary = null;

      searchParamsCache.professionID = route.query.professionID as any;
      searchParamsCache.parentProfessionID = route.query.parentProfessionID as any;
      searchParamsCache.cultureRank = route.query.cultureRank as any;
      searchParamsCache.workYear = route.query.workYear as any;
      searchParamsCache.minSalary = route.query.minSalary as any;
      searchParamsCache.maxSalary = route.query.maxSalary as any;
    }

    onIonViewDidEnter(() => {
      getRecommendTypeList();
      reloadSearchParams();
      reload();
    });

    return {
      arrowBackOutline,
      addCircleOutline,
      router,
      route,
      loading,
      pagination,
      searchParams,
      dataList,
      recommendWhereList,
      popoverCheckboxIsOpen,
      onScroll,
      loadData,
      reload,
      onBack,
      onRecommend,
      onBatchRecommend,
      getRecommendTypeList,
      onPopoverConfirm,
      onRestRecommendWhere,
    }
  }
});
</script>

<style lang="less">
.footer-ios ion-toolbar:first-of-type {
  --border-width: 0 !important;
  --background: #ffffff !important;
}

</style>

