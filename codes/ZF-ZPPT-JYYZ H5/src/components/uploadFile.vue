<template>
    <ion-item v-if="!readonly">
      <ion-thumbnail slot="start">
        <img :src="takenImageUrl" />
      </ion-thumbnail>
      <ion-button type="button" fill="clear" @click="takePicture">
        <ion-icon slot="end" :icon="camera"></ion-icon>
        Take Photo
      </ion-button>
    </ion-item>
</template>
<script lang="ts">
import {defineComponent, ref} from "vue";
import {camera,cameraOutline} from "ionicons/icons";
import {alertController, IonIcon, IonThumbnail} from '@ionic/vue';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import {uploadBase64} from '@/api/system/file';
import {presentAlert} from "@/api/common";

export default defineComponent({
      name: 'b-upload-file',
      components: {IonIcon},
      props: {
        fileRefId: {type: String, default: ''},
        readonly: {type: Boolean, default: true},
        accept: {
          type: String,
          default: 'image/png, image/jpeg,image/jpg,image/gif,doc,docx,xls,xlsx,ppt,pptx,pdf,zip,txt',
        },
      },
      setup(props) {
        const takenImageUrl = ref();

        const takePicture = async () => {
          const image = await Camera.getPhoto({
            quality: 90,
            allowEditing: true,
            source: CameraSource.Camera,
            // resultType: CameraResultType.DataUrl,
            resultType: CameraResultType.Uri,
          });
          const imageUrl = image.webPath;
          takenImageUrl.value = imageUrl;
          console.log(image)
          //
          // if (!props.accept?.split(',').includes(image.format)){
          //   presentAlert("只能上传格式为："+props.accept+"的文件");
          //   return null;
          // }
          //
          // const formData = new FormData();
          // formData.append('base64Str', image.dataUrl as any);
          // formData.append("fileRefId", props.fileRefId);
          // formData.append("fileType", "0");
          // console.log(formData);
          // uploadBase64(formData).then((result)=>{
          //   if (result) {
          //     presentAlert("上传成功");
          //   }
          // })
        };

        return {
          camera,
          IonThumbnail,
          takenImageUrl,
          takePicture
        }
      }
    }
);
</script>