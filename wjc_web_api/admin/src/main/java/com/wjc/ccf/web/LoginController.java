package com.wjc.ccf.web;

import com.wjc.ccf.UserService;
import com.wjc.ccf.domain.User;
import com.wjc.ccf.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/req_login")
    public String login(Model model, @ModelAttribute(name = "name") String name, @ModelAttribute(name = "password") String password){
        String error = null;
        User user = userService.getUserForName(name);
        if(user == null){
            error = "用户名不存在";
            model.addAttribute("error",error);
            return "/login";
        }
        String salt = user.getSalt();
        String pword = SecurityUtil.md5(password, salt);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,pword);
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            error = "用户名/密码错误";
        }catch (IncorrectCredentialsException e){
            error = "用户名/密码错误";
        }catch (AuthenticationException e){
            error = "其他错误" + e.getMessage();
        }
        if(error!=null){
            model.addAttribute("error", error);
            return "/login";
        }
        return "/main";
    }

    @RequestMapping(value = "req_register", method = RequestMethod.GET)
    public String register(){
        return "/register";
    }

    /**
     * 保存用户注册信息
     * @param model
     * @param name
     * @param phone
     * @param nickname
     * @param password
     * @param repassword
     * @return
     */
    @RequestMapping(value = "/req_save_user", method = RequestMethod.POST)
    public String save(Model model, RedirectAttributes redirectAttributes, String name, String phone, String nickname, String password, String repassword){
        if(!password.equals(repassword)){
            model.addAttribute("error","两次密码不一致，请重新输入");
            return "redirect:register";
        }

        String hex = SecurityUtil.getSalt();
        String md5 = SecurityUtil.md5(password, hex);
        User user = userService.save(new User(null,new Date(), new Date(), name, phone, nickname, md5, hex, 0, 0, null));
        if(user.getId() == null){
            return "/error";
        }
        redirectAttributes.addAttribute("name",user.getName());
        redirectAttributes.addAttribute("password",user.getPassword());
        return "redirect:req_login?name="+name+"&password="+password+"";
    }

    @RequestMapping(value = "/req_get_user", method = RequestMethod.GET)
    public @ResponseBody String getUser(String id){
        User user;
        if(StringUtils.isNotBlank(id)){
            user = userService.getUser(Long.valueOf(id));
            System.out.println(user.toString());
        }else{
            user = new User();
        }
        return user.toString();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "/login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index2(){
        return "/index";
    }
}
