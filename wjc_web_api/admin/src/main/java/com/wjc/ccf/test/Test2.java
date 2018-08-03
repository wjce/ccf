package com.wjc.ccf.test;

public class Test2 {
    public static void main(String[] args) {
        System.out.println(sum(10,30));
    }

    static int sum(int n, int m){
        if(n>m){
            throw new RuntimeException("n不能大于m");
        }
        int sum = 0;
        while (n <= m){
            sum = n%2 != 0 ? sum+n : sum;
            n++;
        }
        return sum;
    }
}
