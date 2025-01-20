package com.px.employmentsite.controller;

import com.px.employmentsite.filter.exception.BaseResponse;
import com.px.employmentsite.filter.exception.RespGenerstor;
import com.px.employmentsite.model.PcLongitudeLatitude;
import com.px.employmentsite.model.PcSignin;
import com.px.employmentsite.services.service.AccountService;
import com.px.employmentsite.services.service.LongitudeLatitudeService;
import com.px.employmentsite.vo.taskAndLog.PcLongitudeLatitudeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/longitudeLatitude")
public class LongitudeLatitudeController {

    @Resource
    private LongitudeLatitudeService longitudeLatitudeService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/save")
    public BaseResponse save(@RequestBody PcLongitudeLatitude data) {
        return RespGenerstor.success(longitudeLatitudeService.save(data));
    }

    @GetMapping("list")
    public BaseResponse<List<PcLongitudeLatitudeVo>> list(@RequestParam("userId") String userId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        return RespGenerstor.success(longitudeLatitudeService.list(userId, startDate, endDate));
    }

    @GetMapping("newAllList")
    public BaseResponse<List<PcLongitudeLatitudeVo>> newAllList(@RequestParam(value = "userName", required = false) String userName,
                                                                @RequestParam(value = "startDate", required = false) Date startDate,
                                                                @RequestParam(value = "regionCode", required = false) String regionCode,
                                                                @RequestParam(value = "siteID", required = false) String siteID) {
        return RespGenerstor.success(longitudeLatitudeService.newAllList(userName, startDate, regionCode, siteID));
    }

    @PostMapping("/saveSignin")
    public BaseResponse saveSignin(@RequestBody PcSignin data) {
        String loginUserID = data.getCreateUserID();
        try {
            loginUserID = accountService.getLoginUserID();
        }
        catch (Exception e){
        }
        return RespGenerstor.success(longitudeLatitudeService.saveSignin(data,loginUserID));
    }

}
