package com.wjc.ccf.processor;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Map;

/**
 * @author wangjunce 2018/10/15 13:15
 */
@Component
public class TaoBaoPageProcessor implements PageProcessor {

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000)
            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36")
            .addHeader(":authority", "s.taobao.com")
            .addHeader(":method", "GET")
            .addHeader(":path", "/search?data-key=s&data-value=48&ajax=true&imgfile=&ie=utf8&p4ppushleft=5%2C48&s=0&q=饮料")
            .addHeader(":scheme", "htpps")
            .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("accept-encoding", "gzip, deflate, br")
            .addHeader("upgrade-insecure-requests", "1")
            .addHeader("cookie", "_m_h5_tk_enc=35f15bb9a01f1cf5a8f61fc44aab77e3;")
            .addHeader("accept-language", "zh-CN,zh;q=0.9");

    @Override
    public void process(Page page) {

        Gson gson = new Gson();
        String html = page.getRawText();
        Map<String, Object> map = gson.fromJson(html, Map.class);
//        map.forEach((k,v) -> System.out.println(v));
        System.out.println(html);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
