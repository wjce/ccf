package com.wjc.ccf.test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test1 {
    public static void main(String[] args) {
        Message i1 = null;
        Integer i2 = null;
//        Optional o1 = Optional.ofNullable(i1);
//        Optional o2 = Optional.ofNullable(i2);


//        Optional.ofNullable(i1).orElseGet(() -> { throw new NullPointerException();});
        Optional.ofNullable(i2).orElseGet(() -> { return 0;});
        System.out.println(Optional.ofNullable(i1).orElseGet(() -> { return Message.error("500");}).getContent());
        System.out.println(Optional.ofNullable(i2).orElseGet(() -> { return 0;}));
//        C c = new C();
//        B b = c -> 1;


        MathOperation addition = (int a, int b) -> a + b;
        MathOperation subtraction = (a, b) -> a - b;

        MathOperation multiplication = (int a, int b) -> { return a * b; };
        MathOperation division = (int a, int b) -> a / b;

        AA aa = new AA((a,b) -> a+b);

        //Stream

        List list = Arrays.asList(1,2,3,4,5);
        List list2 = Arrays.asList(1,2,3,"a","b","c","d","e");
        //forEach是接口中的方法，循环是在方法内部做的，无法使用break和countinue
        list.forEach(t -> {
            System.out.println(t);
            if((int)t==4){
            System.out.println(true);
//            Thread.currentThread().stop();
            return;
        }});
        System.out.println(1);

        //
        //map   对每个元素进行操作
        System.out.println("stream------>map");
        List list3 = (List)list.stream().map(t -> t+"a").collect(Collectors.toList());    //将list转化为流，会对list中的元素进行操作
        list3.forEach(l -> System.out.println(l));
        List list4 = Stream.of(list).map(t -> t+"b").collect(Collectors.toList());    //将list当做一个元素转化为流，后续操作将list当做一个整体
        list4.forEach(l -> System.out.println(l));

        //filter
        System.out.println("stream------->filter");
        List list5 = (List)list2.stream().filter(t -> t instanceof String).map(t -> t+",").collect(Collectors.toList());
        list5.forEach(t -> System.out.println(t));

        //limit
        System.out.println("stream--------->limit");
        list2.stream().limit(2).forEach(System.out::println);

        //sorted
        System.out.println("stream---------->sorted");
        Stream.of(5,6,8,1,20).sorted().forEach(System.out::println); //自然排序
        Stream.of(5,6,8,1,20).sorted(Comparator.reverseOrder()).forEach(t -> System.out.println(t)); //与自然排序相反
        list.stream().sorted(Comparator.comparing(Integer::intValue)).forEachOrdered(t -> System.out.println("-"+t));
        list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).forEachOrdered(t -> System.out.println("--"+t));
        Map map = new HashMap();
        map.put("1","a");
        map.put("2","b");
        map.put("3","c");
        map.put("4","d");
        map.put("5","e");
        map.forEach((t,u) -> System.out.println("key:"+t+",value:"+u));
    }

    interface A{
        int a(B b);
    }
    interface B{
        int b(C c);
    }
    static class C{
        public C(){}
    }

    static class AA{
        public AA(MathOperation mathOperation){}
    }
    interface MathOperation {
        int operation(int a, int b);
    }
}
