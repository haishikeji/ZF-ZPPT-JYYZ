<template>
  <div v-for="it in dataList">
    <span><a>{{ it.fileName }}</a></span>
    <download-outlined @click="downFile(it)" ></download-outlined>
    <delete-outlined @click="deleteFile(it)" v-if="!readonly"></delete-outlined>
  </div>
  <upload-outlined @click="showModal" v-if="!readonly"></upload-outlined>
  <a-modal
    v-model:visible="visible"
    title="选择附件"
    :ok-text="okText"
    cancel-text="取消"
    @ok="handleUpload"
    :keyboard="false"
    :mask-closable="false"
    :closable="false"
    :confirm-loading="confirmLoading"
  >
    <a-upload :file-list="fileList" :before-upload="beforeUpload" @remove="handleRemove" :accept="accept">
      <a-button>
        <upload-outlined></upload-outlined>
        选择文件
      </a-button>
    </a-upload>
  </a-modal>
  <a-image
    :width="200"
    :style="{ display: 'none' }"
    :preview="{
        visible:previewVisible,
        onVisibleChange: setVisible
      }"
    :src="previewUrl"
  />
</template>
<script lang="ts">
import {
  UploadOutlined,
  DownloadOutlined,
  DeleteOutlined,
  ExclamationCircleOutlined,
  FileImageOutlined
} from '@ant-design/icons-vue';
import {createVNode, defineComponent, ref} from 'vue';
import type {UploadProps} from 'ant-design-vue';
import {getList, upload, deleteFile as deleteFileApi} from '@/api/system/file';
import {message, Modal} from 'ant-design-vue';
import {download} from "@/utils/downloadFile";

export default defineComponent({
  name: 'b-upload-single-file',
  props: {
    fileRefId: {type: String, default: ''},
    readonly: {type: Boolean, default: true},
    isSingle: {type: String, default: '0'},
    accept: {
      type: String,
      default: 'image/png, image/jpeg,image/jpg,image/gif,.png,.jpeg,.jpg,.gif,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf,.zip,.txt',
    },
    setFileList: {type: Function, default: null},
    preview: {type: Boolean, default: false}
  },
  watch: {
    'fileRefId'() {
      this.getFileList();
    }
  },
  components: {
    UploadOutlined,
    DownloadOutlined,
    DeleteOutlined,
    FileImageOutlined
  },
  setup(props) {
    const visible = ref<boolean>(false);
    const previewVisible = ref<boolean>(false);
    const previewUrl = ref<string>('');
    const confirmLoading = ref<boolean>(false);
    const okText = ref<string>("确认上传");
    const fileList = ref<UploadProps['fileList']>([]);
    const dataList = ref<any>([]);
    const fileInfo = ref<any>({fileName: '', fileId: ''});

    const showModal = () => {
      fileList.value = [];
      visible.value = true;
    };

    const handleRemove: UploadProps['onRemove'] = file => {
      const index = fileList.value!.indexOf(file);
      const newFileList = fileList.value!.slice();
      newFileList.splice(index, 1);
      fileList.value = newFileList;
    };

    const beforeUpload: UploadProps['beforeUpload'] = file => {
      const isAccept = props.accept.indexOf(file.name.slice(file.name.lastIndexOf('.'))) > -1;
      if (!isAccept) {
        message.error(`${file.name} 不是规定的${props.accept}类型文件`);
        return false;
      }

      fileList.value = [...fileList.value!, file];
      return false;
    };

    const handleUpload = () => {
      if (!props.fileRefId) {
        message.success('关联业务ID为空，不能上传！');
        return false;
      }
      confirmLoading.value = true;
      okText.value = "正在上传..."

      const formData = new FormData();
      fileList.value!.forEach((file: any) => {
        formData.append('files', file as any);
      });

      formData.append("fileRefId", props.fileRefId);
      formData.append("fileType", "0");
      formData.append("isSingle", props.isSingle);

      console.log(formData);

      upload(formData).then(result => {
        confirmLoading.value = false;
        okText.value = "确认上传";

        if (result) {
          message.success('上传成功.');
          visible.value = false;
          getFileList();
        }
      }, () => {
        confirmLoading.value = false;
        okText.value = "确认上传";
      });

    };

    const setPreviewVisible = (item: any) => {
      previewVisible.value = true;
      previewUrl.value = item.fileUrl;
    };

    const setVisible = (value): void => {
      previewVisible.value = value;
    };

    const downFile = (item: any) => {
      download(item.fileId, item.fileName);
    };

    const deleteFile = (item: any) => {
      Modal.confirm({
        title: '是否确认删除?',
        icon: createVNode(ExclamationCircleOutlined),
        content: '',
        okText: '确认删除',
        okType: 'danger',
        okButtonProps: {},
        cancelText: '取消',
        onOk() {
          deleteFileApi({fileId: item.fileId}).then(result => {
            if (result)
              getFileList();
          });
        },
        onCancel() {

        },
      });
    }

    const getFileList = () => {
      if (props.fileRefId)
        getList({fileRefID: props.fileRefId}).then(result => {
          fileInfo.value = null;
          if (result && (result as []).length > 0) {
            fileInfo.value = (result as []).at(0);
          }
          dataList.value = result;
          if (props.setFileList)
            props.setFileList(dataList.value);
        });
    };

    getFileList();

    return {
      visible,
      previewVisible,
      previewUrl,
      showModal,
      fileList,
      handleRemove,
      beforeUpload,
      handleUpload,
      confirmLoading,
      okText,
      getFileList,
      downFile,
      fileInfo,dataList,
      deleteFile,
      setPreviewVisible, setVisible,DeleteOutlined
    };
  },
})
</script>
<style>

</style>

