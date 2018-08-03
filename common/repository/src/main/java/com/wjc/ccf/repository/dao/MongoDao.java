package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.Mongo;
import com.wjc.ccf.repository.custom.MongoDaoCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface MongoDao extends MongoRepository<Mongo, Long> {

    List<Mongo> findByAgeAndName(Integer age, String name);

//    Stream<Mongo> findAllByAge(Integer age);

//    @Query("select m from Mongo m")
//    Stream<Mongo> findAllByCustomQueryAndStream();
}
