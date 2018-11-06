package com.wjc.ccf.repository.custom;

import com.mongodb.WriteResult;
import com.wjc.ccf.model.BooksModel;

public interface BooksMongoDaoCustom {

    void removeCollection(BooksModel booksModel);
}
