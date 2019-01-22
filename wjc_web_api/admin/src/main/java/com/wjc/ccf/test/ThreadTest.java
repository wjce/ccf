package com.wjc.ccf.test;

import com.wjc.ccf.web.TestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wangjunce 2019/1/15 16:14
 */
public class ThreadTest {

    public static void main(String[] args) {
        System.out.println(12==0xc?(0xb==11?10:9):8);
//        createThread();
//        createThread2();
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

    static class Task implements Callable{
        @Override
        public Object call() throws Exception {
//            return new ArrayList(){{add(1);add(2);add(4);}};
            return null;
        }
    }

}
