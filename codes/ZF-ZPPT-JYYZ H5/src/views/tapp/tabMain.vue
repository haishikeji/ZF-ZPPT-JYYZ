<template>
  <ion-page>
    <div class="tab-main-content">
      <div class="panel" style="margin-top: 125px;height: 82%;">
        <div class="panel-content" style="height: 92%;">
          <div class="panel-title" >
            考勤管理
          </div>
          <div class="tool-box">
            <a class="tool-item box-line" @click="router.push('/tapp/tabMain/tattendancereport')">
              <div class="tool-img">
                <img src="@/assets/icon/kq.png">
              </div>
              <div class="tool-title">考勤统计</div>
            </a>
          </div>
        </div>
      </div>
    </div>
  </ion-page>
</template>

<script setup lang="ts">
import router from '@/router';

</script>

<style lang="less">
.tab-main-content {
  background-image: url("../../assets/icon/main-bg.png");
  width: 100%;
  height: 100%;
  background-position: top;
  background-size: contain;
  background-repeat: no-repeat;
  background-color: #F5F5F5;
  background-attachment: local;
  overflow-y: auto;
  padding-bottom: 15px;

  .main-title {
    font-size: 24px;
    color: white;
    margin:40px 0 30px 0;
    text-align: center;
  }

  .item-detail {
    margin: 10px;
    border-radius: 8px;
    color: #380F0A;
    font-size: 15px;
  }

  .panel {
    margin: 10px;

    .panel-title {
      font-size: 18px;
      color: #380F0A;
      font-weight: bold;
      display: flex;
      justify-content: space-between;
      padding: 8px 0 0 20px;

      .sub-title {
        font-size: 13px;
        color: #998989;
      }
    }

    .panel-content {
      background-color: white;
      border-radius: 10px;
      padding: 10px;
    }
  }
  .tool-box {
    display: flex;
    flex-wrap: wrap;
    /*padding: 10px 0;*/

    .tool-item {
      flex-direction: column;
      flex: 0 0 25%;
      margin: 10px 0;
      text-decoration: none;
      cursor: pointer;
      width: 25%;
      padding: 0 2px;

      ion-img {
        height: 35px;
        width: 43px;
      }

      .tool-img {
        display: flex;
        justify-content: center;

        img{
          width: 42px;
          height:42px;
        }
      }

      .tool-title {
        color: #5E4545;
        margin-top: 10px;
        display: flex;
        justify-content: center;
        font-size: 14px;
        text-align: center;
      }
    }
  }
}

</style>
