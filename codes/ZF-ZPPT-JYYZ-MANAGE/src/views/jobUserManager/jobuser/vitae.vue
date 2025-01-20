<template>
  <div class="card-search">
    <div class="jl-content">
      <!-- 头像等信息 -->
      <div class="avt-content">
        <!--头像 -->
        <div class="avt-image">
          <img  src="../../../assets/images/njl-avt.png" v-if="jobUserInfo.genderName=='女'">
          <img src="../../../assets/images/jl-avt.png"  v-if="jobUserInfo.genderName=='男'">
<!--          <a-avatar :size="72" :src="jobUserInfo.genderName === '男'?avtImg:avtNImg"></a-avatar>-->
        </div>
        <!-- 姓名 -->
        <p class="nameTitle">{{ jobUserInfo.name }}</p>
        <!-- 性别，年龄，民族 -->
        <div class="age-info">
          <span>
            <man-outlined v-if="jobUserInfo.genderName === '男'"/>
            <woman-outlined v-if="jobUserInfo.genderName === '女'"/>
            {{ jobUserInfo.genderName }}
          </span>
          <span>
            {{ jobUserInfo.age }}岁
          </span>
          <span>
            {{ jobUserInfo.nationName }}
          </span>
        </div>
        <!-- 政治面貌，学历，手机 -->
        <div class="politics-info">
          <span class="label-span">
            政治面貌：
          </span>
          <span>
            {{ jobUserInfo.politicsStatusName }}
          </span>
          <span class="label-span">
            学历：
          </span>
          <span>
            {{ jobUserInfo.cultureName }}
          </span>
          <span class="label-span">
            手机：
          </span>
          <span>
            {{ jobUserInfo.userMobile }}
          </span>
        </div>
        <!-- 标签 -->
        <div class="label-info" v-if="jobUserInfo.listLabel.length >= 1">
          <p class="title">标签</p>
          <div class="flex-box">
            <a-tag v-for="(item, index) in jobUserInfo.listLabel" :key="index">
              {{ item.labelName }}
            </a-tag>
          </div>
        </div>
      </div>
      <!-- 基本信息与学历，工作经历 -->
      <div class="info-content">
        <a-divider orientation="left">基本信息</a-divider>
        <a-descriptions :column="2" bordered style="margin-left: 25px;">
          <a-descriptions-item label="重点人员类别">{{ jobUserInfo.keyTypeName }}</a-descriptions-item>
          <a-descriptions-item label="健康状况">{{ jobUserInfo.healthName }}</a-descriptions-item>
          <a-descriptions-item label="就业状态">{{ jobUserInfo.jobStatusName }}</a-descriptions-item>
          <a-descriptions-item label="住址">{{ jobUserInfo.address }}</a-descriptions-item>
          <a-descriptions-item :span="2" label="个人评价">{{ jobUserInfo.hobby }}</a-descriptions-item>
          <a-descriptions-item :span="2" label="专业技术特长">{{ jobUserInfo.personalSkills }}</a-descriptions-item>
        </a-descriptions>
        <a-divider orientation="left">教育经历</a-divider>
        <a-table :columns="educationColumns" :data-source="educationData" :pagination="false" bordered
                 style="margin-left: 11px;">
          <template #bodyCell="{ column, index, record}">
            <template v-if="column.key === 'cultureRank'">
              <div>
                {{
                  getCultureName(record.cultureRank)
                }}
              </div>
            </template>
            <template v-if="column.key === 'schoolTime'">
              <div>
                {{
                  dayjs(record.schoolTime).format('YYYY-MM-DD')
                }}
              </div>
            </template>
            <template v-if="column.key === 'overTime'">
              <div>
                {{
                  dayjs(record.overTime).format('YYYY-MM-DD')
                }}
              </div>
            </template>
          </template>
        </a-table>
        <a-divider orientation="left">求职意向</a-divider>
        <a-table :columns="jobHuntColumns" :data-source="jobHuntList" bordered :pagination="false"
                 style="margin-left: 11px">
        </a-table>
        <a-divider orientation="left">工作经历</a-divider>
        <a-timeline style="margin-left: 25px;">
          <a-timeline-item v-for="(item, key) in experienceData" :key="key" position="left">
            <p>{{ dayjs(item.startTime).format('YYYY-MM-DD') }}至{{ dayjs(item.endTime).format('YYYY-MM-DD') }}</p>
            <p>工作单位：{{ item.workAddress }}</p>
            <p>职务：{{ item.duties }}</p>
          </a-timeline-item>
        </a-timeline>
        <a-empty :image="simpleImage"  v-if="experienceData.length==0" ></a-empty>
        <a-divider orientation="left">个人荣誉</a-divider>
        <a-table :columns="HonorColumns" :data-source="honorData" bordered :pagination="false"
                 style="margin-left: 11px">
          <template #bodyCell="{ column, index, record}">
            <template v-if="column.key === 'getTime'">
              <div>
                {{
                  dayjs(record.getTime).format('YYYY-MM-DD')
                }}
              </div>
            </template>
          </template>
        </a-table>
        <a-divider orientation="left">附件</a-divider>
        <b-upload-file :fileRefId="jobUserInfo.jobUserID" :readonly="true" :multiple="true" style="width: 100%;"
                       :setFileList="setFileList" :accept="'.pdf,.png,.jpg'"></b-upload-file>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import {getDataById, getEducationList, getExperienceList, getHonorList} from "@/api/jobUserManager/jobuser";
