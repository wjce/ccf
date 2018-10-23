package com.wjc.ccf;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 * @author wangjunce 2018/10/23 14:39
 */
public class StringUtil {

    /**
     * @Description: 整数/1位小数补0，0除外  --只适用两位小数
     * @param: [str]
     * @auther: wangjunce 2018/10/23 14:43
     * @return: java.lang.String
     */
    public String addZero(String str){
        if(null == str || "".equals(str))
            return "";
        int charIndex = 0;
        char d = '.';
        char[] chars = str.toCharArray();
        int length = chars.length;

        if(length == 1){
            int n = chars[0];
            if(!Character.isDigit(n))
                throw new RuntimeException("不符合整数或小数格式");
            if(n != 48)
                str += ".00";
        }

        for(int i=0; i < length; i++){
            if(charIndex > 1)
                throw new RuntimeException("不符合整数或小数格式");
            if(chars[i] == d) {
                charIndex++;
                int j = length-i-1;
                if(j > 2 || j == 0)
                    throw new RuntimeException("不符合整数或小数格式");
                if(j==1)
                    str += '0';
            }
            if(i == length-1 && charIndex==0)
                str += ".00";
        }
        return str;
    }

    /**
     * @Description: 逗号分隔字符串转集合
     * @param: [str]
     * @auther: wangjunce 2018/10/23 14:42
     * @return: java.util.List<java.lang.String>
     */
    public static List<String> StringToList(String str){
        String[] strs = str.split(",");
        return Arrays.stream(strs).collect(Collectors.toList());
    }
}
