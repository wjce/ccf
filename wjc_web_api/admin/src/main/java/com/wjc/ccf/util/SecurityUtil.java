package com.wjc.ccf.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/4/16.
 */
public class SecurityUtil {
    /**
     1、首先创建一个DefaultHashService，默认使用SHA-512算法；
     2、可以通过hashAlgorithmName属性修改算法；
     3、可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
     4、可以通过generatePublicSalt属性在用户没有传入公盐的情况下是否生成公盐；
     5、可以设置randomNumberGenerator用于生成公盐；
     6、可以设置hashIterations属性来修改默认加密迭代次数；
     7、需要构建一个HashRequest，传入算法、数据、公盐、迭代次数
     */
    public static String getSalt(){
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        return hex;
    }

    public static String md5(String password, String salt){
        return new Md5Hash(password,salt).toString();
    }

}
