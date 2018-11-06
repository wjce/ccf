package com.wjc.ccf.pipeline;

import com.mongodb.WriteResult;
import com.wjc.ccf.model.BookModel;
import com.wjc.ccf.model.BooksModel;
import com.wjc.ccf.repository.custom.BooksMongoDaoCustom;
import com.wjc.ccf.repository.dao.BooksMongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BooksModelPipeline implements PageModelPipeline<BooksModel> {

    @Autowired
    private BookModelPipeline bookModelPipeline;
    @Autowired
    private BooksMongoDaoCustom booksMongoDaoCustom;
    @Autowired
    private BooksMongoDao booksMongoDao;

    public String url="";
    @Override
    public void process(BooksModel booksModel, Task task) {

        System.out.println("线程名："+Thread.currentThread().getName());
        List<BooksModel> list = booksMongoDao.findAll();
        if(list.size()!=0) {
            list.forEach(t -> {
                booksMongoDaoCustom.removeCollection(t);
            });
        }
        booksModel.setUrl((List) booksModel.getUrl().stream().map(t->t=url+t).collect(Collectors.toList()));
        System.out.println(booksModel.getChapter().size());
        System.out.println("--->" + booksModel.toString());
        booksMongoDao.save(booksModel);
        saveBook(booksModel);
        Thread.currentThread().interrupt();
    }

    public void saveBook(BooksModel booksModel){
        booksModel.getUrl().forEach(t -> OOSpider.create(Site.me().setCharset("gbk").setRetryTimes(2).setSleepTime(2000), bookModelPipeline, BookModel.class).addUrl(t.toString()).thread(1).run());
    }
}
