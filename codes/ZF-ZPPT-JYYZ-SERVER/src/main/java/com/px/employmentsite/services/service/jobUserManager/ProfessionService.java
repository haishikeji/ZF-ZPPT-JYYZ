package com.px.employmentsite.services.service.jobUserManager;
import com.px.employmentsite.model.SysDictionaryItem;

import java.util.List;

public interface ProfessionService {

    List<SysDictionaryItem> getList();

    List<SysDictionaryItem> getListByParentProfessionID(String professionID);

    String getParentProfessionID(String professionID);
}
