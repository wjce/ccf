package com.wjc.ccf;

import com.wjc.ccf.domain.BaseArea;
import com.wjc.ccf.repository.dao.BaseAreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjc
 * @description
 * @date 2019/9/7
 */
@Transactional
@Service
public class BaseAreaService {
    @Autowired
    private BaseAreaDao baseAreaDao;

    public BaseArea findByNameAndCode(String name, String code){
        return baseAreaDao.findByNameAndCode(name, code);
    }

    public BaseArea findByNameLikeAndCode(String name){
        return baseAreaDao.findByNameLike(name);
    }
}
