package com.wjc.ccf.test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wjc
 * @description
 * @date 2019/10/29
 */
public class StringTest {

    public static void main(String[] args) {
//        System.out.println(isValid("()[]{}"));
//        System.out.println(strStr("", "a"));
//        System.out.println(lengthOfLastWord("a "));
        System.out.println(lengthOfLongestSubstring("bbbb")); // abcd   abcabd bbbb aab
    }

    //无重复字符最长子串
    public static int lengthOfLongestSubstring(String s) {

        if(s == null || s.equals("")){
            return 0;
        }
        char[] arr = s.toCharArray();
        int length = 1;
        int temp = 0;
        int useLength = 0;
        int temp2 = 0;
        for (int i = 0; i < arr.length; i++) {
            char a = arr[i];
            for (int i1 = i+1; i1 < arr.length; i1++) {
                char b = arr[i1];
                if(a == b){
                    if(i1 - i >= length){
                        length = i1 - i;
                    }
                    useLength = 1;
                    break;
                }
            }
            for(int i2 = 0; i2 < i; i2++){
                if(arr[i2] == arr[i]){
                    temp2++;
                    useLength = 0;
                }
            }
            if(temp < i+1){
                temp = i + 1;
            }
        }

        temp = temp - temp2;
        if(useLength == 0)
            return temp;
        return length;
    }

    //最长公共前缀
//    public String longestCommonPrefix(String[] strs) {
//
//        return "";
//    }

    public static int lengthOfLastWord(String s) {
        if(s == null || s.equals("") || s.equals(" ")){
            return 0;
        }

        int index = s.lastIndexOf(" ");
        while (index == s.length()-1){
            s = s.substring(0, s.length()-1);
            index = s.lastIndexOf(" ");
            if(index == -1){
                return s.length();
            }
        }
        s = s.substring(index+1, s.length());
        return s.length();
    }

    public static int strStr(String haystack, String needle) {
        if(needle == null || needle.equals("")){
            return 0;
        }
        if(haystack == null || haystack.equals("") || haystack.length() < needle.length()){
            return -1;
        }

        int count = 0;
        char[] a = needle.toCharArray();
        char[] b = haystack.toCharArray();

        for (int i = 0; i < b.length; i++) {
            if(b[i] == a[0]){
                count++;
                for (int j = 1, k = i+1; j < a.length && k <b.length; j++, k++) {
                    if(b[k] != a[j]){
                        break;
                    }
                    count++;
                }
                if(count == a.length){
                    return i;
                }
                count = 0;
            }
        }
        return -1;
    }

    public static boolean isValid(String s) {
        if(s.equals("")){
            return false;
        }

        char[] arr = s.toCharArray();
        if(arr.length%2 != 0){
            return false;
        }

        List<Character> list = new LinkedList<Character>();
        for (int i = 0; i < arr.length; i++) {
            char a = arr[i];
            if(list.size() == 0){
                list.add(a);
            }else{
                int size = list.size()-1;
                if(checkChar(list.get(size), a)){
                    list.remove(size);
                }else{
                    list.add(a);
                }
            }
        }

        return list.size()==0;
    }

    public static boolean checkChar(char a, char b){
        if((a == '(' && b == ')') || (a == '[' && b == ']') || (a == '{' && b == '}')){
            return true;
        }
        return false;
    }
}
