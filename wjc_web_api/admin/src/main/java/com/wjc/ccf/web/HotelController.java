package com.wjc.ccf.web;

import com.wjc.ccf.HotelCityUrlService;
import com.wjc.ccf.HotelCommentService;
import com.wjc.ccf.HotelUriEntityService;
import com.wjc.ccf.domain.HotelCityUrl;
import com.wjc.ccf.processor.CtripCityPageProcessor;
import com.wjc.ccf.processor.HotelCommentPageProcessor;
import com.wjc.ccf.processor.HotelListPageProcessor;
import com.wjc.ccf.processor.HotelPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author wangjunce 2018/10/24 11:18
 */
@RestController
public class HotelController {

    @Autowired
    private HotelUriEntityService hotelUriEntityService;
    @Autowired
    private HotelCommentService hotelCommentService;
    @Autowired
    private HotelPageProcessor hotelPageProcessor;
    @Autowired
    private HotelListPageProcessor hotelListPageProcessor;
    @Autowired
    private HotelCommentPageProcessor hotelCommentPageProcessor;


    @GetMapping(value = "get_hotel_data")
    public String getData(){

        String hotelAll = "http://hotels.ctrip.com/domestic-city-hotel.html";
        String hotelCity = "https://hotels.ctrip.com/nojoin-hotel.html";
        try {
            Spider.create(hotelPageProcessor)
                    .addUrl(hotelCity)
                    //开启x个线程抓取
                    .thread(5)
                    //启动爬虫
                    .run();
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @GetMapping(value = "/get_hotel_detail")
    public @ResponseBody String getHotelDetail(){
        List<String> urls = hotelCommentService.findCityUrls();
        try {
            for (String url : urls) {

                String u = url.substring(url.lastIndexOf("/"));
//                hotelListPageProcessor.url = u;
                Spider.create(hotelListPageProcessor)
                        .addUrl(url)
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

    @GetMapping(value = "/find_hotel_detail")
    public String findHotelDetail(String param){


        String url = hotelCommentService.findOne(param);
        String u = url.substring(url.lastIndexOf("/"));
//        hotelListPageProcessor.url = u;
        try {
            Spider.create(hotelListPageProcessor)
                    .addUrl(url)
                    //开启x个线程抓取
                    .thread(1)
                    //启动爬虫
                    .run();
        }catch (Exception e){
            return "error";
        }
        return "success";
    }

    @GetMapping(value = "/save_hotel")
    public String saveHotel(){
        List<String> urls = hotelCommentService.findCityUrls();
        String[] str = new String[urls.size()];
        str = urls.toArray(str);
        Spider.create(hotelListPageProcessor)
                .addUrl(str)
                .thread(5)
                .run();
        return "success";
    }

    @GetMapping(value = "/save_comment")
    public String saveHotelComment(){

        List<String> list = hotelUriEntityService.findHotelList();
        String[] urls = new String[list.size()];
        urls = list.toArray(urls);
        Spider.create(hotelCommentPageProcessor)
                .addUrl(urls)
                .thread(5)
                .run();

        return "success";
    }

    @GetMapping(value = "/save_one_comment")
    public String saveOneHotelComment(Long id){

        List<String> list = hotelUriEntityService.findByCityId(id);

        list = list.subList(list.indexOf("http://hotels.ctrip.com/hotel/15338847.html"), list.size()-list.indexOf("http://hotels.ctrip.com/hotel/15338847.html"));

        for (int i = 10; i<list.size(); i+=10) {

            List<String> sl = list.subList(i-10, i);
            String[] urls = new String[sl.size()];
            urls = sl.toArray(urls);

            try {
                Spider.create(hotelCommentPageProcessor)
                        .addUrl(urls)
                        .thread(5)
                        .run();
            }catch (Exception e){
                continue;
            }
        }
//        if(b)
//            return "success";
        return "error";
    }

}
