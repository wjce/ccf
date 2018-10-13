package com.wjc.ccf.repository.dao;

import com.wjc.ccf.model.BooksModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BooksMongoDao extends MongoRepository<BooksModel,ObjectId> {
}
