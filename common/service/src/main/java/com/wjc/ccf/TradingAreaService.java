package com.wjc.ccf;

import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.domain.BaseArea;
import com.wjc.ccf.domain.TradingArea;
import com.wjc.ccf.repository.dao.TradingAreaDao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2019/9/7
 */
@Service
public class TradingAreaService {
    @Autowired
    private TradingAreaDao tradingAreaDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BaseAreaService baseAreaService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void saveArea(){
        System.setProperty("webdriver.chrome.driver", "c:\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://hotels.ctrip.com/hotel/beijing1");
        Actions actions = new Actions(driver);
        WebElement clickElement = driver.findElement(By.id("J_moreBizZone"));
        actions.click(clickElement).perform();
        System.out.println(driver.getCurrentUrl());
        WebElement e = driver.findElement(By.id("J_bizList"));
        System.out.println(e.getText());
        List<WebElement> elements = e.findElements(By.className("letters_block"));
        List<TradingArea> list = new ArrayList<TradingArea>();
        elements.forEach(element -> element.findElements(By.tagName("label")).forEach(t -> {
            TradingArea tradingArea = new TradingArea();
            tradingArea.setName(t.getText());
            list.add(tradingArea);
        }));
        driver.close();
        driver.quit();
    }

    public List<TradingArea> findAll(){
        return tradingAreaDao.findAll();
    }

    public List<TradingArea> findMore(){
        return tradingAreaDao.findByCityId(0l);
    }

    public void updateCityId(){
        Map map = new LinkedHashMap();
        List<TradingArea> list = findAll();
        for (TradingArea tradingArea : list) {
            String url = "https://restapi.amap.com/v3/geocode/regeo?key=a547cd18ce34ef33dc20f4856b89fce4&location=";
            url += tradingArea.getLng() + "," + tradingArea.getLat();
            String json = restTemplate.getForObject(url, String.class);
            try {
                Thread.currentThread().sleep(500l);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.parseObject(json);
            if(jsonObject.getString("status").equals("1")){
                JSONObject jsonArray = jsonObject.getJSONObject("regeocode");
                JSONObject jsonArray1 = jsonArray.getJSONObject("addressComponent");
                String area = jsonArray1.getString("district");
                String cityCode = jsonArray1.getString("citycode");
                BaseArea baseArea = baseAreaService.findByNameAndCode(area, cityCode);
                if(baseArea == null){
                    map.put(area, tradingArea.getId());
                    logger.error("error:  " + area + "," + cityCode);
                    continue;
                }
                Long cityId = baseArea.getId();
                tradingArea.setCityId(cityId);
            }else{
                logger.error("error:    " + jsonObject.toJSONString());
            }
        }
        logger.error("map:  " + map.toString());
    }

    public void updateCityIdIsZero(){
        try {
            Map map = new LinkedHashMap();
            List<TradingArea> list = findMore();
            for (TradingArea tradingArea : list) {
                String url = "https://restapi.amap.com/v3/geocode/regeo?key=a547cd18ce34ef33dc20f4856b89fce4&location=";
                url += tradingArea.getLng() + "," + tradingArea.getLat();
                String json = restTemplate.getForObject(url, String.class);
                logger.info("json:    " + json);
                try {
                    Thread.currentThread().sleep(500l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);
                if (jsonObject.getString("status").equals("1")) {
                    JSONObject jsonArray = jsonObject.getJSONObject("regeocode");
                    JSONObject jsonArray1 = jsonArray.getJSONObject("addressComponent");
                    String area = jsonArray1.getString("district");
                    area = "%" + area.substring(0, area.length() - 1) + "%";
                    logger.info("area:    " + area);
                    String cityCode = jsonArray1.getString("citycode");
                    BaseArea baseArea = baseAreaService.findByNameLikeAndCode(area);
                    if (baseArea == null) {
                        map.put(area, tradingArea.getId());
                        logger.error("error:  " + area + "," + cityCode);
                        continue;
                    }
                    Long cityId = baseArea.getId();
                    tradingArea.setCityId(cityId);
                } else {
                    logger.error("error:    " + jsonObject.toJSONString());
                }
            }
            logger.error("map:  " + map.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
