package com.wjc.ccf.web;

import com.wjc.ccf.ProxyEntityService;
import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.processor.ProxyUrlPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author wangjunce 2018/11/1 14:38
 */
@RestController
public class ProxyEntityController {

    @Autowired
    private ProxyEntityService proxyEntityService;
    @Autowired
    private ProxyUrlPageProcessor proxyUrlPageProcessor;

    @GetMapping(value = "/save_proxy")
    public String save(String url, int port){
        ProxyEntity proxyEntity = new ProxyEntity();
        proxyEntity.setUrl(url);
        proxyEntity.setPort(port);
        proxyEntityService.save(proxyEntity);
        return "success";
    }

    @GetMapping(value = "/getProxy")
    public String getProxy(){
        String url = "http://www.xicidaili.com/nn/";
        try {
            Spider.create(proxyUrlPageProcessor)
                    .addUrl(url)
                    //开启x个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
