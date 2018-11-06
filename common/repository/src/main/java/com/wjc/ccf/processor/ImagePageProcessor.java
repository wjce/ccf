package com.wjc.ccf.processor;


import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author wangjunce 2018/10/12 16:07
 */
@Component
public class ImagePageProcessor implements PageProcessor {

    private static Set<String> set = new HashSet<String>();

//    @Autowired
//    private ImageModelPipeline imageModelPipeline;

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {
        Gson gson = new Gson();
        String html = page.getRawText();
        Map map = gson.fromJson(html, Map.class);

        List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("data");
        for(Map<String, Object> m : list){
            Object thumbURL = m.get("thumbURL");
            if(null == thumbURL){
                break;
            }
            String url = thumbURL.toString();
            set.add(url);
            System.out.println(url);
        }
        System.out.println("set长度："+ set.size());
        try {
            if (set.size() == 1100)
                download();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("下载错误");
        }
        Thread.currentThread().interrupt();
    }

    public void download() throws Exception {
        System.out.println("开始下载图片");
        for(String urlString : set) {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File("f://newImage");
            if (!sf.exists()) {
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(sf.getPath() + "\\" + "图片" + System.currentTimeMillis()+".jpg");
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
        System.out.println("结束下载图片");
    }

    @Override
    public Site getSite() {
        return site;
    }
}
