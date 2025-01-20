package com.px.employmentsite.controller.baseSettings;

import com.github.pagehelper.PageInfo;
import com.px.employmentsite.filter.exception.BaseErrorEnum;
import com.px.employmentsite.filter.exception.BaseException;
import com.px.employmentsite.filter.exception.BaseResponse;
import com.px.employmentsite.filter.exception.RespGenerstor;
import com.px.employmentsite.model.SelectProps;
import com.px.employmentsite.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.px.employmentsite.services.service.baseSettings.InstitutionService;
import com.px.employmentsite.services.service.AccountService;
import com.px.employmentsite.vo.baseSettings.InstitutionVo;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api/institution/")
public class InstitutionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ExcelHelper excelHelper;

    @ResponseBody
    @GetMapping("/getList")
    public BaseResponse<PageInfo<InstitutionVo>> getList(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                                                         @RequestParam(required = false) List<String> institutionIDList, @RequestParam(required = false) String companyName,
                                                         @RequestParam(required = false) String companyAddress, @RequestParam(required = false) String dutyUserName) {

        PageInfo<InstitutionVo> result = institutionService.getList(pageIndex,pageSize,institutionIDList,companyName,companyAddress,dutyUserName);

        return RespGenerstor.success(result);
    }



    @GetMapping("get")
    public BaseResponse<InstitutionVo> get(@RequestParam(required = false) String institutionID) {
        var data = institutionService.get(institutionID);
        if (data == null) {
            data = new InstitutionVo();
            data.institutionID = UUID.randomUUID().toString();
        }
        data.manageSites = null;
        return RespGenerstor.success(data);
    }

    @ResponseBody
    @GetMapping("/getDataList")
    public BaseResponse<List<SelectProps>> getDataList() {
        return RespGenerstor.success(institutionService.getDataList());
    }

    @PostMapping("save")
    public BaseResponse<Integer> save(@RequestBody InstitutionVo data) {
        return RespGenerstor.success(institutionService.save(data,accountService.getLoginUserID()));
    }

    @GetMapping("export")
    public BaseResponse export(HttpServletResponse response, @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                               @RequestParam(required = false) List<String> institutionIDList, @RequestParam(required = false) String companyName,
                               @RequestParam(required = false) String companyAddress, @RequestParam(required = false) String dutyUserName) throws Exception{

        PageInfo<InstitutionVo> result = institutionService.getList(pageIndex,pageSize,institutionIDList,companyName,companyAddress,dutyUserName);
        ExcelHelper.ExcelData data = excelHelper.new ExcelData();
        data.setTitles(Arrays.asList(new String[]{"序号","企业名称","企业地址", "负责人姓名", "负责人电话","法人姓名", "法人电话", "企业机构代码", "管理驿站数量"}));
        int i = 0;
        List<List<Object>> rowsData = new ArrayList();
        for (InstitutionVo item : result.getList()) {
            List<Object> row = new ArrayList();
            ++i;
            row.add(i);
            row.add(item.companyName);
            row.add(item.companyAddress);
            row.add(item.fzrName);
            row.add(item.fzrMobile);
            row.add(item.frName);
            row.add(item.frMobile);
            row.add(item.companyCode);
            row.add(item.siteCount);
            rowsData.add(row);
        }
        data.setRows(rowsData);
        excelHelper.exportExcel(response, data);
        return null;
    }

    @ResponseBody
    @PostMapping("/delete")
    public BaseResponse<Integer> delete(@RequestBody List<String> idList){ 
        var result = institutionService.delete(idList);
        if (result <= 0) {
            throw new BaseException(BaseErrorEnum.DELETE_NOT_DATA_ERROR);
        }
        return RespGenerstor.success(result);
    }


}
