package com.px.employmentsite.model;

public class PcProfession {
    private String professionID;

    private String parentProfessionID;

    private String professionName;

    private Integer orderNo;

    private Integer status;

    public String getProfessionID() {
        return professionID;
    }

    public void setProfessionID(String professionID) {
        this.professionID = professionID == null ? null : professionID.trim();
    }

    public String getParentProfessionID() {
        return parentProfessionID;
    }

    public void setParentProfessionID(String parentProfessionID) {
        this.parentProfessionID = parentProfessionID == null ? null : parentProfessionID.trim();
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName == null ? null : professionName.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}