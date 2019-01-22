package com.wjc.ccf.web;

import com.wjc.ccf.WebmagicUrlService;
import com.wjc.ccf.domain.WebmagicUrl;
import com.wjc.ccf.processor.BaiDuArticlePageProcessor;
import com.wjc.ccf.processor.BaiDuPageProcessor;
import com.wjc.ccf.processor.BaiDuPhoneArticlePageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * @author wangjunce 2018/10/19 11:00
 */
@RestController
public class BaiDuController {

    @Autowired
    private BaiDuPageProcessor baiDuPageProcessor;
    @Autowired
    private WebmagicUrlService webmagicUrlService;
    @Autowired
    private BaiDuArticlePageProcessor baiDuArticlePageProcessor;
    @Autowired
    private BaiDuPhoneArticlePageProcessor baiDuPhoneArticlePageProcessor;

    @GetMapping(value = "get_bd_data")
    public String getData(){

        WebmagicUrl webmagicUrl = webmagicUrlService.findUrl(1l);

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("171.39.74.116",8123),
                new Proxy("117.191.11.79",8080),
                new Proxy("39.137.69.10", 80),
                new Proxy("180.114.12.225", 4154),
                new Proxy("121.8.98.196", 8080),
                new Proxy("39.137.2.210", 8080)
        ));

        try {
            Spider.create(baiDuPageProcessor)
                    .addUrl(webmagicUrl.getUrl())
                    .setDownloader(httpClientDownloader)
                    //开启x个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }


    @GetMapping(value = "/get_article")
    public @ResponseBody String getArticle(String url){

        Spider.create(baiDuArticlePageProcessor)
                .addUrl(url)
                .thread(1)
                .run();
        return "";
    }
}
