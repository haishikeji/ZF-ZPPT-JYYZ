<template>
  <ion-page v-if="$routeActive">
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="tabMain" href="/tapp/tabMain" class="tab-main">
          <img class="tab-main-icon"/>
          <ion-label>首页</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tabWork" href="/tapp/tabWork" class="tab-work">
          <img class="tab-work-icon"/>
          <ion-label>工作台</ion-label>
        </ion-tab-button>

        <ion-tab-button tab="tabUser" href="/tapp/tabUser" class="tab-user">
          <img class="tab-user-icon"/>
          <ion-label>我的</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  </ion-page>
</template>

<script setup lang="ts">
</script>
<style lang="less">
.tab-main{
  img{
    content: url("@/assets/icon/tab-main.png");
  }
}
.tab-work{
  img{
    content: url("@/assets/icon/tab-work.png");
  }
}
.tab-user{
  img{
    content: url("@/assets/icon/tab-user.png");
  }
}

ion-tab-button img{
  width: 20px;
}
ion-tab-button[aria-selected=false] ion-label {
  color: white;
  font-size: 12px;
}

ion-tab-button[aria-selected=true] ion-label {
  color: #014C85;
  font-size: 12px;
}

.tab-selected{
  .tab-main-icon{
    content: url("@/assets/icon/tab-main-s.png");
  }
  .tab-work-icon{
    content: url("@/assets/icon/tab-work-s.png");
  }
  .tab-user-icon{
    content: url("@/assets/icon/tab-user-s.png");
  }

}


</style>
