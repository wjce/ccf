package com.wjc.ccf.entity;

import java.util.Date;

/**
 * @author wjc
 * @description
 * @date 2019/12/2
 */
public class MyTime {

    private long value;

    public MyTime(long value){
        this.value = value;
    }

    public MyTime(){
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public long value(){
        return value;
    }
    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }

}
