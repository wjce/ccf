package com.wjc.ccf.repository.impl;

import com.wjc.ccf.repository.custom.BookMongoDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookMongoDaoCustomImpl implements BookMongoDaoCustom {
    @Autowired
    private MongoTemplate mongoTemplate;
}
