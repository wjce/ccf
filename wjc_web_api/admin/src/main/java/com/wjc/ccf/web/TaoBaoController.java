package com.wjc.ccf.web;

import com.wjc.ccf.WebmagicUrlService;
import com.wjc.ccf.domain.WebmagicUrl;
import com.wjc.ccf.processor.TaoBaoPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.utils.HttpConstant;

import java.text.SimpleDateFormat;

/**
 * @author wangjunce 2018/10/15 13:13
 */
@RestController
public class TaoBaoController {

    @Autowired
    private TaoBaoPageProcessor taoBaoPageProcessor;
    @Autowired
    private WebmagicUrlService webmagicUrlService;

    @GetMapping(value = "get_tb_data")
    public String getData(String search, Integer num){

        WebmagicUrl webmagicUrl = webmagicUrlService.findUrl(3l);
        String url = webmagicUrl.getUrl() + search;
        if(null != num){
            String str = "&data-value=";
            url = url + str + num;
            taoBaoPageProcessor.getSite().addHeader(":path", "/search?data-key=s&ajax=true&imgfile=&ie=utf8&p4ppushleft=5%2C48&s=0&q="+search + str + num);
        }else{
            taoBaoPageProcessor.getSite().addHeader(":path", "/search?data-key=s&ajax=true&imgfile=&ie=utf8&p4ppushleft=5%2C48&s=0&q="+search);
        }

        Request request = new Request();
        request.setMethod(HttpConstant.Method.GET);
        request.setUrl(url);
        request.addCookie("cookie", "t=96de8aadb09d02944d9d07a11d649f34; cna=EZhRFIwNtjUCAd6rw8XOheGl; tracknick=%5Cu6D77%5Cu6D9B%5Cu72D7%5Cu53F8%5Cu9A6C; _cc_=WqG3DMC9EA%3D%3D; tg=0; enc=gPJvvVX6Mhl7HMT9Wq%2FZ85ELBDG7pZ%2BqYZQrju6hLkrJL83yyzRBwI%2BWN9DB%2F1wQxCqiNfIZHPD%2FP9XpV0iwEg%3D%3D; miid=413092972126413901; thw=cn; hng=CN%7Czh-CN%7CCNY%7C156; UM_distinctid=166f2688501df6-029ee7849874f3-5b173413-1fa400-166f2688503797; isg=BPn5lcDLhgu1hVq2Iuayw9RuCGUTrvyq5QWGSRsvRCCEohg0Y1HYiLe7IObxGoXw; cookie2=1859ca53cca973834d1425d4cefb335b; v=0; _tb_token_=f3eeaeb688deb; JSESSIONID=0A64543EBC6CBB467CD66D6ACDF7A9BC");
        request.addHeader(":authority", "s.taobao.com")
                .addHeader(":method","GET")
                .addHeader(":path", "/search?data-key=s&ajax=true&imgfile=&ie=utf8&s=0&q="+search)
                .addHeader(":scheme","https")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("accept-encoding", "gzip, deflate, br")
                .addHeader("accept-language", "zh-CN,zh;q=0.9")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        try {
            Spider.create(taoBaoPageProcessor)
                    .addUrl(url)
//                    .addRequest(request)
//                    .setDownloader(httpClientDownloader)
                    .thread(1)
                    .run();
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

}
