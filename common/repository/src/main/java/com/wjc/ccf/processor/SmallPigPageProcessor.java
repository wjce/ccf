package com.wjc.ccf.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wjc
 * @description
 * @date 2019/8/5
 */
@Component
public class SmallPigPageProcessor implements PageProcessor {

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {

//        String html = page.getRawText();
//        System.out.println(html);
//        Elements elements = Jsoup.parse(html).getElementsByClass("position_box");
//
//        for (int i = 0; i < elements.size(); i++) {
//            if(i == 2){
//                getDiTie(elements.get(i));
//            }else if(i == 5){
//                getSchool(elements.get(i));
//            }else if(i == 6){
//                getHotel(elements.get(i));
//            }
//        }

    }

//    public void getHotel(Element element){
//        Elements elements = element.getElementsByTag("div");
//        Elements hotels = elements.get(1).getElementsByTag("span");
//        for (Element hotel : hotels) {
//            String spanText = hotel.text();
//            System.out.println("学校：" + spanText);
//        }
//    }
//
//    public void getSchool(Element element){
//        Elements elements = element.getElementsByTag("div");
//        Elements schools = elements.get(1).getElementsByTag("span");
//        for (Element school : schools) {
//            String spanText = school.text();
//            System.out.println("学校：" + spanText);
//        }
//    }
//
//    public void getDiTie(Element element){
//
//        /** 地铁线路*/
//        Elements elements = element.getElementsByTag("div");
//        Elements nums = elements.get(1).getElementsByTag("span");
//        for (Element num : nums) {
//            String spanClass = num.attr("key");
//            String spanText = num.text();
//            System.out.println("地铁线路: "+spanClass + ",text: " + spanText);
//        }
//
//        /**地铁站*/
//        Elements stationSpan = elements.get(2).getElementsByTag("span");
//        for (Element station : stationSpan) {
//            String spanClass = station.attr("class");
//            String spanText = station.text();
//            System.out.println("地铁站class: " + spanClass + ",站名" + spanText);
//        }
//    }
    @Override
    public Site getSite() {
        return site;
    }
}
