package com.wjc.ccf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public @ResponseBody String getException(){
        return "自定义exception";
    }
}
