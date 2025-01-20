package com.px.employmentsite.services.impl.system;

import com.px.employmentsite.mapper.AreaCodeMapper;
import com.px.employmentsite.model.AreaCode;
import com.px.employmentsite.model.AreaCodeExample;
import com.px.employmentsite.services.service.system.CityAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CityAreaService")
public class CityAreaImpl implements CityAreaService {

    @Autowired
    private AreaCodeMapper areaCodeMapper;

    @Override
    public List<AreaCode> getCityList() {
        AreaCodeExample exp=new AreaCodeExample();
        exp.or().andLvEqualTo("3");
        exp.setOrderByClause("code");
        return areaCodeMapper.selectByExample(exp);
    }

    @Override
    public List<AreaCode> getAreaList(String code) {
        AreaCodeExample exp=new AreaCodeExample();
        if(code!="") {
            exp.or().andLvEqualTo("4").andFidEqualTo(code);
        }else{
            exp.or().andLvEqualTo("4");
        }
        exp.setOrderByClause("code");
        return areaCodeMapper.selectByExample(exp);
    }
}
