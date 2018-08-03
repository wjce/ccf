//package com.wjc.ccf.interceptor;
//
//import com.wjc.ccf.UserService;
//import com.wjc.ccf.domain.User;
//import com.wjc.ccf.util.SecurityUtil;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by Administrator on 2018/4/19.
// */
//public class SessionInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.isAuthenticated() && subject.isRemembered()){
//            String error = null;
//            User user = userService.getUserForName(subject.getPrincipals().toString());
//            if(user == null){
//                error = "用户名不存在";
//                model.addAttribute("error",error);
//                return false;
//            }
//            String salt = user.getSalt();
//            String pword = SecurityUtil.md5(password, salt);
//            Subject subject = SecurityUtils.getSubject();
//            UsernamePasswordToken token = new UsernamePasswordToken(name,pword);
//            try{
//                subject.login(token);
//            }catch (UnknownAccountException e){
//                error = "用户名/密码错误";
//            }catch (IncorrectCredentialsException e){
//                error = "用户名/密码错误";
//            }catch (AuthenticationException e){
//                error = "其他错误" + e.getMessage();
//            }
//            if(error!=null){
//                model.addAttribute("error", error);
//                return false;
//            }
//
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
