package com.wjc.ccf.web;

import com.wjc.ccf.processor.BooksPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RestController
public class BooksController {

    @Autowired
    private BooksPageProcessor booksPageProcessor;

    @GetMapping(value = "/get_books")
    public String getBooks(){
        try {
            Spider.create(booksPageProcessor)
                    .addUrl("http://www.tsxsw.com")
                    //开启5个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        }catch (Exception e){
            return "error";
        }
        return "success";
    }

}
