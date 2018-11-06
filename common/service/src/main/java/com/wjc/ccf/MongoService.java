package com.wjc.ccf;

import com.mongodb.WriteResult;
import com.wjc.ccf.domain.Mongo;
import com.wjc.ccf.repository.custom.MongoDaoCustom;
import com.wjc.ccf.repository.dao.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class MongoService {
    @Autowired
    private MongoDao mongoDao;
    @Autowired
    private MongoDaoCustom mongoDaoCustom;

    @Transactional
    public Mongo findOne(Long id){
        return mongoDao.findById(id).orElse(null);
    }

    @Transactional
    public Mongo save(Mongo mongo){
        return mongoDao.save(mongo);
    }

    @Transactional(readOnly = true)
    public Page<Mongo> findList(Pageable pageable){
        return mongoDao.findAll(pageable);
    }

    @Transactional
    public Mongo update(Mongo mongo){
        Mongo oldMongo = findOne(mongo.getId());
        oldMongo.setName(mongo.getName());
        oldMongo.setAge(mongo.getAge());
        return oldMongo;
    }

    @Transactional
    public Mongo findOneByTemplate(Long id){
        return mongoDaoCustom.findMongo(id);
    }

    public List<Mongo> findMongoList(){
        return mongoDaoCustom.findList();
    }

    public void saveMongo(Mongo mongo){
        mongoDaoCustom.saveMongo(mongo);
    }

    public void delMongo(Long id){
        mongoDaoCustom.delMongo(id);
    }

    public void updateMongo(Mongo mongo){
        mongoDaoCustom.updateMongo(mongo);
    }

    public void updateFirstMongo(Mongo mongo){
        mongoDaoCustom.updateFirstMongo(mongo);
    }

    public void updateFirstMongoAge(Mongo mongo){
        mongoDaoCustom.updateFirstMongoAge(mongo);
    }

    public List<Mongo> findByAgeAndName(Integer age, String name){
        return mongoDao.findByAgeAndName(age, name);
    }

//    public void findStream(){
//        Stream<Mongo> stream = mongoDao.findAllByCustomQueryAndStream();
//        stream.forEach(mongo -> System.out.println(mongo.getAge()));
//    }
}
