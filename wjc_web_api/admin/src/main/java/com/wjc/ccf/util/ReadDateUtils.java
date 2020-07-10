package com.wjc.ccf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjc
 * @description
 * @date 2019/8/17
 */
public class ReadDateUtils {
    public static void readDate() throws Exception {
//        String a = "赫德岛和 麦克唐纳岛 Heard Islands and McDonald Islands HM  赫德岛和 麦克唐纳岛 Heard Islands and McDonald Islands";
        Pattern p = Pattern.compile("[\\u4E00-\\u9FFF]+");


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E:/1.txt")), "UTF-8"));
        String line = "";
        while ((line = br.readLine()) != null) {

            String param = line;
            String arr[] = new String[1000];
            try {
                arr = param.split(" ");
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            String oneStr = "";
            String twoStr = "";
            String threeStr = "";
            String fourStr = "";
            String fiveStr = "";

            int n = 0;
            for (int i = 0; i < arr.length; i++) {

                String str = arr[i];
                if (str.equals("")) {
                    ++n;
                    continue;
                }
                Matcher m = p.matcher(str);
                if (m.matches()) {
                    if (oneStr.equals("") || twoStr.equals("")) {
                        oneStr += str;
                    } else {
                        fourStr += str;
                        threeStr += arr[i - 1 - n];
                        twoStr = twoStr.substring(0, twoStr.length() - 2);
                    }
                    continue;
                }

                if (fourStr.equals("")) {
                    twoStr += str;
                } else {
                    fiveStr += str;
                }

            }

            System.out.println(oneStr + "," + twoStr + "," + threeStr + "," + fourStr + "," + fiveStr);
        }
    }
}
