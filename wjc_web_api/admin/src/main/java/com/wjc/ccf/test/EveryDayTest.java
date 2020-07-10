package com.wjc.ccf.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjc
 * @description
 * @date 2020/4/9
 */
public class EveryDayTest {

     static class Solution {
         public static void main(String[] args) {
             generateParenthesis(2);
         }
        public static List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<String>();
            add(res, "", 0, 0, n);
            for (String re : res) {
                System.out.println(re);
            }
            return res;
        }

        public static void add(List<String> res , String ans, int count1, int count2, int n){
            if(count1 > n || count2 > n) return;

            if(count1 == n && count2 == n)  res.add(ans);


            if(count1 >= count2){
                String ans1 = new String(ans);
                add(res, ans+"(", count1+1, count2, n);
                add(res, ans1+")", count1, count2+1, n);

            }
        }
    }
}
