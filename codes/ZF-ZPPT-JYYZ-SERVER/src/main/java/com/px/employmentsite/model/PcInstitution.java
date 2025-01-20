package com.px.employmentsite.model;

import java.util.Date;

public class PcInstitution {
    private String institutionID;

    private String companyName;

    private String companyAddress;

    private String fzrName;

    private String fzrMobile;

    private String frName;

    private String frMobile;

    private String companyCode;

    private String companyDesc;

    private String remark;

    private String createUserID;

    private Date createTime;

    private String modifyUserID;

    private Date modifyTime;

    private String signInPoliticalArea;

    private Date validDate;

    private Integer economicTypeID;

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID == null ? null : institutionID.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getFzrName() {
        return fzrName;
    }

    public void setFzrName(String fzrName) {
        this.fzrName = fzrName == null ? null : fzrName.trim();
    }

    public String getFzrMobile() {
        return fzrMobile;
    }

    public void setFzrMobile(String fzrMobile) {
        this.fzrMobile = fzrMobile == null ? null : fzrMobile.trim();
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName == null ? null : frName.trim();
    }

    public String getFrMobile() {
        return frMobile;
    }

    public void setFrMobile(String frMobile) {
        this.frMobile = frMobile == null ? null : frMobile.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc == null ? null : companyDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID == null ? null : createUserID.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUserID() {
        return modifyUserID;
    }

    public void setModifyUserID(String modifyUserID) {
        this.modifyUserID = modifyUserID == null ? null : modifyUserID.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSignInPoliticalArea() {
        return signInPoliticalArea;
    }

    public void setSignInPoliticalArea(String signInPoliticalArea) {
        this.signInPoliticalArea = signInPoliticalArea;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getEconomicTypeID() {
        return economicTypeID;
    }

    public void setEconomicTypeID(Integer economicTypeID) {
        this.economicTypeID = economicTypeID;
    }
}