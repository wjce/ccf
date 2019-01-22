package com.wjc.ccf.web;

import com.wjc.ccf.WebmagicUrlService;
import com.wjc.ccf.domain.WebmagicUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjunce 2018/12/20 14:54
 */
@RestController
public class TestController {

    @Autowired
    private WebmagicUrlService webmagicUrlService;

    @GetMapping(value = "/test")
    public String test(){

        WebmagicUrl webmagicUrl = webmagicUrlService.someServiceMethod();

        return webmagicUrl.getId().toString();
    }

}
