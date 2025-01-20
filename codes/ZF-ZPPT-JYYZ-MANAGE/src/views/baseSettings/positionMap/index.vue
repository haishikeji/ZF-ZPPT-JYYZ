<template>
  <div class="card-search">
    <a-form
      ref="formRef"
      name="advanced_search"
      class="ant-advanced-search-form"
      :model="searchParams"
    >
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item label="所属县区" :label-col="{ span: 8 }" name="regionCode">
            <a-select
              ref="select"
              v-model:value="searchParams.regionCode"
              :options="regionList"
              :field-names="{ label: 'name', value: 'code' }"
              :allow-clear="true"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="所属驿站" :label-col="{ span: 8 }" name="siteID">
            <a-select
              ref="select"
              v-model:value="searchParams.siteID"
              :options="allSites"
              :field-names="{ label: 'siteName', value: 'siteID' }"
              :allow-clear="true"
              style="width: 200px"
              @change="loadData"
            >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="日期" :label-col="{ span: 8 }" name="startDate">
            <a-date-picker v-model:value="searchParams.startDate" value-format="YYYY-MM-DD"/>
          </a-form-item>
        </a-col>
        <a-col :span="6" style="text-align: left">
          <a-button type="primary" html-type="submit" @click="onSearch">查询</a-button>
          <a-button
            style="margin: 0 8px"
            @click="
              () => {
                formRef.resetFields();
                loadData();
              }
            "
            >重置
          </a-button>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item label="人员名称" :label-col="{ span: 8 }" name="siteUserName">
            <a-input v-model:value="searchParams.userName" placeholder="" :allow-clear="true"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <div
      id="mapDiv"
      style="
        position: absolute;
        width: calc(100% - 270px);
        height: calc(100% - 300px);
        z-index: 100;
      "
    ></div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import dayjs from 'dayjs';
  import {type FormInstance, message, type SelectProps} from 'ant-design-vue';
  import { getRegionCodeList } from '@/api/system/area/index';
  import { getSiteList } from '@/api/baseSettings/siteInfo';
  import {getLongitudeLatitudeNewAllList} from "@/api/taskAndLog/longitudeLatitude";
  import thIcon from '@/assets/images/th.jpg';

  export default defineComponent({
    name: 'PositionMapIndex',
    setup() {
      const formRef = ref<FormInstance>();
      const T = (window as any).T;
      const zoom = 14;
      let map = null;
      let markerList = new Array<any>([]);
      let labelList = new Array<any>([]);
      const trackList = ref<Array<any>>([])
      const regionList = ref<SelectProps['options']>();
      const allSites = ref<any>([]);
      const searchParams = ref({
        regionCode: "",
        siteID: "",
        userName: "",
        startDate: dayjs(new Date()).format('YYYY-MM-DD'),
      });

      const loadData = () => {
        return '';
      };
      const getRegionList = async function () {
        const regionResult: any = await getRegionCodeList();
        regionList.value = regionResult;
      };
      const getAllSites = () => {
        getSiteList({ pageIndex: 1, pageSize: 1000 }).then((result: any) => {
          allSites.value = result.list;
        });
      };

      const initMap = () => {
        console.log(`T:${T}`);
        //初始化地图对象
        map = new T.Map('mapDiv');

        if (map != null) (map as any).centerAndZoom(new T.LngLat(114.4, 23.08), zoom); //
      };

      async function getTrackList() {
        // 清空数据
        trackList.value = [];

        // 查询经纬度数据
        await getLongitudeLatitudeNewAllList(searchParams.value.userName, searchParams.value.startDate, searchParams.value.regionCode, searchParams.value.siteID).then((result: any) => {
          trackList.value = result;
        })
      }

      // 查询运动轨迹
      async function onSearch() {
        if (!searchParams.value.startDate) {
          message.error('请选择日期！');
          return;
        }

        // 删除已有标点
        if (markerList.length > 0) {
          for (let i = 0; i < markerList.length; i++) {
            (map as any).removeOverLay(markerList[i]);
          }
        }
        if (labelList.length > 0) {
          for (let i = 0; i < labelList.length; i++) {
            (map as any).removeOverLay(labelList[i]);
          }
        }

        try {
          await getTrackList()
        } catch (error) {
          console.log(error)
        }

        if (trackList.value.length == 0) {
          message.info('当日无轨迹');
          //设置显示地图的中心点和级别
          (map as any).centerAndZoom(new T.LngLat(114.40, 23.08), zoom);//
          return;
        }

        // 设置中心点，以第一条数据的定位为显示中心
        const center = trackList.value[0];
        (map as any).centerAndZoom(new T.LngLat(center.longitude, center.latitude), zoom);

        const icon = new T.Icon({
          iconUrl: thIcon,
          iconSize: new T.Point(30, 30),
          iconAnchor: new T.Point(10, 56)
        })
        trackList.value.map((item: any) => {
          const label = new T.Label({
            text: item.siteName + item.userName, //文本标注的内容
            position: new T.LngLat(item.longitude, item.latitude), //文本标注的地理位置
            offset: new T.Point(-50, 20) //文本标注的位置偏移值
          })
          const point = new T.LngLat(item.longitude, item.latitude, {
            icon: icon
          })
          const marker = new T.Marker(point); // 创建标注
          (map as any).addOverLay(label); // 将标注添加到地图中
          (map as any).addOverLay(marker);// 将标注添加到地图中
          labelList.push(label);
          markerList.push(marker);
        })
      }

      onMounted(() => {
        initMap();
        getRegionList();
        getAllSites();
      });

      return {
        searchParams,
        loadData,
        formRef,
        regionList,
        allSites,
        onSearch
      };
    },
    created() {},
  });
</script>
