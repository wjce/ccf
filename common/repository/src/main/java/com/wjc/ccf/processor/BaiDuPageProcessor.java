package com.wjc.ccf.processor;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wangjunce 2018/10/19 11:01
 */
@Component
public class BaiDuPageProcessor implements PageProcessor {
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {


        Gson gson = new Gson();
        String html = page.getRawText();
//        int after = html.indexOf("cb_16219182_0(");
//        int before = html.lastIndexOf(")");
//        html = html.substring(after, before).substring("cb_16219182_0(".length());
//        Map<String, Object> map = gson.fromJson(html, Map.class);
//        map.forEach((k,v) -> System.out.println(v));
        System.out.println(html);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
