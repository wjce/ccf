package com.wjc.ccf.repository.impl;

import com.mongodb.WriteResult;
import com.wjc.ccf.model.BooksModel;
import com.wjc.ccf.repository.custom.BooksMongoDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BooksMongoDaoCustomImpl implements BooksMongoDaoCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void removeCollection(BooksModel booksModel) {
        mongoTemplate.remove(booksModel, "tb_books");
    }
}
