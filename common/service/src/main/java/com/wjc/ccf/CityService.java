package com.wjc.ccf;

import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.domain.*;
import com.wjc.ccf.repository.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityDao cityDao;
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private SmallPigHotelDao smallPigHotelDao;
//    @Autowired
//    private SmallPigSchoolDao smallPigSchoolDao;
//    @Autowired
//    private SmallPigDiTieStationDao smallPigDiTieStationDao;
    @Autowired
    private SmallPigDiTieNumDao smallPigDiTieNumDao;

    @Transactional
    public List<City> findList(){
        return cityDao.findByLevelBetween(2,3);
//        return cityDao.findAll();
    }

    @Transactional
    public void update(){
        String url = "http://api.map.baidu.com/geocoding/v3/?address=";
        String url2 = "&output=json&ak=p4nCbQdzyYUGYN7lnMRNuQZdG0Nqb39x&callback=showLocation";
        List<SmallPigDiTieNum> stationList = smallPigDiTieNumDao.findAll();
        for (SmallPigDiTieNum smallPigDiTieStation : stationList) {
            String json = restTemplate.getForObject(url + smallPigDiTieStation.getTitle() + "地铁站" + url2, String.class);
            json = json.substring(json.indexOf('(')+1, json.length()-1);
            System.out.println(json);
            JSONObject map = JSONObject.parseObject(json);
            if(map.getString("status").equals("1")){
                System.out.println(smallPigDiTieStation.getTitle());
                continue;
            }
            JSONObject result = (JSONObject) map.get("result");
            JSONObject location = (JSONObject) result.get("location");
            String lng = location.get("lng").toString();
            String lat = location.get("lat").toString();
            smallPigDiTieStation.setLat(lat);
            smallPigDiTieStation.setLng(lng);
            try {
                Thread.sleep(500l);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        smallPigDiTieNumDao.saveAll(stationList);
    }

}
