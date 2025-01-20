package com.px.employmentsite.model;

import java.util.Date;

public class PcCreditRecord {
    private String creditRecordID;

    private String companyID;

    private Date enterTime;

    private Date includeTime;

    private Date expirationTime;

    private String documentNumber;

    private String decidingDepartment;

    private String source;

    private Integer isEffective;

    private Integer departmentTypeID;

    private String createUserID;

    private Date createTime;

    private String modifyUserID;

    private Date modifyTime;

    private String creditRecordContent;

    public String getCreditRecordID() {
        return creditRecordID;
    }

    public void setCreditRecordID(String creditRecordID) {
        this.creditRecordID = creditRecordID == null ? null : creditRecordID.trim();
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID == null ? null : companyID.trim();
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber == null ? null : documentNumber.trim();
    }

    public Date getIncludeTime() {
        return includeTime;
    }

    public void setIncludeTime(Date includeTime) {
        this.includeTime = includeTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getDecidingDepartment() {
        return decidingDepartment;
    }

    public void setDecidingDepartment(String decidingDepartment) {
        this.decidingDepartment = decidingDepartment == null ? null : decidingDepartment.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Integer getDepartmentTypeID() {
        return departmentTypeID;
    }

    public void setDepartmentTypeID(Integer departmentTypeID) {
        this.departmentTypeID = departmentTypeID;
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

    public String getCreditRecordContent() {
        return creditRecordContent;
    }

    public void setCreditRecordContent(String creditRecordContent) {
        this.creditRecordContent = creditRecordContent == null ? null : creditRecordContent.trim();
    }
}