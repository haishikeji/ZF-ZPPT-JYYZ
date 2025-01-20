package com.px.employmentsite.services.impl.system;

import com.px.employmentsite.mapper.SysAnnouncementMapper;
import com.px.employmentsite.mapper.SysAnnouncementSysRoleMapper;
import com.px.employmentsite.mapper.cquery.AnnouncementCQuery;
import com.px.employmentsite.model.*;
import com.px.employmentsite.services.service.AccountService;
import com.px.employmentsite.services.service.system.AnnouncementService;
import com.px.employmentsite.vo.system.AnnouncementModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementCQuery announcementCQuery;
    @Autowired
    private SysAnnouncementMapper announcementMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SysAnnouncementSysRoleMapper announcementRoleMapper;

    @Override
    public PageInfo<AnnouncementModel> getAnnouncementList(int page, int rows, String userID,String title,String beiginDate,String endDate) {
        PageHelper.startPage(page, rows);

        List<AnnouncementModel> dataList = announcementCQuery.selectAnnouncementList(userID,title,beiginDate,endDate);

        PageInfo<AnnouncementModel> result = new PageInfo(dataList);
        return result;
    }
    @Override
    public PageInfo<AnnouncementModel> getUserAnnouncementList(int page, int rows, String userID,String title) {
        PageHelper.startPage(page, rows);

        List<AnnouncementModel> dataList = announcementCQuery.selectUserAnnouncementList(userID,title);

        PageInfo<AnnouncementModel> result = new PageInfo(dataList);
        return result;
    }
    @Override
    public SysAnnouncement getAnnouncement(String id){
        return announcementMapper.selectByPrimaryKey(id);
    }
    @Override
    public List<String> getAnnouncementRoles(String id){
        SysAnnouncementSysRoleExample example = new SysAnnouncementSysRoleExample();
        example.or().andAnnouncementIDEqualTo(id);
        return announcementRoleMapper.selectByExample(example).stream().map(x->x.getRoleID()).collect(Collectors.toList());
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(List<String> idList) {
        SysAnnouncementSysRoleExample example = new SysAnnouncementSysRoleExample();
        example.or().andAnnouncementIDIn(idList);
        announcementRoleMapper.deleteByExample(example);

        SysAnnouncementExample exp=new SysAnnouncementExample();
        exp.or().andAnnouncementIDIn(idList);
        announcementMapper.deleteByExample(exp);
        return  1;
    }
    @Override
    public int save(SysAnnouncement model,
                    List<String> announRoleList){
        SysAnnouncement dbData= announcementMapper.selectByPrimaryKey(model.getAnnouncementID());
        if (dbData == null) {
            model.setCreateBy(accountService.getLoginUserID());
            model.setCreateTime(new Date());
            announcementMapper.insert(model);
        } else {
            model.setUpdateBy(accountService.getLoginUserID());
            model.setUpdateTime(new Date());
            announcementMapper.updateByPrimaryKey(model);
        }
        if (announRoleList != null && announRoleList.size() > 0) {
            SysAnnouncementSysRoleExample example = new SysAnnouncementSysRoleExample();
            example.or().andAnnouncementIDEqualTo(model.getAnnouncementID());
            announcementRoleMapper.deleteByExample(example);
            announRoleList.stream().forEach(x -> {
                SysAnnouncementSysRoleKey row=new SysAnnouncementSysRoleKey();
                row.setAnnouncementID(model.getAnnouncementID());
                row.setRoleID(x);
                announcementRoleMapper.insertSelective(row);
            });
        }
        return 1;
    }
}
