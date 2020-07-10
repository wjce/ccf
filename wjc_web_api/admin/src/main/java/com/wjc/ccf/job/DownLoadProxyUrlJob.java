//package com.wjc.ccf.job;
//
//import com.wjc.ccf.ProxyEntityService;
//import com.wjc.ccf.domain.ProxyEntity;
//import com.wjc.ccf.processor.ProxyUrlPageProcessor;
//import com.wjc.ccf.repository.dao.ProxyEntityDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import us.codecraft.webmagic.Spider;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
///**
// * @author wangjunce 2018/11/1 15:17
// */
//@Component("downLoadProxyUrlJob")
//public class DownLoadProxyUrlJob {
//
//    @Autowired
//    private ProxyEntityService proxyEntityService;
//    @Autowired
//    private ProxyUrlPageProcessor proxyUrlPageProcessor;
//    @Autowired
//    private ProxyEntityDao proxyEntityDao;
//
//    @Scheduled(cron = "0 0/15 * * * ?")
//    public void saveProxy(){
//        proxyEntityService.setState();
//        try {
//            Spider.create(proxyUrlPageProcessor)
//                    .addUrl("http://www.xicidaili.com/nn/")
//                    //开启x个线程抓取
//                    .thread(1)
//                    //启动爬虫
//                    .run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void removeProxy() throws MalformedURLException{
//        List<ProxyEntity> entities = proxyEntityDao.findByState(0);
//        CopyOnWriteArrayList<ProxyEntity> copyOnWriteArrayList = new CopyOnWriteArrayList<ProxyEntity>();
//        copyOnWriteArrayList.addAll(entities);
//
//        List<Long> removeList = new ArrayList<Long>();
//        URL url = new URL("http://www.baidu.com");
//        copyOnWriteArrayList.forEach(t -> {
//            try {
//                InetSocketAddress addr = new InetSocketAddress(t.getUrl(),t.getPort());
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
//                URLConnection conn = url.openConnection(proxy);
//                conn.connect();
//            }catch (IOException e){
//                System.out.println("DownLoadProxyUrlJob--removeProxy--ExceptionMessage(62): " + e.getMessage());
////                copyOnWriteArrayList.remove(t);
//                removeList.add(t.getId());
//            }
//        });
//        proxyEntityDao.updateProxtEntity(removeList);
//        System.out.println(copyOnWriteArrayList.size());
//    }
//}
