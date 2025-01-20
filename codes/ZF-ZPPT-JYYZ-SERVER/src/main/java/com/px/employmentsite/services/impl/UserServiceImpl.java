package com.px.employmentsite.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.px.employmentsite.enums.DataRangeEnum;
import com.px.employmentsite.filter.exception.BaseException;
import com.px.employmentsite.mapper.*;
import com.px.employmentsite.mapper.cquery.SysUserCQuery;
import com.px.employmentsite.model.*;
import com.px.employmentsite.services.service.AccountService;
import com.px.employmentsite.services.service.UserService;
import com.px.employmentsite.services.service.system.RoleService;
import com.px.employmentsite.util.DateUtils;
import com.px.employmentsite.util.StringUtils;
import com.px.employmentsite.util.ip.IpUtils;
import com.px.employmentsite.vo.user.DataRange;
import com.px.employmentsite.vo.user.UserEditVo;
import com.px.employmentsite.vo.user.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    SysUserCQuery sysUserCQuery;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private StringUtils stringUtils;

    @Autowired
    private SysUserDatarangeMapper sysUserDatarangeMapper;

    @Autowired
    private PcSiteUserMapper pcSiteUserMapper;

    @Autowired
    private SysUserSysRoleMapper sysUserSysRoleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SysLogMapper sysLogMapper;

    private String defaultPassword = "52653B706ADC444F6660FAB66D6DC210";//Hjy@2024

    private String siteRoleId = "10a12f77-0958-4220-b9ce-07b9215046f2"; //驿站工作者


    @Override
    public List<SysRole> getUserRole(String userID) {
        return null;
    }

    @Override
    public List<DataRange> getUserDataRange(String userID) {
        List<HashMap<String, Object>> dataRangeList = sysUserCQuery.selectUserDataRange(userID);
        List<String> userCompanyList = sysUserCQuery.selectUserCompany(userID);
        List<String> userSiteList = sysUserCQuery.selectUserSite(userID);
        List<String> userInstitutionList = sysUserCQuery.selectUserInstitution(userID);
        List<String> userRegionList = sysUserCQuery.selectUserRegion(userID);
        List<String> userCityList = sysUserCQuery.selectUserCity(userID);
        List<DataRange> result = new ArrayList<>();
        Map<String, List<HashMap<String, Object>>> menuGrouping = dataRangeList.stream()
                .collect(Collectors.groupingBy(x -> x.get("MenuNo").toString()));
        for (String key : menuGrouping.keySet()) {
            List<HashMap<String, Object>> valueList = menuGrouping.get(key);
            valueList.sort(Comparator.comparing(x -> ((Long) x.get("OrderNo")).intValue()));
            int minOrderNo = valueList.stream()
                    .map(x -> ((Long) x.get("OrderNo")).intValue())
                    .min(Integer::compareTo).orElse(0);

            for (HashMap<String, Object> value : valueList) {
                if (((Long) value.get("OrderNo")).intValue() == minOrderNo) {
                    int dataRangeID = (int) value.get("DataRangeID");

                    DataRange menuDataRange = new DataRange();
                    menuDataRange.setMenuNo(key);
                    menuDataRange.setRange(dataRangeID);
                    DataRangeEnum dataRangeEnum = DataRangeEnum.getDataRangeByValue(dataRangeID);

                    switch (dataRangeEnum) {
                        case ALL:
                            break;
                        case City:
                            menuDataRange.setRangeIDList(userCityList);
                            break;
                        case Region:
                            menuDataRange.setRangeIDList(userRegionList);
                            break;
                        case Institution:
                            menuDataRange.setRangeIDList(userInstitutionList);
                            break;
                        case Site:
                            menuDataRange.setRangeIDList(userSiteList);
                            break;
                        case Company:
                            menuDataRange.setRangeIDList(userCompanyList);
                            break;
                        case SELF:
                            menuDataRange.setRangeIDList(List.of(userID));
                            break;
                        default:
                            break;
                    }
                    result.add(menuDataRange);
                }
            }
        }

        return result;
    }


    @Override
    public PageInfo<HashMap<String, Object>> getUserList(int page, int rows, String name, String loginID, Integer sex, Integer recordStatus, Integer userType) {

        PageHelper.startPage(page, rows);
        List<HashMap<String, Object>> list = sysUserCQuery.selectUserList(name, loginID, sex, recordStatus, userType);

        PageInfo<HashMap<String, Object>> result = new PageInfo(list);

        return result;
    }

    @Override
    public int updateUserStatus(String userId, Integer status) {

        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        user.setRecordStatus(status);
        return sysUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public UserInfoModel getUserInfo(String userId) {
        if (stringUtils.IsNullOrEmpty(userId)) {
            return null;
        } else {
            return sysUserCQuery.selectUserInfo(userId);
        }
    }

    @Override
    public Integer save(UserEditVo data, String userId) throws Exception {
        AtomicInteger result = new AtomicInteger();
        if (data.getUserTypeID().equals(3) && data.isAdd) {//驿站工作者
            if (data.getSiteUserIdList() != null && data.getSiteUserIdList().size() > 0) {
                PcSiteUserExample exp = new PcSiteUserExample();
                PcSiteUserExample.Criteria cri = exp.or();
                cri.andSiteUserIDIn(data.getSiteUserIdList());

                List<PcSiteUser> pcSiteUserList = pcSiteUserMapper.selectByExample(exp);
                List<String> userIdList = new ArrayList<>();

                pcSiteUserList.forEach(siteUser -> {
                    SysUserExample uExp = new SysUserExample();
                    SysUserExample.Criteria uCri = uExp.or();
                    uCri.andLoginIDEqualTo(siteUser.getUserNo());
                    SysUser dbUser = sysUserMapper.selectByExample(uExp).stream().findFirst().orElse(null);
                    if (dbUser == null) {
                        dbUser = new SysUser();
                        dbUser.setUserID(UUID.randomUUID().toString());
                        dbUser.setName(siteUser.getSiteUserName());
                        dbUser.setLoginID(siteUser.getUserNo());
                        dbUser.setPassword(data.getPassword() == null ? defaultPassword : DigestUtils.md5DigestAsHex(data.getPassword().getBytes()).toUpperCase());
                        dbUser.setUserTypeID(data.getUserTypeID());
                        dbUser.setRecordStatus(data.getRecordStatus());
                        dbUser.setCreateBy(userId);
                        dbUser.setCreateTime(new Date());
                        result.set(result.get() + sysUserMapper.insert(dbUser));

                        userIdList.add(dbUser.getUserID());
                    }

                    siteUser.setUserID(dbUser.getUserID());
                    pcSiteUserMapper.updateByPrimaryKey(siteUser);
                });

                roleService.saveRoleUser(siteRoleId, userIdList);

            }
        } else {
            SysUserExample uExp = new SysUserExample();
            SysUserExample.Criteria uCri = uExp.or();
            uCri.andLoginIDEqualTo(data.getLoginID());

            SysUser dbUser = sysUserMapper.selectByExample(uExp).stream().findFirst().orElse(null);
            if (dbUser != null && !dbUser.getUserID().equals(data.getUserID()))
                throw new Exception("该账号已录入系统，请勿重复录入");


            UserInfoModel record = getUserInfo(data.getUserID());
            SysUser dbData = record == null ? null : sysUserMapper.selectByPrimaryKey(record.userID);
            Boolean isExist = dbData != null;
            if (!isExist) {
                //添加
                dbData = new SysUser();
                dbData.setUserID(data.getUserID());
                dbData.setName(data.getName());
                dbData.setLoginID(data.getLoginID());
                if (data.getPassword() == null) {
                    dbData.setPassword(defaultPassword);
                } else {
                    dbData.setPassword(DigestUtils.md5DigestAsHex(data.getPassword().getBytes()).toUpperCase());
                }
                dbData.setUserTypeID(data.getUserTypeID());
                dbData.setRecordStatus(data.getRecordStatus());
                dbData.setCreateBy(userId);
                dbData.setCreateTime(new Date());
                result.set(sysUserMapper.insert(dbData));
            } else {
                //修改
                dbData.setName(data.getName());
                /*dbData.setLoginID(data.getLoginID());*/
                /*if (data.getPassword() == null) {
                    dbData.setPassword(defaultPassword);
                } else {
                    dbData.setPassword(DigestUtils.md5DigestAsHex(data.getPassword().getBytes()).toUpperCase());
                }*/
                /*dbData.setUserTypeID(data.getUserTypeID());*/
                dbData.setRecordStatus(data.getRecordStatus());
                dbData.setUpdateBy(userId);
                dbData.setUpdateTime(new Date());
                result.set(sysUserMapper.updateByPrimaryKey(dbData));
            }

            SysUserDatarangeExample udExp = new SysUserDatarangeExample();
            SysUserDatarangeExample.Criteria udCri = udExp.or();
            udCri.andUserIdEqualTo(data.getUserID());
            sysUserDatarangeMapper.deleteByExample(udExp);

            if (data.datarangeIdList != null) {

                data.datarangeIdList.forEach(datarangeId -> {
                    SysUserDatarangeKey sysUserDatarange = new SysUserDatarangeKey();
                    sysUserDatarange.setUserId(data.getUserID());
                    sysUserDatarange.setDatarangeId(datarangeId);

                    sysUserDatarangeMapper.insert(sysUserDatarange);
                });

            }
        }

        return result.get();
    }

    @Override
    public SysUser getIsCacheUserInfoByID(String userId) {

        SysUser userInfoVo = sysUserMapper.selectByPrimaryKey(userId);

        if (userInfoVo == null) {
            userInfoVo = new SysUser();
            userInfoVo.setName("");
            userInfoVo.setUserID("");
        }
        return userInfoVo;
    }

    @Override
    public String getRandomLoginID() {
        String loginID = randomLoginID();

        Integer count = 1; //验证次数
        while (count <= 3) {
            count--;
            //验证帐号是否存在
            if (sysUserCQuery.selectUserByLoginID(loginID).size() > 0) {
                loginID = randomLoginID();
            } else {
                return loginID;
            }
        }

        return loginID;
    }

    private String randomLoginID() {
        return dateUtils.dateToStrFormat(new Date(), "yyyyMMdd") + UUID.randomUUID().toString().substring(0, 4);
    }

    @Override
    public SysUser addUser(String loginID, String userName, Integer userTypeID, String createBy) {

        if (sysUserCQuery.selectUserByLoginID(loginID).size() > 0)
            return null;

        SysUser user = new SysUser();
        user.setUserID(UUID.randomUUID().toString());
        user.setLoginID(loginID);
        user.setName(userName);
        user.setUserTypeID(userTypeID);
        user.setRecordStatus(1);
        user.setCreateBy(createBy);
        user.setCreateTime(new Date());
        user.setPassword(this.defaultPassword);

        sysUserMapper.insert(user);

        return user;

    }

    @Override
    public Integer updateUser(String userID, String userName, String updateBy) {
        SysUser user = sysUserMapper.selectByPrimaryKey(userID);
        user.setName(userName);
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        return sysUserMapper.updateByPrimaryKey(user);
    }

    public Integer updatePassword(HttpServletRequest request, String userID, String newPassword, String updateBy) throws Exception {
        SysUser user = sysUserMapper.selectByPrimaryKey(userID);
        if(newPassword.equals(user.getPassword())){
            throw new BaseException("100001", "新密码不能与旧密码相同");
        }
        user.setPassword(newPassword);
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        Integer result = sysUserMapper.updateByPrimaryKey(user);
        // 记录日志
        if (result > 0) {
            SysLog sysLog = new SysLog();
            sysLog.setLogID(UUID.randomUUID().toString());
            sysLog.setPageName("用户列表");
            sysLog.setPageUrl("/sys/permission/userslist/updatePassword");
            sysLog.setActionName("修改密码");
            sysLog.setUserID(accountService.getLoginUserID());
            sysLog.setLogTime(new Date());
            sysLog.setIPAddress(IpUtils.getIpAddr(request));
            sysLog.setOperationData("修改了" + user.getName() + "(" + user.getLoginID() + ")" + "的登录密码");
            sysLogMapper.insert(sysLog);
        }
        return result;
    }

    @Override
    public List<SysMenu> getUserShortcutMenuList(String userID) {
        return sysUserCQuery.selectUserShortcutMenuList(userID);
    }

    public SysUser getUserByLoginID(String loginID) {
        SysUserExample exp = new SysUserExample();
        SysUserExample.Criteria criteria = exp.or();
        criteria.andLoginIDEqualTo(loginID);

        return sysUserMapper.selectByExample(exp).stream().findFirst().orElse(null);
    }

    /*
     * 添加驿站工作者
     * @params loginId,name
     */
    @Override
    public SysUser addSiteUser(String userId, String loginId, String name, String createUserId) {
        if (stringUtils.IsNullOrEmpty(loginId))
            return null;

        SysUser dbUserByUserId = null;
        SysUser dbUserByLoginId = null;
        if (!stringUtils.IsNullOrEmpty(userId)) {
            dbUserByUserId = sysUserMapper.selectByPrimaryKey(userId);
        }
        if (!stringUtils.IsNullOrEmpty(loginId)) {
            SysUserExample uExp = new SysUserExample();
            SysUserExample.Criteria uCri = uExp.or();
            uCri.andLoginIDEqualTo(loginId);
            dbUserByLoginId = sysUserMapper.selectByExample(uExp).stream().findFirst().orElse(null);
        }

        if (dbUserByUserId != null && dbUserByLoginId != null && !dbUserByUserId.getUserID().equals(dbUserByLoginId.getUserID())) {
            return null;
        }

        SysUser dbData = dbUserByUserId != null ? dbUserByUserId : dbUserByLoginId;

        if (dbData == null) {
            dbData = new SysUser();
            dbData.setUserID(UUID.randomUUID().toString());
            dbData.setName(name);
            dbData.setLoginID(loginId);
            dbData.setPassword(defaultPassword);
            dbData.setUserTypeID(3);
            dbData.setRecordStatus(1);
            dbData.setCreateBy(createUserId);
            dbData.setCreateTime(new Date());
            sysUserMapper.insert(dbData);
        } else {
            dbData.setName(name);
            dbData.setLoginID(loginId);
            dbData.setUpdateBy(createUserId);
            dbData.setUpdateTime(new Date());

            sysUserMapper.updateByPrimaryKey(dbData);
        }

        roleService.saveRoleUser(siteRoleId, Arrays.asList(dbData.getUserID()));

        return dbData;
    }

    public boolean isSiteUser(String userId) {
        SysUserSysRoleExample exp = new SysUserSysRoleExample();
        SysUserSysRoleExample.Criteria cri = exp.or();
        cri.andUserIDEqualTo(userId);
        cri.andRoleIDEqualTo(siteRoleId);

        return sysUserSysRoleMapper.selectByExample(exp).size() > 0;
    }

    @Override
    public String getDefaultPassword(){
        return this.defaultPassword;
    }

}
