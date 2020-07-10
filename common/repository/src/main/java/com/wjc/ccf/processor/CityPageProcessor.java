package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.domain.City;
import com.wjc.ccf.repository.dao.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

import java.util.HashMap;
import java.util.Map;

@Component
public class CityPageProcessor implements PageProcessor {

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Autowired
    private CityDao cityDao;
    private City city;

    @Override
    public void process(Page page) {
        String json = page.getJson().get();
        json = json.substring(json.indexOf('(')+1, json.length()-1);
        JSONObject map = JSONObject.parseObject(json);
//        Map map = (Map)JSON.parse(json);

        JSONObject result = (JSONObject) map.get("result");
        JSONObject location = (JSONObject) result.get("location");
        String lng = location.get("lng").toString();
        String lat = location.get("lat").toString();
        city.setLatitude(lat);
        city.setLongitude(lng);
        cityDao.save(city);
        Thread.currentThread().interrupt();
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
