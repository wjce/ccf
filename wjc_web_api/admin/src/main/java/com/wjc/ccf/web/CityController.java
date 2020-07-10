package com.wjc.ccf.web;

import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.CityService;
import com.wjc.ccf.CtripCityService;
import com.wjc.ccf.TradingAreaService;
import com.wjc.ccf.domain.BaseArea;
import com.wjc.ccf.domain.City;
import com.wjc.ccf.domain.CtripCity;
import com.wjc.ccf.domain.TradingArea;
import com.wjc.ccf.processor.CityPageProcessor;
import com.wjc.ccf.processor.CtripCityPageProcessor;
import com.wjc.ccf.processor.TradingAreaPageProcessor;
import com.wjc.ccf.repository.dao.BaseAreaDao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/hotel")
@RestController
public class CityController {

    @Autowired
    private CtripCityService ctripCityService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CityPageProcessor cityPageProcessor;
    @Autowired
    private CtripCityPageProcessor ctripCityPageProcessor;
    @Autowired
    private TradingAreaPageProcessor tradingAreaPageProcessor;
    @Autowired
    private TradingAreaService tradingAreaService;

    @GetMapping("/update")
    public void update(){
        cityService.update();
    }
    /**
     * 商圈添加城市id
     */
    @GetMapping("/setCityId")
    public void setCityId(){
        tradingAreaService.updateCityId();
//        tradingAreaService.updateCityIdIsZero();
    }

    /**
     * 根据城市名称获取对应经纬度坐标
     * @return
     */
    @GetMapping("setCity")
    public String setCity(){

        String ak = "eikVTDrvMvVnPldFlh44DWdUAKpq1xfL";//高德
        String url = "http://api.map.baidu.com/geocoding/v3/?address=";
        String url2 = "&output=json&ak=p4nCbQdzyYUGYN7lnMRNuQZdG0Nqb39x&callback=showLocation";
        List<City> list = cityService.findList();
        String[] urls = new String[list.size()];
        List list2 = new ArrayList();
        try {
            for (City city : list) {
                cityPageProcessor.setCity(city);
                Spider.create(cityPageProcessor)
                        .addUrl(url + city.getName() + url2)
                        //开启x个线程抓取
                        .thread(1)
                        //启动爬虫
                        .run();
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    /**
     * 保存携程城市id
     * @return
     */
    @GetMapping("saveCripCity")
    public String saveCripCity(){

        String url = "https://hotels.ctrip.com/jiudian/";
        try {
            Spider.create(ctripCityPageProcessor)
                    .addUrl(url)
                    .thread(1)
                    .run();
        }catch (Exception e){
            return "error";
        }
        return "success";
    }

    @GetMapping("saveCripTradingArea")
    public String saveCripTradingArea(){
        String url = "https://hotels.ctrip.com/domestic/tool/AjaxCityZoneNew.aspx?city=";

        List<CtripCity> list = ctripCityService.findAll();
        try {
            int size = Thread.activeCount();
            for (CtripCity ctripCity : list) {
                tradingAreaPageProcessor.cityId = ctripCity.getCid();
                Spider.create(tradingAreaPageProcessor)
                        .addUrl(url + ctripCity.getCid())
                        .thread(1)
                        .run();

                if(size+5 == Thread.activeCount()) {
                    Thread.sleep(3000l);
                    continue;
                }
            }
        }catch (Exception e){
            return "error";
        }
        return "success";
    }
}
