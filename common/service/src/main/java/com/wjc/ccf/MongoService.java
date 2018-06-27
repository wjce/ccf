package com.wjc.ccf;

import com.wjc.ccf.domain.Mongo;
import com.wjc.ccf.repository.dao.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MongoService {
    @Autowired
    private MongoDao mongoDao;

    @Transactional
    public Mongo save(Mongo mongo){
        return mongoDao.save(mongo);
    }

    @Transactional(readOnly = true)
    public Page<Mongo> findList(Pageable pageable){
        return mongoDao.findAll(pageable);
    }
}
