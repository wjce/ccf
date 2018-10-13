package com.wjc.ccf.test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

public class TestBigdecimal {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(90);
        bigDecimal = bigDecimal.divide(new BigDecimal(100));

        BigDecimal price = new BigDecimal(10.101);
        price = price.multiply(bigDecimal).setScale(2, ROUND_HALF_UP);
        System.out.println(price);
        System.out.println(0.9*10.101);
    }
}
