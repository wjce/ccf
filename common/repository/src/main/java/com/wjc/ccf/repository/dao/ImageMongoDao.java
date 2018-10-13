package com.wjc.ccf.repository.dao;

import com.wjc.ccf.model.ImagesModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wangjunce 2018/10/12 16:35
 */
public interface ImageMongoDao extends MongoRepository<ImagesModel, ObjectId> {
}
