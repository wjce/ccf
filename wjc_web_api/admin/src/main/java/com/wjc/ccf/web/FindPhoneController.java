package com.wjc.ccf.web;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author wangjunce 2019/1/22 10:11
 */
@RestController
public class FindPhoneController {

    /**
     * @Description: 查询手机号归属地
     * @param: [phone]
     * @author: wangjunce 2019/1/22 13:09
     * @return: java.lang.String
     */
    @GetMapping(value = "getPhone")
    public String getPhone(String phone){
        String result = "";
        BufferedReader in = null;
        String url1 = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?cb=jQuery110203695516523984277_1548121990992&resource_name=guishudi&query=";
        try {
            URL url = new URL(url1 + phone);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Host", "sp0.baidu.com");
            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gbk"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            String prov = getValue("\"prov\":", result);
            String city = getValue("\"city\":", result);
            System.out.println(prov+city);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    static String getValue(String key, String value){
        int i = value.indexOf(key);
        value = value.substring(i + key.length());
        int index = value.indexOf(",");
        return value.substring(0, index).replaceAll("\"","");
    }
}
