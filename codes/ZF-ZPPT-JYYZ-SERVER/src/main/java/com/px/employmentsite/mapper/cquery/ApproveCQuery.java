package com.px.employmentsite.mapper.cquery;

import com.px.employmentsite.vo.system.ApproveModel;

import java.util.List;

public interface ApproveCQuery {
    List<ApproveModel> selectApproveList(String tableName, String keyID);
}
