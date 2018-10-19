package com.wjc.ccf.web;

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

    @GetMapping(value = "get_tb_data")
    public String getData(String search){

        String taobao = "https://s.taobao.com/search?data-key=s&data-value=48&ajax=true&imgfile=&ie=utf8&p4ppushleft=5%2C48&s=0&q=";
        String tbSearch = "https://s.taobao.com/search?q=风扇&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.2&ie=utf-8&initiative_id=tbindexz_20170306";

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("171.39.74.116",8123),
                new Proxy("117.191.11.79",8080),
                new Proxy("39.137.69.10", 80),
                new Proxy("180.114.12.225", 4154),
                new Proxy("121.8.98.196", 8080),
                new Proxy("39.137.2.210", 8080)
        ));

//        Request request = new Request();
//        request.setCharset("utf-8");
        try {
            Spider.create(taoBaoPageProcessor)
                    .addUrl(taobao+search)
//                    .addRequest(request)
//                    .setDownloader(httpClientDownloader)
                    //开启x个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        } catch (Exception e) {
            return "error";
        }
        return "";
    }

//    public static void main(String[] args) {
//        long long1 = 1539932738337l;
//        long long2 = 1539932740379l;
//        long long3 = 1539932743927l;
//        long long4 = 1539932746935l;
//        System.out.println(long1/60);
//        System.out.println(long2/60);
//        System.out.println(long3/60);
//        System.out.println(long4/60);
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        System.out.println(dateformat.format(long1));
//        System.out.println(dateformat.format(long2));
//        System.out.println(dateformat.format(long3));
//        System.out.println(dateformat.format(long4));
//    }
}
