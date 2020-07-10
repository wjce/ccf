package com.wjc.ccf;

import com.wjc.ccf.domain.HotelCityUrl;
import com.wjc.ccf.domain.HotelComment;
import com.wjc.ccf.domain.HotelCommentImages;
import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.dao.HotelCityUrlDao;
import com.wjc.ccf.repository.dao.HotelCommentDao;
import com.wjc.ccf.util.HttpClient;
import com.wjc.ccf.util.HttpResponse;
import com.wjc.ccf.util.JavaHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjunce 2018/10/30 12:01
 */
@Service
public class HotelCommentService {
    @Autowired
    private HotelCommentDao hotelCommentDao;
    @Autowired
    private HotelCityUrlDao hotelCityUrlDao;
    @Autowired
    private HotelUriEntityService hotelUriEntityService;

    @Transactional(readOnly = true)
    public void getDate(){
        hotelCommentDao.findById(1l);
    }

    @Transactional(readOnly = true)
    public List<String> findCityUrls(){
        List<HotelCityUrl> list = hotelCityUrlDao.findAll();
        List<String> urls = new ArrayList<String>();
        for (HotelCityUrl hotelCityUrl : list) {
//            if(hotelCityUrl.getId() < 1123l){
//                continue;
//            }
            urls.add(hotelCityUrl.getUrl());
        }
        return urls;
    }

    @Transactional(readOnly = true)
    public String findOne(String param){
        String url = "";
        if(param.matches("^[0-9]*$")){
            HotelCityUrl hotelCityUrl = hotelCityUrlDao.findById(Long.valueOf(param)).get();
            url = hotelCityUrl.getUrl();
        }else{
            List<HotelCityUrl> list = hotelCityUrlDao.findByName(param);
            url = list.get(0).getUrl();
        }
        return url;
    }

}
