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

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy("95.154.104.147",3726),
//                new Proxy("116.62.168.236",8080),
//                new Proxy("123.59.210.220", 1080),
//                new Proxy("202.146.144.139", 45316),
                new Proxy("223.203.0.14", 8000)
        ));
        Request request = new Request();
        request.setMethod("post");
        request.setUrl(url);
        try {
            Spider.create(taoBaoPageProcessor)
                    .addUrl(url)
//                    .addRequest(request)
//                    .setDownloader(httpClientDownloader)
                    //开启x个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

}
