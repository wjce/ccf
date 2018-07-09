package com.wjc.ccf.repository.custom;

import com.mongodb.WriteResult;
import com.wjc.ccf.domain.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * update方法修改如果选择使用collection名，where条件如果是id必须写成_id；
 * 如果选择Class，id可以写为id;(_id通用)
 */
@Component
public class QueryMongoDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Mongo findMongo(Long id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.where("id").is(id);
        query.addCriteria(criteria);

        return mongoTemplate.findOne(query, Mongo.class);
    }

    public List<Mongo> findMongoList(){
        return mongoTemplate.findAll(Mongo.class);
    }

    public void saveMongo(Mongo mongo){
        mongoTemplate.insert(mongo);
    }

    public void delMongo(Long id){
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Mongo.class);
    }

    public WriteResult updateMongo(Mongo mongo){
        Query query = new Query(Criteria.where("_id").is(mongo.getId()));
        Update update = new Update();
        //addToSet在该字段key中继续添加值，字段key的值则会变成数组
//        update.addToSet("key","value");
        update.set("age", mongo.getAge());
        update.set("name", mongo.getName());
        return mongoTemplate.upsert(query, update, Mongo.class);
    }

    public WriteResult updateFirstMongo(Mongo mongo){
        Query query = new Query(Criteria.where("id").is(mongo.getId()));
        Update update = new Update();
        update.set("age", mongo.getAge());
        update.set("name", mongo.getName());
        return mongoTemplate.updateFirst(query, update, Mongo.class);
    }

    public WriteResult updateFirstMongoAge(Mongo mongo){
        Query query = new Query(Criteria.where("age").is(mongo.getAge()));
        Update update = new Update();
        update.set("name", mongo.getName());
        return mongoTemplate.updateFirst(query, update, Mongo.class);
    }
}
