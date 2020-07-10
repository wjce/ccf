package com.wjc.ccf.processor;

import com.wjc.ccf.domain.CtripCity;
import com.wjc.ccf.repository.dao.CtripCityDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class CtripCityPageProcessor  implements PageProcessor {

    private String url = "https://hotels.ctrip.com";
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);
    @Autowired
    private CtripCityDao ctripCityDao;

    @Override
    public void process(Page page) {
        Elements elements = Jsoup.parse(page.getRawText()).getElementsByClass("index_item sec_province");
        Elements elements3 = elements.get(0).getElementsByTag("dl");
        Elements elements4 = elements3.tagName("a");
        Elements elements5 = elements4.select("dd a");
        int i = 0;
        for (Element element : elements4) {
            element.select("dd a");
        }
        System.out.println(i);
        Elements elements2 = elements.get(0).select("dl dd a");
        List<CtripCity> list = new ArrayList<CtripCity>();

//        for(Element element : elements){
//            Elements a = element.select("a");
            for(Element element1 : elements2){
                CtripCity ctripCity = new CtripCity();
                ctripCity.setTitle(element1.text());
                String href = element1.attr("href");
                ctripCity.setUrl(url + href);
                ctripCity.setCid(Long.valueOf(href.substring(href.indexOf('-')+1, href.indexOf(".html"))));
                list.add(ctripCity);
                list.sort(Comparator.comparing(CtripCity::getCid));
                System.out.println(ctripCity.toString());
            }
//        }

        ctripCityDao.saveAll(list);

    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
