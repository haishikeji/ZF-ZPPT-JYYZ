package com.px.employmentsite.services.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.px.employmentsite.mapper.SysLogMapper;
import com.px.employmentsite.mapper.cquery.SysLogCQuery;
import com.px.employmentsite.model.SysLog;
import com.px.employmentsite.services.service.system.LogService;
import com.px.employmentsite.vo.system.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysLogCQuery sysLogCQuery;

    @Override
    public PageInfo<LogModel> getList(Integer page, Integer rows, String loginID, String userName, Date startDate, Date endDate){
        PageHelper.startPage(page, rows);

        List<LogModel> dataList = sysLogCQuery.selectLogList(loginID, userName, startDate,endDate);

        PageInfo<LogModel> result = new PageInfo(dataList);
        return result;
    }

    public Integer save(String pageName, String pageUrl, String actionName, String userID, String ipAddress) {
        try{
            SysLog sysLog = new SysLog();
            sysLog.setLogID(UUID.randomUUID().toString());
            sysLog.setPageName(pageName);
            sysLog.setPageUrl(pageUrl);
            sysLog.setActionName(actionName);
            sysLog.setUserID(userID);
            sysLog.setLogTime(new Date());
            sysLog.setIPAddress(ipAddress);

            sysLogMapper.insert(sysLog);
        }
        catch (Exception ex){

        }

        return 1;
    }

}

