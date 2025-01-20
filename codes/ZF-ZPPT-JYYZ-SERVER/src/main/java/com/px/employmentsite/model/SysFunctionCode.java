package com.px.employmentsite.model;

public class SysFunctionCode {
    private String functionCode;

    private String functionName;

    private String parentFunctionCode;

    private Integer orderNo;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public String getParentFunctionCode() {
        return parentFunctionCode;
    }

    public void setParentFunctionCode(String parentFunctionCode) {
        this.parentFunctionCode = parentFunctionCode == null ? null : parentFunctionCode.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}