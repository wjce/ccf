package com.wjc.ccf.processor;

import com.wjc.ccf.model.BooksModel;
import com.wjc.ccf.pipeline.BooksModelPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Component
public class BooksPageProcessor implements PageProcessor {

    @Autowired
    private BooksModelPipeline booksModelPipeline;

    //抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setCharset("gbk").setRetryTimes(3).setSleepTime(3000);

    //process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        System.out.println("books线程名："+Thread.currentThread().getName());

        //查找所有class为main w的div元素，并找到它class为block2的div子节点的ul子节点下的li子节点下的a并提取文本/超链接
//        List<String> list = page.getHtml().xpath("//div[@class='main w']/div[@class='block2']/div/ul/li/a").all();
        List<String> names = page.getHtml().xpath("//div[@class='main w']/div[@class='block2']/div/ul/li/a/text()").all();
        List<String> urls = page.getHtml().xpath("//div[@class='main w']/div[@class='block2']/div/ul/li/a/@href").all();
//        page.addTargetRequests(list);
//        page.addTargetRequests(names);
//        page.addTargetRequests(urls);
//        list.forEach(t -> System.out.println("list----->"+t));
        names.forEach(t -> System.out.println("names----->"+t));
        urls.forEach(t -> System.out.println("urls----->"+t));
        //从页面发现后续的url地址来抓取
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        for(String url : urls){
            booksModelPipeline.url = url;
                System.out.println("url:" + url);
                OOSpider.create(Site.me().setCharset("gbk").setSleepTime(3000), booksModelPipeline, BooksModel.class).addUrl(url).thread(7).run();
                System.out.println("for-next");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

//    public static void main(String[] args) {
//        Spider.create(new GithubRepoPageProcessor())
//                .addUrl("http://www.tsxsw.com")
//                //开启5个线程抓取
//                .thread(5)
//                //启动爬虫
//                .run();
//    }
}
