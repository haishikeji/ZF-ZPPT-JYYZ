package com.px.employmentsite.model;

import java.util.Date;

public class WxQrcodeScanRecord {
    private String qrcodeScanRecordId;

    private String qrcodeId;

    private String openId;

    private Integer recordStatus;

    private String createUserID;

    private Date createTime;

    private String modifyUserID;

    private Date modifyTime;

    public String getQrcodeScanRecordId() {
        return qrcodeScanRecordId;
    }

    public void setQrcodeScanRecordId(String qrcodeScanRecordId) {
        this.qrcodeScanRecordId = qrcodeScanRecordId == null ? null : qrcodeScanRecordId.trim();
    }

    public String getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(String qrcodeId) {
        this.qrcodeId = qrcodeId == null ? null : qrcodeId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
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
}