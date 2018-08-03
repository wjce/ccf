package com.wjc.ccf.repository.impl;

import com.mongodb.WriteResult;
import com.wjc.ccf.domain.Mongo;
import com.wjc.ccf.repository.custom.MongoDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * update方法修改如果选择使用collection名，where条件如果是id必须写成_id；
 * 如果选择Class，id可以写为id;(_id通用)
 */
@Repository
public class MongoDaoCustomImpl implements MongoDaoCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Mongo findMongo(Long id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.where("id").is(id);
        query.addCriteria(criteria);

        return mongoTemplate.findOne(query, Mongo.class);
    }

    @Override
    public List<Mongo> findList(){
        return mongoTemplate.findAll(Mongo.class);
    }

    @Override
    public void saveMongo(Mongo mongo){
        mongoTemplate.insert(mongo);
    }

    @Override
    public void delMongo(Long id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Mongo.class);
    }

    @Override
    public WriteResult updateMongo(Mongo mongo){
        Query query = new Query(Criteria.where("_id").is(mongo.getId()));
        Update update = new Update();
        //addToSet在该字段key中继续添加值，字段key的值则会变成数组
//        update.addToSet("key","value");
        update.set("age", mongo.getAge());
        update.set("name", mongo.getName());
        return mongoTemplate.upsert(query, update, Mongo.class);
    }

    @Override
    public WriteResult updateFirstMongo(Mongo mongo){
        Query query = new Query(Criteria.where("id").is(mongo.getId()));
        Update update = new Update();
        update.set("age", mongo.getAge());
        update.set("name", mongo.getName());
        return mongoTemplate.updateFirst(query, update, Mongo.class);
    }

    @Override
    public WriteResult updateFirstMongoAge(Mongo mongo){
        Query query = new Query(Criteria.where("age").is(mongo.getAge()));
        Update update = new Update();
        update.set("name", mongo.getName());
        return mongoTemplate.updateFirst(query, update, Mongo.class);
    }
}
