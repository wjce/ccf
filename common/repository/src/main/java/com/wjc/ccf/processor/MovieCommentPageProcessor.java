package com.wjc.ccf.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wjc
 * @description
 * @date 2019/8/8
 */
@Component
public class MovieCommentPageProcessor implements PageProcessor {
    private Site site = Site.me().setCharset("utf-8").setSleepTime(3000);

    @Override
    public void process(Page page) {
        String html = page.getRawText();
        System.out.println(html);
    }

    @Override
    public Site getSite() {
        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
    }
}
