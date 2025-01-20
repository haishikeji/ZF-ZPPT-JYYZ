<template>
  <div class="card-edit">
    <a-divider orientation="left">站点人员基本信息</a-divider>
    <a-descriptions bordered>
      <a-descriptions-item label="姓名">{{ siteUserInfo.siteUserName }}</a-descriptions-item>
      <a-descriptions-item label="性别">{{ siteUserInfo.genderName }}</a-descriptions-item>
      <a-descriptions-item label="头像">
        <img v-if="!avtImageUrl" src="~@/assets/images/admin.png" alt="" class="avt-img"/>
        <img v-else :src="avtImageUrl" alt="" style="border-radius: 50%" class="avt-img"/>
      </a-descriptions-item>
      <a-descriptions-item label="联系电话">{{ siteUserInfo.mobile }}</a-descriptions-item>
      <a-descriptions-item label="工号">{{ siteUserInfo.userNo }}</a-descriptions-item>
      <a-descriptions-item label="用户类型">{{ siteUserInfo.roleName }}</a-descriptions-item>
      <a-descriptions-item label="所属驿站">{{ siteUserInfo.siteName }}</a-descriptions-item>
      <a-descriptions-item label="最后更新人">{{ siteUserInfo.updateUserName }}</a-descriptions-item>
      <a-descriptions-item label="最后更新时间">
        {{ siteUserInfo.updateTime ? dayjs(siteUserInfo.updateTime).format('YYYY-MM-DD') : '' }}
      </a-descriptions-item>
    </a-descriptions>
    <a-divider orientation="left">其他信息</a-divider>
    <a-descriptions bordered>
      <a-descriptions-item label="民族">{{ siteUserInfo.nationName }}</a-descriptions-item>
      <a-descriptions-item label="政治面貌">{{ siteUserInfo.politicsStatusName }}</a-descriptions-item>
      <a-descriptions-item label="最高学历">{{ siteUserInfo.cultureRankName }}</a-descriptions-item>
      <a-descriptions-item label="毕业院校">{{ siteUserInfo.finishSchool }}</a-descriptions-item>
      <a-descriptions-item label="专业">{{ siteUserInfo.profession }}</a-descriptions-item>
      <a-descriptions-item label="是否全日制">{{ siteUserInfo.isFullTimeName }}</a-descriptions-item>
      <a-descriptions-item label="职业资格类别">{{ siteUserInfo.occupationalCategoryName }}</a-descriptions-item>
      <a-descriptions-item label="职业资格等级">{{ siteUserInfo.occupationalLevelName }}</a-descriptions-item>
      <a-descriptions-item label="籍贯">{{ siteUserInfo.nativePlace }}</a-descriptions-item>
      <a-descriptions-item label="邮箱">{{ siteUserInfo.email }}</a-descriptions-item>
      <a-descriptions-item label="住址">{{ siteUserInfo.address }}</a-descriptions-item>
    </a-descriptions>
    <!--    <a-divider orientation="left">相片</a-divider>-->
    <!--    <b-upload-file :fileRefId="siteUserInfo.userID" :readonly="true" :multiple="false" :restrict="1"-->
    <!--                   :setFileList="setFileList" :accept="'.pdf,.png,.jpg'"></b-upload-file>-->
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import {getSiteUserByID} from "@/api/baseSettings/userInfo";
// import BUploadFile from "@/components/file/uploadFile.vue";
import dayjs from "dayjs";
// import {useUserStore} from "@/store/modules/user";
import {getFileBase64, getList} from "@/api/system/file";
import {base64ToBlob} from "@/utils/imageUtils";

// const userStore = useUserStore();
// const userInfo = reactive(userStore.getUserInfo);
// 站点人员信息
const siteUserInfo = reactive({
  siteUserID: "",
  siteUserName: "",
  genderName: "",
  mobile: "",
  userNo: "",
  idCard: "",
  roleName: "",
  siteName: "",
  socialSecurityCard: "",
  finishSchool: "",
  profession: "",
  nativePlace: "",
  email: "",
  address: "",
  nationName: "",
  politicsStatusName: "",
  cultureRankName: "",
  isFullTimeName: "",
  occupationalCategoryName: "",
  occupationalLevelName: "",
  updateUserName: "",
  updateTime: ""
})
// 头像图片
const avtImageUrl = ref<any>("")

// 文件列表
// const fileList = ref();
// const setFileList = (files) => {
//   fileList.value = files;
// };

// 站点人员数据加载
function loadData(siteUserID: string) {
  getSiteUserByID(siteUserID, 1).then((result: any) => {
    Object.keys(siteUserInfo).forEach(key => {
      siteUserInfo[key] = result[key];
    })
    getAvtImag(result.userID)
  });
}

// 获取头像
function getAvtImag(fileRefID) {
  if (!fileRefID) {
    return;
  }
  getList({fileRefID}).then((res) => {
    if (!res) {
      avtImageUrl.value = false;
    } else {
      // 获取图片
      getFileBase64({fileId: res[0].fileId}).then(data => {
        if (data) {
          avtImageUrl.value = URL.createObjectURL(base64ToBlob("data:image/png;base64," + data));
        }
      })
    }
  })
}

// 页面初始化
onMounted(() => {
  const id = history.state.params?.id;
  loadData(id);
})
</script>

<script lang="ts">
// 设置页面名称进行组件缓存
export default {
  name: "SiteUserDetail"
}
</script>

<style lang="less">
.avt-img {
  width: 40px;
  height: 40px;
}
</style>
