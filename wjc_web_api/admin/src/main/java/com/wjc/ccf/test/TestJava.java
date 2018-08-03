package com.wjc.ccf.test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

public class TestJava {

    public static void main(String[] args) {
//        List<List<String>> fatherList = new LinkedList<>();
//        List<String> childList = new ArrayList<>();
//        childList.add("before add child list");
//        fatherList.add(childList);
//        childList.add("after add child list");
//        System.out.println(fatherList.get(0));
        HashMap map = new HashMap<>();
        testBase64();
        String password = "123456";
        byte[] pwd = encode(password.getBytes());
        System.out.println("pwd:"+ new String(pwd));
        byte[] pwd2 = decode(pwd);
        System.out.println("pwd2:"+ new String(pwd2));
    }

    public static void testBase64(){
        byte[] e = Base64.getEncoder().encode("haha".getBytes());
        e = Base64.getEncoder().encode(e);
        e = Base64.getEncoder().encode(e);
        byte[] b = Base64.getDecoder().decode(e);
        String es = Base64.getEncoder().encodeToString("hahaha".getBytes());
        byte[] d2 = Base64.getDecoder().decode(es);
        byte[] b3 = Base64.getEncoder().encode(e);
        System.out.println("e-byte[]:"+e+",e-String:"+new String(e));
        System.out.println("byte[]:"+b3+",String:"+new String(b3));
        System.out.println(es);
        System.out.println(new String(d2));
        System.out.println("b-byte[]:"+b+",b-String:"+new String(b));
        System.out.println("byte[]:"+b3+",String:"+new String(b3));
    }

    public static byte[] encode(byte[] pwd){
        byte[] b = null;
        for(int i=0; i<64; i++){
            b = Base64.getEncoder().encode(Optional.ofNullable(pwd).orElseGet(() -> {throw new RuntimeException("param not be null");}));

        }
        return b;
    }

    public static byte[] decode(byte[] pwd){
        for(int i=0; i<64; i++){
            pwd = Base64.getMimeDecoder().decode(Optional.ofNullable(pwd).orElseGet(() -> {throw new RuntimeException("param not be null");}));
            System.out.println("decode()--->pwd:"+new String(pwd));
        }
        return pwd;
    }
}
