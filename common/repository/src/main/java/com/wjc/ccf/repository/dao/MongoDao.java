package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.Mongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MongoDao extends MongoRepository<Mongo, Long> {
}
