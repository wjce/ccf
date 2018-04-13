package com.wjc.ccf.web;

import com.wjc.ccf.UserService;
import com.wjc.ccf.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/req_get_user", method = RequestMethod.GET)
    public @ResponseBody String getUser(String id){
        User user;
        if(StringUtils.isNotBlank(id)){
//            user = userService.getUser(Long.valueOf(id));
//            System.out.println(user.toString());
        }else{
            user = new User();
        }
        return null;
        //return user.toString();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
