import { defineStore } from 'pinia';
//import { useWsStore } from './ws';
import { store } from '@/store';
import { login } from '@/api/login';
import {ACCESS_OpenId_KEY, ACCESS_TOKEN_KEY, USER_INFO_KEY} from '@/enums/cacheEnum';
import { Storage } from '@/utils/Storage';
import { logout, getInfo, permmenu } from '@/api/account';
import {oauthLogin} from "@/api/oauth";
import {alertController} from "@ionic/vue";

interface UserState {
  token: string;
  name: string;
  avatar: string;
  // like [ 'sys:user:add', 'sys:user:update' ]
  perms: string[];
  menus: API.Menu[];
  userInfo: Partial<API.AdminUserInfo>;
  timer: any,
  openId: string
}

export const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    token: Storage.get(ACCESS_TOKEN_KEY, null),
    name: 'amdin',
    avatar: '',
    perms: [],
    menus: [],
    userInfo: Storage.get(USER_INFO_KEY, null),
    timer: null,
    openId:''
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
    getUserInfo():Partial<API.AdminUserInfo>{
      return this.userInfo;
    },
    getOpenId(): string {
      return this.openId;
    },
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
    setUserInfo(userInfo:any){
      this.userInfo = userInfo ?? '';
      const ex = 7 * 24 * 60 * 60 * 1000;
      Storage.set(USER_INFO_KEY, this.userInfo,ex);
    },
    setOpenId(openId: string) {
      this.openId = openId ?? '';
      const ex = 7 * 24 * 60 * 60 * 1000;
      Storage.set(ACCESS_OpenId_KEY, this.openId, ex);
    },
    /** 登录 */
    async login(params: API.LoginParams) {
      try {

        const data = await login(params);
        this.setToken(data.token);
        return this.afterLogin(data);

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
                return this.afterLogin(data);
            }
            else{
                return Promise.reject(false);
            }
        } catch (error) {
            return Promise.reject(false);
        }
    },
    /** 登录成功之后, 获取用户信息以及生成权限路由 */
    async afterLogin(loginResult:any) {
      try {
        //const wsStore = useWsStore();
        const [userInfo, { perms,menus }] = await Promise.all([getInfo(), permmenu()]);
        userInfo.loginResult = loginResult;
        this.perms = perms;
        this.name = userInfo.name;
        /*this.avatar = userInfo.headImg;*/
        this.userInfo = userInfo;
        this.menus = menus;

        this.setUserInfo(userInfo);
        // 生成路由
        //const generatorResult = await generatorDynamicRouter(menus);
        //this.menus = generatorResult.menus.filter((item) => !item.meta?.hideInMenu);
        //!wsStore.client && wsStore.initSocket();

        return { userInfo };
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
      this.clearTimer();
      //resetRouter();
    },
    /**
     * 设置获取经纬度定时器
     */
    setTimer(timer: any) {
      this.timer = timer
    },
    /**
     * 清除经纬度定时器
     */
    clearTimer(){
      if (this.timer) {
        clearTimeout(this.timer);
      }
    }
  },
});

// 在组件setup函数外使用
export function useUserStoreWithOut() {
  return useUserStore(store);
}
