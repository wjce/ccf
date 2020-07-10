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
                new Proxy("180.109.146.254",46235),
                new Proxy("163.204.242.153",9999),
                new Proxy("118.112.195.239", 9999),
                new Proxy("39.137.69.9", 8080),
                new Proxy("61.186.64.33", 29922),
                new Proxy("112.95.24.209", 8118)
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
