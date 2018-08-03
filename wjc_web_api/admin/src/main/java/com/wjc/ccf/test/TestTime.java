package com.wjc.ccf.test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

//java8 time
public class TestTime {

    public static void main(String[] args) {
//        getClock();
        getLocalDate();
//        getLocalDateTime();
    }

    //Clock获取到的时间与北京时间相差8小时，需要+8
    static void getClock(){
        System.out.println(Clock.fixed(Clock.systemUTC().instant(),Clock.systemUTC().getZone()));

        ZoneId zoneId = Clock.systemDefaultZone().getZone();
        Clock c2 = Clock.system(zoneId);
        Instant i1 = c2.instant();
        System.out.println("i1:"+i1);

        Clock c1 = Clock.systemDefaultZone();

        System.out.println("c1==c2:"+(c1==c2)+"  c1.equals(c2):"+(c1.equals(c2)));
        System.out.println("c1:"+c1+" , c2:"+c2);

        //获取正确的时间
        Instant i2 = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(8)).instant();
        System.out.println("i2:"+i2);
    }

    static void getLocalDate(){
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now(ZoneId.of("Asia/Shanghai")));
        LocalDate l1 = LocalDate.of(2018, Month.JULY, 14);  //指定年月日
        LocalDate l2 = LocalDate.ofYearDay(1996, 107);  //x年中的x天
        LocalDate l3 = LocalDate.parse("2008-11-03");

        System.out.println("l1:"+l1);
        System.out.println("l2:"+l2);
        System.out.println("l3:"+l3);
    }

    static void getLocalDateTime(){
        System.out.println(LocalDateTime.now());
        System.out.println(LocalTime.now());
    }
}
