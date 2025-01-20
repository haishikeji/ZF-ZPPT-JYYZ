package com.px.employmentsite.services.impl.system;

import com.px.employmentsite.mapper.*;
import com.px.employmentsite.model.*;
import com.px.employmentsite.services.service.system.DictionaryService;
import com.px.employmentsite.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("DictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private SysDictionaryItemMapper sysDictionaryItemMapper;
    @Autowired
    SysDictionaryItemMapper dictionaryItemMapper;

    @Autowired
    private StringUtils stringUtils;

    public List<SysDictionaryItem> getDictionaryItemByCodeList(String code) {
        SysDictionaryItemExample exp = new SysDictionaryItemExample();
        SysDictionaryItemExample.Criteria cri = exp.or();
        cri.andDictionaryCodeEqualTo(code);

        exp.setOrderByClause("orderNo");

        return sysDictionaryItemMapper.selectByExample(exp);
    }

    @Override
    @Cacheable(cacheNames = "dictionaryList")
    public List<SysDictionaryItem> getDictionaryItemList(String code) {
        SysDictionaryItemExample example = new SysDictionaryItemExample();
        example.or().andDictionaryCodeEqualTo(code);
        List<SysDictionaryItem> list = dictionaryItemMapper.selectByExample(example);
        return list;
    }
}
