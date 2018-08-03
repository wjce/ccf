package com.wjc.ccf.pipeline;

import com.wjc.ccf.model.BookModel;
import com.wjc.ccf.repository.dao.BookMongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

@Component
public class BookModelPipeline implements PageModelPipeline<BookModel> {

    @Autowired
    private BookMongoDao bookMongoDao;

    @Override
    public void process(BookModel bookModel, Task task) {
        System.out.println("book线程名："+Thread.currentThread().getName());
        bookMongoDao.save(bookModel);
    }
}
