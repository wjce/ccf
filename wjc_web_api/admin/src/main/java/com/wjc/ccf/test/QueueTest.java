package com.wjc.ccf.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author wjc
 * @description
 * @date 2020/1/4
 */
public class QueueTest {

    public static void main(String[] args) throws Exception{
        BlockingQueue queue = new ArrayBlockingQueue(3);
        queue.add(3);
        queue.add("9");
        queue.add('f');
        queue.offer("超时", 1, TimeUnit.SECONDS);
//        Thread.sleep(1000);
        queue.take();

        System.out.println(queue.contains(3));
        System.out.println(queue.contains("9"));
        System.out.println(queue.contains('f'));
        System.out.println(queue.contains("超时"));
//        queue.clear();
        queue.add(1d);
    }
}
