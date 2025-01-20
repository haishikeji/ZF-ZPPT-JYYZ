package com.px.employmentsite.mapper.cquery;

import com.px.employmentsite.vo.baseSettings.InstitutionVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface InstitutionCQuery {

    List<InstitutionVo> getList(@Param("institutionIDList") String institutionIDList, @Param("companyName") String companyName,
                                @Param("companyAddress") String companyAddress, @Param("dutyUserName") String dutyUserName);

}
