package com.px.employmentsite.model;

import java.util.Date;

public class PcSiteUser {
    private String siteUserID;

    private String siteUserName;

    private String userID;

    private String siteID;

    private Integer gender;

    private String mobile;

    private Integer roleID;

    private String createUserID;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String userNo;

    private String IDCard;

    private String SocialSecurityCard;

    private Integer Age;

    private String FinishSchool;

    private String Profession;

    private String NativePlace;

    private String Email;

    private String Address;

    private Integer Nation;

    private Integer PoliticsStatusID;

    private Integer CultureRank;

    private Integer IsFullTime;

    private String OccupationalCategory;

    private Integer OccupationalLevel;

    public String getSiteUserID() {
        return siteUserID;
    }

    public void setSiteUserID(String siteUserID) {
        this.siteUserID = siteUserID == null ? null : siteUserID.trim();
    }

    public String getSiteUserName() {
        return siteUserName;
    }

    public void setSiteUserName(String siteUserName) {
        this.siteUserName = siteUserName == null ? null : siteUserName.trim();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID == null ? null : siteID.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard == null ? null : IDCard.trim();
    }

    public String getSocialSecurityCard() {
        return SocialSecurityCard;
    }

    public void setSocialSecurityCard(String socialSecurityCard) {
        SocialSecurityCard = socialSecurityCard;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public Integer getNation() {
        return Nation;
    }

    public void setNation(Integer nation) {
        Nation = nation;
    }

    public Integer getPoliticsStatusID() {
        return PoliticsStatusID;
    }

    public void setPoliticsStatusID(Integer politicsStatusID) {
        PoliticsStatusID = politicsStatusID;
    }

    public Integer getCultureRank() {
        return CultureRank;
    }

    public void setCultureRank(Integer cultureRank) {
        CultureRank = cultureRank;
    }

    public String getFinishSchool() {
        return FinishSchool;
    }

    public void setFinishSchool(String finishSchool) {
        FinishSchool = finishSchool;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public Integer getIsFullTime() {
        return IsFullTime;
    }

    public void setIsFullTime(Integer isFullTime) {
        IsFullTime = isFullTime;
    }

    public String getOccupationalCategory() {
        return OccupationalCategory;
    }

    public void setOccupationalCategory(String occupationalCategory) {
        OccupationalCategory = occupationalCategory;
    }

    public Integer getOccupationalLevel() {
        return OccupationalLevel;
    }

    public void setOccupationalLevel(Integer occupationalLevel) {
        OccupationalLevel = occupationalLevel;
    }

    public String getNativePlace() {
        return NativePlace;
    }

    public void setNativePlace(String nativePlace) {
        NativePlace = nativePlace;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}