import {ManOutlined, WomanOutlined} from '@ant-design/icons-vue';
import type {SelectProps, TableColumnsType} from "ant-design-vue";
import dayjs from "dayjs";
import {getSysDictionaryList} from "@/api/system/dictionary";
import {getJobHuntList} from "@/api/jobUserManager/jobhunt";
import {useUserStore} from "@/store/modules/user";
import crtyptoHelp from "@/utils/crypto";
import BUploadFile from "@/components/file/uploadFile.vue";
import {Empty} from "ant-design-vue";

const userStore = useUserStore();
const userInfo = ref(userStore.getUserInfo)

// 求职人员信息
const jobUserInfo = reactive({
  jobUserID: "",
  name: "",
  genderName: "",
  age: null,
  nationName: "",
  politicsStatusName: "",
  cultureName: "",
  userMobile: "",
  birthPlace: "",
  identityNumber: "",
  keyTypeName: "",
  healthName: "",
  jobStatusName: "",
  familyNatureName: "",
  address: "",
  familyAddress: "",
  hobby: "",
  personalSkills: "",
  listLabel: new Array<any>()
})
// 受教育经历
const educationData = ref<Array<any>>([]);
const simpleImage = Empty.PRESENTED_IMAGE_SIMPLE;
// 受教育经历表格定义
const educationColumns: TableColumnsType = [
  {
    title: '序号',
    align: "center",
    key: 'educationID',
    width: 120,
    customRender: item => `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`
  },
  {
    title: '学校名',
    dataIndex: 'schoolName',
    key: 'schoolName',
    align: "center",
    width: 120
  },
  {
    title: '文化程度',
    dataIndex: 'cultureRank',
    key: 'cultureRank',
    align: "center",
    width: 120
  },
  {
    title: '就读时间',
    dataIndex: 'schoolTime',
    key: 'schoolTime',
    align: "center",
    width: 120
  },
  {
    title: '毕业时间',
    dataIndex: 'overTime',
    key: 'overTime',
    align: "center",
    width: 120
  },
  {
    title: '专业',
    dataIndex: 'major',
    key: 'major',
    align: "center",
    width: 120
  },
];
const searchParams = reactive({
  pageIndex: 1,
  pageSize: 99
});
// 工作经验数据
const experienceData = ref<Array<any>>([]);
// 求职意向数据
const jobHuntList = ref<Array<any>>([]);
// 求职意向表格定义
const jobHuntColumns: TableColumnsType = [
  {
    title: '序号',
    align: 'center',
    width: 80,
    key: 'jobHuntID',
    customRender: (item) =>
      `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`,
  },
  {title: '希望工作地区', dataIndex: 'areaWork', key: 'areaWork', width: 150, align: "center",},
  {
    title: '可到职日期', dataIndex: 'inDate', key: 'inDate', width: 100, align: "center",
    customRender: ({record}) => record.inDate == null ? "" : dayjs(record.inDate).format('YYYY-MM-DD'),
  },
  {title: '工作年限', dataIndex: 'workYear', key: 'workYear', align: "center",},
  {title: '求职类型', dataIndex: 'jobHuntTypeStr', key: 'jobHuntTypeStr', align: "center",},
  {title: '求职岗位', dataIndex: 'professionName', key: 'professionName', align: "center",},
  {title: '人才类型', dataIndex: 'jobUserTypeStr', key: 'jobUserTypeStr', align: "center",},
  {
    title: '月薪要求', dataIndex: 'salary', key: 'salary', align: "center",
    customRender: (item) => {
      const salary = showSalary(item.record.minSalary, item.record.maxSalary);
      return salary;
    }
  },
];
const HonorColumns: TableColumnsType = [
  {
    title: '序号',
    align: "center",
    key: 'honorID',
    width: 100,
    customRender: item => `${searchParams.pageSize * (searchParams.pageIndex - 1) + item.index + 1}`
  },
  {
    title: '荣誉名称',
    dataIndex: 'honorName',
    key: 'honorName',
    align: "center",
    width: 300
  },
  {
    title: '获得时间',
    dataIndex: 'getTime',
    key: 'getTime',
    align: "center",
    width: 200
  },
]
const honorData = ref([]);

