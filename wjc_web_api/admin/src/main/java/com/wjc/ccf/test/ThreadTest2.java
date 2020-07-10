package com.wjc.ccf.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest2 {

    public static class ThreadCommunicate {
        static class Counter {
            private volatile int value=100;

            public void increment() {
                value++;
            }

            public void decrement() {
                value--;
            }

            public int value() {
                return this.value;
            }
        }

        static class IncrementTask implements Runnable {
            public void run() {
                for (int i = 0; i < 10000; i++) {
//                    System.out.println("increment:"+counter.value);
                    counter.increment();
                }
            }
        }

        static class DecrementTask implements Runnable {
            public void run() {
                for (int i = 0; i < 10000; i++) {
//                    System.out.println("decrement:"+counter.value);
                    counter.decrement();
                }
            }
        }

        private static Counter counter = new Counter();


        public static void main(String[] args) throws InterruptedException {
//            Thread i = new Thread(new IncrementTask());
//            Thread d = new Thread(new DecrementTask());
//            i.start();
//            d.start();
//            i.join();
//            d.join();
//            System.out.println(counter.value());

        }
    }
    static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        int num = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        Task task = new Task();
        for (int i = 1; i <= num; i++) {
            executorService.execute(task);
        }

        task.setParam("change param!");
        System.out.println("");
        executorService.shutdown();
    }

    static class Task implements Runnable{

        private String param = "hello world";

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public Task(){}

        public Task(String param){
            this.param = param;
        }

        @Override
        public void run() {
            testLock(getParam());
        }

    }

    static void testLock(String param){
        try {
            lock.readLock().lock();
            System.out.println("lock---" + param);

        } finally {
            lock.readLock().unlock();
            System.out.println("unlock---" + param);
        }
    }
}

