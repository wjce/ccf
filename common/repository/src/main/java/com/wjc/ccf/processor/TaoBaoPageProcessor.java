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

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000).setUseGzip(true)
            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36")
            .addHeader(":authority", "s.taobao.com")
            .addHeader(":method", "GET")
            .addHeader(":scheme", "https")
            .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("accept-encoding", "gzip, deflate, br")
            .addHeader("upgrade-insecure-requests", "1")
            .addHeader("cookie", "t=96de8aadb09d02944d9d07a11d649f34; cookie2=1d0c3672924ed7cdd57f873ae13550d9; v=0; _tb_token_=e11e8b3f8eaad; cna=EZhRFIwNtjUCAd6rw8XOheGl; unb=1906837775; uc1=cookie16=U%2BGCWk%2F74Mx5tgzv3dWpnhjPaQ%3D%3D&cookie21=UIHiLt3xThH8t7YQoFNq&cookie15=UIHiLt3xD8xYTw%3D%3D&existShop=false&pas=0&cookie14=UoTYNkeeWX2hKQ%3D%3D&tag=8&lng=zh_CN; sg=%E9%A9%AC57; _l_g_=Ug%3D%3D; skt=3ba3d69ff5d28510; cookie1=W8txro3uEUiyVwQqhssX91%2B7j4GOCQFbRrPJg1srXS4%3D; csg=91066f89; uc3=vt3=F8dByRmpHbrQsue9uIM%3D&id2=UojUASgz6ebyUQ%3D%3D&nk2=2QMfSuJ2LRLzXQ%3D%3D&lg2=UtASsssmOIJ0bQ%3D%3D; existShop=MTU0MDAwODQ3Ng%3D%3D; tracknick=%5Cu6D77%5Cu6D9B%5Cu72D7%5Cu53F8%5Cu9A6C; lgc=%5Cu6D77%5Cu6D9B%5Cu72D7%5Cu53F8%5Cu9A6C; _cc_=WqG3DMC9EA%3D%3D; dnk=%5Cu6D77%5Cu6D9B%5Cu72D7%5Cu53F8%5Cu9A6C; _nk_=%5Cu6D77%5Cu6D9B%5Cu72D7%5Cu53F8%5Cu9A6C; cookie17=UojUASgz6ebyUQ%3D%3D; tg=0; mt=np=; isg=BA8PUj5YiMxHr4wwu0BsNz0DnqPZnHJEz3fwTyEcq36F8C_yKQTzpg3h9mBriDvO\n")
            .addHeader("accept-language", "zh-CN,zh;q=0.9");

    @Override
    public void process(Page page) {

        Gson gson = new Gson();
        String html = page.getRawText();
//        Map<String, Object> map = gson.fromJson(html, Map.class);
//        map.forEach((k,v) -> System.out.println(v));
        System.out.println(html);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
