package com.wjc.ccf.processor;

import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.dao.ProxyEntityDao;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author wangjunce 2018/11/1 15:21
 */
@Component
public class ProxyUrlPageProcessor implements PageProcessor {

    @Autowired
    private ProxyEntityDao proxyEntityDao;

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {
        String text = page.getRawText();
        Elements elements = Jsoup.parse(text).select("tr");
        for(int i=1; i<elements.size(); i++){
            Elements td = elements.get(i).select("td");
            String ip = td.get(1).text();
            String port = td.get(2).text();
            ProxyEntity proxyEntity = new ProxyEntity();
            proxyEntity.setUrl(ip);
            proxyEntity.setPort(Integer.valueOf(port));
            proxyEntityDao.save(proxyEntity);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
