package com.wjc.ccf.web;

import com.wjc.ccf.UserService;
import com.wjc.ccf.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/4/19.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/mytest")
    public String test(){
        return "/test";
    }
    @RequestMapping(value = "/user_list", method = RequestMethod.GET)
    public String list(Model model, Pageable pageable){
        Page<User> page = userService.list(pageable);
        model.addAttribute("page",page);

        return "/user/user_list";
    }


}
