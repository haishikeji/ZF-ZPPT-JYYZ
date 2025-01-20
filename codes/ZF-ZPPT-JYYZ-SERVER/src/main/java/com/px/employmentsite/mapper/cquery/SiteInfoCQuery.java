package com.px.employmentsite.mapper.cquery;

import com.px.employmentsite.vo.baseSettings.SiteInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SiteInfoCQuery {
    List<SiteInfoVo> getList(@Param("siteIDList") String siteIDList, @Param("siteCode") String siteCode,
                             @Param("siteName") String siteName, @Param("institutionID") String institutionID,
                             @Param("regionCode") String regionCode, @Param("streetCode") String streetCode);

    List<SiteInfoVo> getAllSite();

    List<SiteInfoVo> getListByInstitutionID(@Param("institutionID") String institutionID);

    /**
     * 查询驿站的登记企业、岗位、求职者数量
     */
    Map<String, Object> findSiteDataCount(@Param("siteID") String siteID, @Param("year") String year, @Param("month") String month);

    List<SiteInfoVo> getDataMapList(@Param("siteName") String siteName, @Param("regionCode") String regionCode,
                                    @Param("streetCode") String streetCode);
}