// 期望月薪显示优化方法
const showSalary = (minSalary: any, maxSalary: any) => {
  if (minSalary != null) {
    if (maxSalary != null) {
      return minSalary.toString() + "-" + maxSalary.toString();
    } else {
      return "≥" + minSalary.toString();
    }
  } else {
    if (maxSalary != null) {
      return "≤" + maxSalary.toString();
    } else {
      return "";
    }
  }
}
// 教育经历数据
const cultureList = ref<SelectProps['options']>();

// 加载求职人员数据
const loadData = (id: any) => {
  getDataById(id, userInfo.value.userID, false).then(data => {
    Object.keys(jobUserInfo).forEach(key => {
      jobUserInfo[key] = data[key];
    })

    jobUserInfo.userMobile = crtyptoHelp.decryptDesText(jobUserInfo.userMobile);
    jobUserInfo.identityNumber = crtyptoHelp.decryptDesText(jobUserInfo.identityNumber);
  });
};

// 加载教育经历
const loadEducation = (id: any) => {
  getEducationList(id, true).then(data => {
    if (data) {
      educationData.value = data;
    }
  });
}

// 加载工作经历
const loadExperienceData = (id: any) => {
  getExperienceList(id, true).then(data => {
    if (data) {
      experienceData.value = data;
    }
  })
}

// 加载个人荣誉
const loadHonorData = (id: any) => {
  getHonorList(id).then(data => {
    if (data) {
      honorData.value = data;
    }
  })
}

// 加载求职意向数据
function loadJobHuntData(jobUserID: any) {
  let params = {
    pageIndex: 1,
    pageSize: 999,
    jobUserID
  }
  getJobHuntList(params).then(result => {
    jobHuntList.value = result.list
  })
}

// 获取教育经历数据
const getCultureList = () => {
  getSysDictionaryList('HighestDegree').then((data) => {
    cultureList.value = data;
  });
};

function getCultureName(value: any) {
  let filter = cultureList.value?.filter(item => item.value == value);
  if (filter && filter.length > 0) {
    return filter[0].name
  }
  return "";
}

const fileList = ref();
const setFileList = (files) => {
  fileList.value = files;
};

// 页面初始化
onMounted(() => {
  const id = history.state.params?.id;
  if (id) {
    loadData(id);
    loadEducation(id);
    loadExperienceData(id);
    loadJobHuntData(id);
    loadHonorData(id);
  }
  getCultureList()
})
</script>

<script lang="ts">
// 设置页面名称进行组件缓存
export default {
  name: "JobUserVitae"
}
</script>

<style lang="less" scoped>
.card-search {

  .jl-content {
    min-height: calc(100vh - 240px);
    width: 100%;
    display: flex;

    .avt-content {
      width: 20%;
      background-color: #e7e7e7;
      padding: 10px;

      .avt-image {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        img{
          width:120px;
          height:120px;
        }
      }

      .age-info {
        width: 100%;
        display: flex;
        justify-content: space-between;
        margin-top: 15px;
        padding: 0 20%;
      }

      .politics-info {
        margin-top: 25px;
        display: grid;
        row-gap: 22px;
        grid-template-columns: repeat(2, minmax(0, 1fr));

        .label-span {
          text-align: right;
        }
      }

      .nameTitle {
        margin-top: 1rem;
        text-align: center;
        font-size: 20px;
        font-weight: 700;
      }

      .label-info {
        margin-top: 15px;
        padding: 0 5%;

        .title {
          margin-bottom: 10px;
        }

        .flex-box {
          display: flex;
          flex-wrap: wrap;

          .ant-tag {
            text-align: center;
            margin-bottom: 8px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
      }

    }

    .info-content {
      width: 80%;
      height: 100%;
      margin-left: 10px;
    }
  }
}


</style>
