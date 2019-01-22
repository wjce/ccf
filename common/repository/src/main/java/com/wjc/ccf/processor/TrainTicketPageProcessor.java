package com.wjc.ccf.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wangjunce 2018/12/8 9:57
 */
@Component
public class TrainTicketPageProcessor implements PageProcessor {

    private Site site = Site.me().setCharset("gb2312").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {

        page.getHtml();
        System.out.println(page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
