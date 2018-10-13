package com.wjc.ccf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@RestController
public class TestController {

    private static final String TOKEN = "wjc201808271541";

    @GetMapping("/check_token")
    public String test(HttpServletRequest request, HttpServletResponse response){

        try {
            /**
             * 接收微信服务器发送请求时传递过来的4个参数
             */
            String signature = request.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
            String timestamp = request.getParameter("timestamp");//时间戳
            String nonce = request.getParameter("nonce");//随机数
            String echostr = request.getParameter("echostr");//随机字符串
            //排序
            String sortString = sort(TOKEN, timestamp, nonce);
            //加密
            String mySignature = sha1(sortString);
            //校验签名,通过检验signature对请求进行校验，若校验成功则原样返回echostr,表示接入成功，否则接入失败
            if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
                System.out.println("签名校验通过。");
                //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
                return echostr;
            } else {
                System.out.println("签名校验失败.");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return "500";
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str
     *            需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            //返回实现指定摘要算法的 MessageDigest 对象
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            //使用指定的 byte 数组更新摘要
            digest.update(str.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
