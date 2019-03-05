package com.wjc.ccf.test;

import com.wjc.ccf.web.TestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangjunce 2019/1/15 16:14
 */
public class ThreadTest {

    static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
//        createThread();
//        createThread2();
        testLock();
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(new Task3("111"));
//        executorService.execute(new Task3("222"));
//        executorService.execute(new Task3("333"));
//        executorService.execute(new Task3("444"));
//        executorService.execute(new Task3("555"));
//        executorService.execute(new Task3("666"));
//        executorService.execute(new Task3("777"));
//        executorService.execute(new Task3("888"));
//        executorService.execute(new Task3("999"));
//        executorService.execute(new Task3("000"));
//        executorService.shutdown();
    }

    static class Task3 implements Runnable{
        public Task3(){}
        public Task3(String param){this.param = param;}
        public String param;
        @Override
        public void run() {
            testLock(param);
        }
    }

    public static void testLock(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testLock("1111");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testLock("2222");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                testLock("3333");
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                testLock("4444");
            }
        });
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                testLock("5555");
            }
        });

        try {
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void testLock(String param){

        System.out.println(param);
        lock.lock();
        try {
            Thread.sleep(500l);
            System.out.println(param+"time:" + System.currentTimeMillis());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void createThread2(){
        int size = 5;
        ExecutorService pool = Executors.newFixedThreadPool(size);
        List<Future> list = new ArrayList<Future>();

        for (int i = 0; i < size; i++) {
            Callable callable = new Task2(i + "");
            Future future = pool.submit(callable);
            list.add(future);
        }

        pool.shutdown();        // 拒绝传入任务
//        pool.shutdownNow();  //取消所有遗留的任务,停止当前正执行的task，并返回尚未执行的task的list
        for (Future future : list) {
            try {
                System.out.println(future.get().toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Task2 implements Callable{
        public Task2() {}
        public Task2(String param) {
            this.param = param;
        }

        private String param;

        public String getParam() {
            return param;
        }
        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public Object call() throws Exception {
            System.out.println("开始任务:" + getParam());
            Date date = new Date();
            Thread.sleep(1000);
            Date date1 = new Date();
            long time = (date1.getTime()-date.getTime())/1000;
            return "停止任务，任务执行时间：" + time;
        }
    }

    //使用Callable创建线程，通过Callable创建的线程有返回值，通过Runnable创建的线程无返回值
    public static void createThread(){
        System.out.println("通过Callable创建有返回值的线程");
        Callable callable = new Task();
        FutureTask futureTask = new FutureTask(callable); //执行Callable任务后，可以获取一个Future的对象，在该对象上调用get就可以获取到Callable任务返回的Object
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            System.out.println(futureTask.get()==null?"值为空":futureTask.get().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        while(futureTask.isDone()) {
            thread.interrupt();
        }
    }

    static class Task implements Callable{
        @Override
        public Object call() throws Exception {
//            return new ArrayList(){{add(1);add(2);add(4);}};
            return null;
        }
    }

}
