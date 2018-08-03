package com.wjc.ccf.shiro;

import com.wjc.ccf.UserService;
import com.wjc.ccf.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wjc on 2018/4/16.
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String name = principalCollection.getPrimaryPrincipal().toString();
        if(name == null){
            throw new RuntimeException("error");
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(userService.listPermissions(name));
        return simpleAuthorizationInfo;
    }

    //角色
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.getUserForName(name);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(user.getState()==1 || user.getState()==2){
            throw new LockedAccountException(); //帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getName(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
