package com.wjc.ccf.web;

import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.processor.SmallPigCityPageProcessor;
import com.wjc.ccf.processor.SmallPigPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2019/8/5
 */
@RequestMapping("/smallPig")
@RestController
public class SmallPigHotelController {
    @Autowired
    private SmallPigPageProcessor smallPigPageProcessor;
    @Autowired
    private SmallPigCityPageProcessor smallPigCityPageProcessor;

    @RequestMapping(value = "/getHotelInfo", method = RequestMethod.GET)
    public String getHotelInfo(){

        String url = "https://cq.xiaozhu.com/search-duanzufang-0/";
        Spider.create(smallPigPageProcessor)
                .addUrl(url)
                //开启x个线程抓取
                .thread(1)
                //启动爬虫
                .run();
        return "suceess";
    }

    @RequestMapping(value = "/getCity", method = RequestMethod.GET)
    public String getCity(){
        String url = "https://cq.xiaozhu.com/ajaxRequest/Ajax_SearchCitys";
        Spider.create(smallPigCityPageProcessor)
                .addUrl(url)
                //开启x个线程抓取
                .thread(1)
                //启动爬虫
                .run();
        return "suceess";
    }

}
