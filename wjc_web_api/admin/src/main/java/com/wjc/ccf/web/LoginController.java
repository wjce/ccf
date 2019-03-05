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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/get_hotel_js")
    public String atest(){
        return "/hotel_js";
    }
    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/req_logout", method = RequestMethod.GET)
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:";
    }

    /**
     * 登录
     * @param model
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/req_login")
    public String login(Model model, @ModelAttribute(name = "name") String name, @ModelAttribute(name = "password") String password, String rememberMe){
        if(StringUtils.isBlank(name) && StringUtils.isBlank(password)){
            return "/login";
        }
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

        if(null!=rememberMe && rememberMe.equals("on")){
            token.setRememberMe(true);
        }
        return "redirect:main";
    }

    /**
     * 访问主页
     * @return
     */
    @RequestMapping(value = "/main")
    public String main(){
        return "/main";
    }

    /**
     * 注册页
     * @return
     */
    @RequestMapping(value = "/req_register", method = RequestMethod.GET)
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
    @RequestMapping(value = "/save", method = RequestMethod.POST)
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
        return "redirect:req_login";
    }

    @RequestMapping(value = "/a", method = RequestMethod.POST)
    public String a(){
        return "/main";
    }

    /**
     * 获取注册名是否可用
     * @param name
     * @return
     */
    @RequestMapping(value = "/find_user_name", method = RequestMethod.POST)
    public @ResponseBody String validateName(String name){
        System.out.println(name);
        User user = userService.getUserForName(name);
        if(user==null){
            return "200";
        }
        return "205";
    }

    /**
     * 入口
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){

        model.addAttribute("content", "hello world");
        System.out.println("=============");
        Subject subject = SecurityUtils.getSubject();
        if(subject.isRemembered()){
            return "/index";
        }
        return "/login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index2(Model model){
        model.addAttribute("message2", "error");

        return "/index";
    }

    @RequestMapping(value = "/test_login", method = RequestMethod.GET)
    public @ResponseBody String test(){
        String html = "<div class=\"row\">\n" +
                "        <div id=\"error\" class=\"alert alert-info\">\n" +
                "            <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>\n" +
                "            <span th:text=\"${error}\"></span>\n" +
                "        </div>\n" +
                "        <form method=\"post\" action=\"/req_login\" class=\"bootstrap-admin-login-form\">\n" +
                "            <h1>登录</h1>\n" +
                "            <div class=\"form-group\">\n" +
                "                <input class=\"form-control\" type=\"text\" name=\"name\" placeholder=\"账号\" />\n" +
                "            </div>\n" +
                "            <div class=\"form-group\">\n" +
                "                <input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"密码\" />\n" +
                "            </div>\n" +
                "            <div class=\"form-group\">\n" +
                "                <label>\n" +
                "                    <input type=\"checkbox\" name=\"rememberMe\" />\n" +
                "                    记住密码\n" +
                "                </label>\n" +
                "            </div>\n" +
                "            <button class=\"btn btn-lg btn-primary\" type=\"submit\">提交</button>\n" +
                "            <button class=\"btn btn-lg btn-primary\" type=\"button\" onclick=\"javascript:window.location.href='/req_register'\">注册</button>\n" +
                "        </form>\n" +
                "    </div>";
        return html;
    }
}
