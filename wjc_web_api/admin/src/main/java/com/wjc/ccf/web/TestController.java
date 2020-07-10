package com.wjc.ccf.web;

import com.wjc.ccf.WebmagicUrlService;
import com.wjc.ccf.domain.WebmagicUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangjunce 2018/12/20 14:54
 */
@RestController
public class TestController {

    @Autowired
    private WebmagicUrlService webmagicUrlService;

    @GetMapping(value = "/test1")
    public String test1(){

        WebmagicUrl webmagicUrl = webmagicUrlService.someServiceMethod();

        return webmagicUrl.getId().toString();
    }

    @GetMapping(value = "/MP_verify_e4Tde04DzhjkDkTV.txt")
    public String getTxt(){
        return "e4Tde04DzhjkDkTV";
    }

    @GetMapping(value = "test")
    public String getToken(){

        return null;
    }

}
