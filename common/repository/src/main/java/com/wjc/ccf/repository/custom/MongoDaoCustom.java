package com.wjc.ccf.repository.custom;

import com.mongodb.WriteResult;
import com.wjc.ccf.domain.Mongo;

import java.util.List;

public interface MongoDaoCustom {

    Mongo findMongo(Long id);

    List<Mongo> findList();

    void saveMongo(Mongo mongo);

    void delMongo(Long id);

    WriteResult updateMongo(Mongo mongo);

    WriteResult updateFirstMongo(Mongo mongo);

    WriteResult updateFirstMongoAge(Mongo mongo);

    List<Mongo> findMongoList();

}
