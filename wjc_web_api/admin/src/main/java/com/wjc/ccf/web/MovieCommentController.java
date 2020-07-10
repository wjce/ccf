package com.wjc.ccf.web;

import com.wjc.ccf.processor.MovieCommentPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author wjc
 * @description
 * @date 2019/8/8
 */
@RestController
@RequestMapping("/movie")
public class MovieCommentController {

    @Autowired
    private MovieCommentPageProcessor movieCommentPageProcessor;

    @GetMapping("/getComment")
    public String getComment(){
//        String url = "https://movie.douban.com/subject/26794435/comments?status=P";
//        String city = "https://hotel.meituan.com/beijing/";
        String url = "https://www.meituan.com/meishi/266358/";
//        String comment = "https://www.meituan.com/meishi/api/poi/getMerchantComment?uuid=eb9fdf96d9ab4b2e8ad0.1564380884.1.0.0&platform=1&partner=126&originUrl=https%3A%2F%2Fwww.meituan.com%2Fmeishi%2F266358%2F&riskLevel=1&optimusCode=10&id=266358&userId=&offset=0&pageSize=10&sortType=1";
//        String nearCompany = "https://www.meituan.com/meishi/api/poi/getNearPoiList?offset=0&limit=10&cityId=1&lat=40.004513&lng=116.40083";
        Spider.create(movieCommentPageProcessor)
                .addUrl(url)
                //开启x个线程抓取
                .thread(1)
                //启动爬虫
                .run();
        return "success";
    }
}
