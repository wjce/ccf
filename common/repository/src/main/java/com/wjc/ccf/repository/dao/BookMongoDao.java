package com.wjc.ccf.repository.dao;

import com.wjc.ccf.model.BookModel;
import com.wjc.ccf.repository.custom.BookMongoDaoCustom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookMongoDao extends MongoRepository<BookModel, ObjectId> {
}
