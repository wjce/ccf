package com.wjc.ccf;

import com.wjc.ccf.domain.CtripCity;
import com.wjc.ccf.repository.dao.CtripCityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CtripCityService {

    @Autowired
    private CtripCityDao ctripCityDao;

    public List<CtripCity> findAll(){
        return ctripCityDao.findAll();
    }

}

