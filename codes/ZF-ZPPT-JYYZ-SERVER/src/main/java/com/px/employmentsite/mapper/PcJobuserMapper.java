package com.px.employmentsite.mapper;

import com.px.employmentsite.model.PcJobuser;
import com.px.employmentsite.model.PcJobuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PcJobuserMapper {
    long countByExample(PcJobuserExample example);

    int deleteByExample(PcJobuserExample example);

    int deleteByPrimaryKey(String jobuserID);

    int insert(PcJobuser row);

    int insertSelective(PcJobuser row);

    List<PcJobuser> selectByExample(PcJobuserExample example);

    PcJobuser selectByPrimaryKey(String jobuserID);

    int updateByExampleSelective(@Param("row") PcJobuser row, @Param("example") PcJobuserExample example);

    int updateByExample(@Param("row") PcJobuser row, @Param("example") PcJobuserExample example);

    int updateByPrimaryKeySelective(PcJobuser row);

    int updateByPrimaryKey(PcJobuser row);
}