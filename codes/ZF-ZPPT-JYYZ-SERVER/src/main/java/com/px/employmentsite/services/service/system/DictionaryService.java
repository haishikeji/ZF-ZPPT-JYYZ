package com.px.employmentsite.services.service.system;

import com.px.employmentsite.model.*;

import java.util.List;

public interface DictionaryService {
    List<SysDictionaryItem> getDictionaryItemByCodeList(String code);

    List<SysDictionaryItem> getDictionaryItemList(String code);
}
