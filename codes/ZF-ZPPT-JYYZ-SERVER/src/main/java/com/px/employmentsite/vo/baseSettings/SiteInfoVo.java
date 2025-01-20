package com.px.employmentsite.vo.baseSettings;

import com.px.employmentsite.util.datarange.annotations.InstitutionID;
import com.px.employmentsite.util.datarange.annotations.RegionID;
import com.px.employmentsite.util.datarange.annotations.SiteID;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class SiteInfoVo {

    @SiteID
    public String siteID;

    public String siteCode;
    @RegionID
    public String regionCode;
    public String regionName;
    public String streetCode;
    public String streetName;
    public String detailAddress;

    public String siteX;
    public String siteY;

    public Double siteLongitude;
    public Double siteLatitude;

    public String siteName;
    public String fzrName;
    public String fzrMobile;

    public String siteInstitutionID;
    @InstitutionID
    public String institutionID;
    public String institutionName;
    public String institutionAddress;
    public String institutionFzrName;
    public String institutionFzrMobile;
    public List<SiteUserVo> manageUsers;
    public String siteUsers;
    public String remark;

    public String createUserID;
    public Date createTime;
    public String modifyUserID;
    public String modifyUserName;
    public Date modifyTime;

}
