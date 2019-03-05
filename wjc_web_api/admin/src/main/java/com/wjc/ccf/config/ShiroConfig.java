package com.wjc.ccf.config;

import com.wjc.ccf.shiro.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangjunce on 2018/4/16.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap();
        filterChainDefinitionMap.put("/logout","logout");

//        filterChainDefinitionMap.put("/static/**", "anon"); 使用该路径shiro依然拦截
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/contact/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/vendors/**", "anon");

        /** 爬虫及测试代码请求 */
        filterChainDefinitionMap.put("/req_mongo/**", "anon");
        filterChainDefinitionMap.put("/sms", "anon");
        filterChainDefinitionMap.put("/get_books", "anon");
        filterChainDefinitionMap.put("/get_images", "anon");
        filterChainDefinitionMap.put("/get_tb_data", "anon");
        filterChainDefinitionMap.put("/get_hotel_data", "anon");
        filterChainDefinitionMap.put("/get_hotel_js", "anon");
        filterChainDefinitionMap.put("/get_hotel_detail", "anon");
        filterChainDefinitionMap.put("/find_hotel_detail", "anon");
        filterChainDefinitionMap.put("/save_hotel", "anon");
        filterChainDefinitionMap.put("/save_comment", "anon");
        filterChainDefinitionMap.put("/save_one_comment", "anon");
        filterChainDefinitionMap.put("/getProxy", "anon");
        filterChainDefinitionMap.put("/getTicketByTrainNumber", "anon");
        filterChainDefinitionMap.put("/test", "anon");
        filterChainDefinitionMap.put("/get_article", "anon");
        filterChainDefinitionMap.put("/getPhone", "anon");

        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/req_register","anon");
        filterChainDefinitionMap.put("/save","anon");
        filterChainDefinitionMap.put("/req_login","anon");
        filterChainDefinitionMap.put("/find_user_name","anon");
        filterChainDefinitionMap.put("/index","anon");
        filterChainDefinitionMap.put("/**","authc");

//        shiroFilterFactoryBean.setLoginUrl("/exception");
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/main");

//        shiroFilterFactoryBean.setUnauthorizedUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 返回自定义的shiroRealm对象
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    /**
     * cookie对象
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(30);
        return simpleCookie;
    }

    /**
     * cookie管理对象
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
}
