import { defineStore } from 'pinia';
//import { useWsStore } from './ws';
import type { RouteRecordRaw } from 'vue-router';
import { store } from '@/store';
import { login } from '@/api/login';
import {ACCESS_DATA_PERMISSION_KEY, ACCESS_DATA_RANGE_KEY, ACCESS_TOKEN_KEY} from '@/enums/cacheEnum';
import { Storage } from '@/utils/Storage';
import { logout, getInfo, permmenu } from '@/api/account';
import { generatorDynamicRouter } from '@/router/generator-router';
import { resetRouter } from '@/router';
import {oauthLogin} from "@/api/oauth";

interface UserState {
  token: string;
  name: string;
  avatar: string;
  // like [ 'sys:user:add', 'sys:user:update' ]
  perms: string[];
  menus: RouteRecordRaw[];
  userInfo: Partial<API.AdminUserInfo>;
  dataRangeList: Partial<API.DataRange>[];
  avtImage: number;
}

export const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    token: Storage.get(ACCESS_TOKEN_KEY, null),
    name: 'amdin',
    avatar: '',
    perms: Storage.get(ACCESS_DATA_PERMISSION_KEY, null),
    menus: [],
    userInfo: {},
    dataRangeList: Storage.get(ACCESS_DATA_RANGE_KEY, null),
    avtImage: 0,
  }),
  getters: {
    getToken(): string {
      return this.token;
    },
    getAvatar(): string {
      return this.avatar;
    },
    getName(): string {
      return this.name;
    },
    getPerms(): string[] {
      return this.perms;
    },
    getDataRanges(): Partial<API.DataRange>[] {
      return this.dataRangeList;
    },
    getUserInfo():Partial<API.AdminUserInfo>{
      //return Storage.get(USER_INFO_KEY, null);
      return this.userInfo;
    },
    getAvtImage(): number {
      return this.avtImage;
    }
  },
  actions: {
    /** 清空token及用户信息 */
    resetToken() {
      this.avatar = this.token = this.name = '';
      this.perms = [];
      this.menus = [];
      this.userInfo = {};
      Storage.clear();
    },
    /** 登录成功保存token */
    setToken(token: string) {
      this.token = token ?? '';
      const ex = 7 * 24 * 60 * 60 * 1000;
      Storage.set(ACCESS_TOKEN_KEY, this.token, ex);
    },
    /** 登录成功保存dataRange */
    setDataRangeList(dataRangeList: Partial<API.DataRange>[]) {
      this.dataRangeList = dataRangeList;
      const ex = 7 * 24 * 60 * 60 * 1000;
      Storage.set(ACCESS_DATA_RANGE_KEY, this.dataRangeList, ex);
    },
    setUserInfo(userInfo:any){
      this.userInfo = userInfo ?? '';
      //const ex = 7 * 24 * 60 * 60 * 1000;
      //Storage.set(USER_INFO_KEY, this.userInfo,ex);
    },
    setPermissionList(dataList: string[]) {
      this.perms = dataList;
      const ex = 7 * 24 * 60 * 60 * 1000;
      Storage.set(ACCESS_DATA_PERMISSION_KEY, this.perms, ex);
    },
    /** 登录 */
    async login(params: API.LoginParams) {
      try {

        const data = await login(params);
        this.setToken(data.token);
        this.setDataRangeList(data.dataRangeList);
        this.setPermissionList(data.permissionList);
        return this.afterLogin();

      } catch (error) {
        return Promise.reject(error);
      }
    },
    /** 单点登录 */
    async oauthLogin(type: string, ticket: string,tm:string) {
      try {

        const data = await oauthLogin(type, ticket,tm);
        if (data != null && data.token != null) {
          this.setToken(data.token);
          this.setDataRangeList(data.dataRangeList);
          this.setPermissionList(data.permissionList);
          return this.afterLogin();
        }
        else{
          return Promise.reject(false);
        }
      } catch (error) {
        return Promise.reject(false);
      }
    },
    /** 登录成功之后, 获取用户信息以及生成权限路由 */
    async afterLogin() {
      try {
        //const wsStore = useWsStore();
        const [userInfo, { perms, menus }] = await Promise.all([getInfo(), permmenu()]);
        //this.perms = perms;
        this.name = userInfo.name;
        this.avatar = userInfo.headImg;
        this.userInfo = userInfo;

        this.setUserInfo(userInfo);
        // 生成路由
        const generatorResult = await generatorDynamicRouter(menus);
        this.menus = generatorResult.menus.filter((item) => !item.meta?.hideInMenu);
        //!wsStore.client && wsStore.initSocket();

        return { menus, perms, userInfo };
      } catch (error) {
        return Promise.reject(error);
        // return this.logout();
      }
    },
    /** 登出 */
    async logout() {
      await logout();
      //const wsStore = useWsStore();
      //wsStore.closeSocket();
      this.resetToken();
      resetRouter();
    },
    /** 修改头像通知 */
    setAvtImageNum() {
      this.avtImage += 1;
      console.log(this.avtImage);
    }
  },
});

// 在组件setup函数外使用
export function useUserStoreWithOut() {
  return useUserStore(store);
}
