package com.wjc.ccf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class SmsUtil {
    private static final String SMS_URL = "http://utf8.api.smschinese.cn/";
    private static final String SMS_UID = "a1090779272";
    private static final String SMS_KEY = "d41d8cd98f00b204e980";

    public static void send(String phone, String text){
        StringBuffer sb = new StringBuffer();
        sb.append("Uid=");
        sb.append(SMS_UID);
        sb.append("&Key=");
        sb.append(SMS_KEY);
        sb.append("&smsMob=");
        sb.append(phone);
        sb.append("&smsText=");
        sb.append(text);

        try {
            String data = URLEncoder.encode(sb.toString(),"utf-8");
            URL url = new URL(SMS_URL);
            HttpURLConnection conn = (HttpURLConnection )url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("charset","utf-8");
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(sb.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            int code = conn.getResponseCode();
            String msg = "";
            if(code == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while((line = bufferedReader.readLine()) != null){
                    msg += line;
                }
                bufferedReader.close();
            }else{
                throw new RuntimeException("短信发送失败");
            }
            conn.disconnect();
            System.out.println(msg);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
