<template>
  <ion-page class="list-page">
    <ion-header>
      <ion-toolbar>
        <ion-title>表单页面</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
      <div class="bw-vue-form">
        <div class="form-title">船舶信息编辑页面</div>
        <div class="form-input">
          <ion-label>船舶名称</ion-label>
          <ion-input placeholder="请输入船舶名称"></ion-input>
        </div>
        <div class="form-input">
          <ion-label>证件号码</ion-label>
          <ion-input placeholder="请输入证件号码"></ion-input>
        </div>
        <div class="form-select">
          <ion-label>船舶主体</ion-label>
          <ion-select interface="action-sheet" placeholder="请选择船舶主体">
            <ion-select-option value="apples">Apples</ion-select-option>
            <ion-select-option value="oranges">Oranges</ion-select-option>
            <ion-select-option value="bananas">Bananas</ion-select-option>
          </ion-select>
        </div>
      </div>

      <div class="bw-vue-form">
        <div class="form-title">个人基本信息详情页面</div>
        <div class="form-detail">
          <ion-label>社保号码</ion-label>
          <ion-text>123456789</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>姓名</ion-label>
          <ion-text>张xx</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>性别</ion-label>
          <ion-text>男</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>民族</ion-label>
          <ion-text>汉族</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>联系电话</ion-label>
          <ion-text>123456789</ion-text>
        </div>
        <div class="form-detail">
          <ion-label>简单介绍</ion-label>
          <ion-text>
            上海张江综合性国家科学中心与合肥综合性国家科学中心携手实施“两心同创”，探路科技创新共同体建设。“合肥光源”和“上海光源”的合作，正是这一探索的生动实践
          </ion-text>
        </div>
      </div>

      <div class="bw-vue-list">
        <div class="list-title">列表页面</div>
        <div class="list-content">
          <ion-list>
            <ion-item>
              <ion-label>
                <div class="multi-title">
                  <h2>广州市中医院同德门诊部</h2>
                  <p>7.58km</p>
                </div>
                <p>广州市白云区27号</p>
              </ion-label>
            </ion-item>
            <ion-item>
              <ion-label>
                <h2>H3 Heading</h2>
                <p>Paragraph</p>
              </ion-label>
            </ion-item>
            <ion-item>
              <ion-label>
                <h2>H3 Heading</h2>
                <p>Paragraph</p>
              </ion-label>
            </ion-item>
            <ion-item>
              <ion-label>
                <h2>H3 Heading</h2>
                <p>Paragraph</p>
              </ion-label>
            </ion-item>
          </ion-list>
        </div>
      </div>
      <!--       <div>
               <b-qr-scan></b-qr-scan>
             </div>-->

      <b-image :file-ref-id="fileRefId" :readonly="false" :is-single="false"></b-image>

      <div id='mapDiv' style='position: absolute; width: 100%; height: 100%; z-index: 100'></div>
    </ion-content>

  </ion-page>
</template>
<script>
import {defineComponent} from "vue";
import BQrScan from "@/components/bQrScan.vue";
import BImage from "@/components/bImage.vue";

export default defineComponent({
  name: "demo_edit",
  // eslint-disable-next-line vue/no-unused-components
  components: {BQrScan, BImage},
  setup() {
    const fileRefId = "11111";//文件关键业务ID
    const initMap = () => {
      const T = window.T;
      const zoom = 12;

      //初始化地图对象
      const map = new T.Map("mapDiv");
      //设置显示地图的中心点和级别
      map.centerAndZoom(new T.LngLat(116.40969, 39.94940), zoom);
      const points = [];
      points.push(new T.LngLat(116.41239, 39.97569));
      points.push(new T.LngLat(116.412799, 39.9068));
      points.push(new T.LngLat(116.32970, 39.92940));
      points.push(new T.LngLat(116.385440, 39.90610));
      //创建线对象
      const line = new T.Polyline(points);
      //向地图上添加线
      map.addOverLay(line);
    }

    window.onload = initMap;
    return {
      fileRefId
    }
  }
});
</script>
<style lang="less">

</style>