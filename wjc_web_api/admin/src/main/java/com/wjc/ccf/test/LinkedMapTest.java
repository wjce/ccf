package com.wjc.ccf.test;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class LinkedMapTest {
    public static void main(String[] args) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("name","louis");
        linkedHashMap.put("age","24");
        linkedHashMap.put("sex","male");


        Iterator iterator = linkedHashMap.keySet().iterator();
        while(iterator.hasNext()){
            System.out.println(linkedHashMap.get(iterator.next()));
        }
    }
}
