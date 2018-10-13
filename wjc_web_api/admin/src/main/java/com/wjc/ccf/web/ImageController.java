package com.wjc.ccf.web;

import com.wjc.ccf.processor.ImagePageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * 爬取百度图片
 * @author wangjunce 2018/10/12 16:05
 */
@RestController
public class ImageController {

    @Autowired
    private ImagePageProcessor imagePageProcessor;

    String url = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1539396480830_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%A4%B4%E5%83%8F";

    String search = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=index&fr=&hs=0&xthttps=111111&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%A4%B4%E5%83%8F&oq=%E5%A4%B4%E5%83%8F&rsp=-1";

    String index = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=index&fr=&hs=0&xthttps=111111&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%A4%B4%E5%83%8F&oq=%E5%A4%B4%E5%83%8F&rsp=-1";

    @GetMapping(value = "get_images")
    public String getImages(){
        String headImg = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%E5%A4%B4%E5%83%8F&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%E5%A4%B4%E5%83%8F&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&expermode=&cg=head&pn=";
        String wxheadImg = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%E5%A4%B4%E5%83%8F%E5%BE%AE%E4%BF%A1&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%E5%A4%B4%E5%83%8F%E5%BE%AE%E4%BF%A1&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&expermode=&pn=";

        int size = Thread.activeCount();
        for(int i =1; i<1925; i=i+10) {
            try {
                Spider.create(imagePageProcessor)
                        .addUrl(wxheadImg+i)
                        //开启x个线程抓取
                        .thread(5)
                        //启动爬虫
                        .run();
                while(true){
                    if(size == Thread.activeCount())
                        break;
                }
            } catch (Exception e) {
                return "error";
            }
        }
        try{
            imagePageProcessor.download();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "爬取成功~";
    }
}
