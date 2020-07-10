package com.wjc.ccf.processor;

import com.wjc.ccf.domain.HotelCityUrl;
import com.wjc.ccf.repository.dao.HotelCityUrlDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

/**
 * @author wangjunce 2018/10/24 11:20
 */
@Component
public class HotelPageProcessor implements PageProcessor {
    private static final String url = "http://hotels.ctrip.com";
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);
    @Autowired
    private HotelCityUrlDao hotelCityUrlDao;

    @Override
    public void process(Page page) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Elements elements = Jsoup.parse(page.getRawText()).getElementsByTag("dd");
        for(Element element : elements){
            Elements a = element.select("a");
            for(Element element1 : a){
                Map<String, String> map = new HashMap<String, String>();
                map.put("url", url+element1.attr("href"));
                map.put("name", element1.text());
                list.add(map);
                System.out.println(element1.text() + ":  " + element1.attr("href"));
            }
        }

//        page.addTargetRequests(list);
        for (Map map : list) {
            HotelCityUrl hotelCityUrl = new HotelCityUrl();
            hotelCityUrl.setUrl(map.get("url").toString());
            hotelCityUrl.setName(map.get("name").toString());
            hotelCityUrlDao.save(hotelCityUrl);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

}
