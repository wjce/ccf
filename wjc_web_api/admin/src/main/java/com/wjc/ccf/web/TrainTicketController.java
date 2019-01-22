package com.wjc.ccf.web;

import com.wjc.ccf.processor.TrainTicketPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author wangjunce 2018/12/8 9:49
 */
@RestController
public class TrainTicketController {

    @Autowired
    private TrainTicketPageProcessor trainTicketPageProcessor;

    @GetMapping("/getTicketByTrainNumber")
    public String getTacketByTrainNumber(String number){

        String url = "http://search.huochepiao.com/chaxun/resultc.asp?txtCheci=";

        Spider.create(trainTicketPageProcessor)
                .addUrl(url+number)
                .thread(1)
                .run();
        return "success";
    }

}
