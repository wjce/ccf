package com.wjc.ccf.arithmetic;

public class Excrcise {

    public static void main(String[] args) {
        System.out.println('b');
        System.out.println('b' + 'c');
        System.out.println((char) ('a' + 4));

        int N = 5;

        System.out.println(toBinaryString(N));
        System.out.println(Integer.toBinaryString(N));
    }

    /**
     * 正整数用二进制表示并转换成String
     * @param i
     * @return
     */
    public static String toBinaryString(int i){
        String s = "";
        for (int n = i; n > 0; n /= 2)
            s = (n % 2) + s;
        return s;
    }
}
