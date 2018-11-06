package com.wjc.ccf.repository.custom;

import com.mongodb.WriteResult;
import com.wjc.ccf.domain.Mongo;

import java.util.List;

public interface MongoDaoCustom {

    Mongo findMongo(Long id);

    List<Mongo> findList();

    void saveMongo(Mongo mongo);

    void delMongo(Long id);

    void updateMongo(Mongo mongo);

    void updateFirstMongo(Mongo mongo);

    void updateFirstMongoAge(Mongo mongo);

    List<Mongo> findMongoList();

}
