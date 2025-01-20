<template>
  <div class="card-edit">
    <a-divider orientation="left">工作备忘信息</a-divider>
    <a-descriptions bordered>
      <a-descriptions-item label="姓名">{{ workInfo.userName }}</a-descriptions-item>
      <a-descriptions-item label="备忘类型">{{ workInfo.logTypeName }}</a-descriptions-item>
      <a-descriptions-item label="完成时间">{{ workInfo.workTime }}</a-descriptions-item>
      <a-descriptions-item label="所属县区">{{ workInfo.regionName }}</a-descriptions-item>
      <a-descriptions-item :span="2" label="所属街道">{{ workInfo.streetName }}</a-descriptions-item>
      <a-descriptions-item label="工作备忘情况">{{ workInfo.workContent }}</a-descriptions-item>
      <a-descriptions-item label="最后更新人">{{ workInfo.modifyUserName }}</a-descriptions-item>
      <a-descriptions-item label="最后更新时间">
        {{ workInfo.modifyTime ? dayjs(workInfo.modifyTime).format('YYYY-MM-DD') : '' }}
      </a-descriptions-item>
    </a-descriptions>
    <a-divider orientation="left">工作量完成情况</a-divider>
    <a-descriptions :column="2" bordered>
      <a-descriptions-item label="走访企业数量">{{ recordsCount.RecordsCompanyNum }}</a-descriptions-item>
      <a-descriptions-item label="收集岗位">{{ recordsCount.RecordsPostNum }}</a-descriptions-item>
      <a-descriptions-item label="登记就业人数">{{ recordsCount.RecordsJobuserNum }}</a-descriptions-item>
      <a-descriptions-item label="求职意向">{{ recordsCount.RecordsJobhuntNum }}</a-descriptions-item>
    </a-descriptions>
    <a-divider orientation="left">其他信息</a-divider>
    <b-upload-file :accept="'.pdf,.png,.jpg'" :fileRefId="workInfo.doWorkID"
                   :multiple="true" :readonly="true" :setFileList="setFileList"></b-upload-file>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {get, getRecordsCount} from "@/api/taskAndLog/dowork";
import BUploadFile from "@/components/file/uploadFile.vue";
import dayjs from "dayjs";

// 日志信息
const workInfo = reactive({
  doWorkID: "",
  userName: "",
  logTypeName: "",
  userId: "",
  workTime: "",
  regionName: "",
  streetName: "",
  workContent: "",
  modifyUserName: "",
  modifyTime: ""
})
// 工作量完成数据
const recordsCount = reactive({
  RecordsCompanyNum: null,
  RecordsJobuserNum: null,
  RecordsPostNum: null,
  RecordsJobhuntNum: null,
});
// 文件列表
const fileList = ref();

// 查询日志信息
function loadData(doWorkID: string) {
  get(doWorkID).then((result: any) => {
    Object.keys(result).forEach((key: string) => {
      workInfo[key] = result[key];
    })
    findRecordsCount()
  });
};

// 查询工作量信息
function findRecordsCount() {
  if (workInfo.userId && workInfo.workTime) {
    getRecordsCount(workInfo.userId, workInfo.workTime).then((res) => {
      // 赋值
      Object.keys(recordsCount).forEach(key => {
        recordsCount[key] = res[key];
      })
    });
  }
}

// 设置文件列表
const setFileList = (files) => {
  fileList.value = files;
};

// 页面初始化
onMounted(() => {
  const id = history.state.params?.id;
  loadData(id);
})
</script>

<script lang="ts">
// 设置页面名称进行组件缓存
export default {
  name: "DoWorkDetail"
}
</script>

<style scoped>

</style>
