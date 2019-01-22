package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度贴吧回帖
 * @author wangjunce 2019/1/18 11:32
 */
@Component
public class BaiDuArticlePageProcessor implements PageProcessor {

    private final String STOKEN = "05cc85e1d551cb63285bcb756a4acf9a2b7ab9f2498c4d81978aa1bdf36f7e99";
    private final String commentUrl = "http://tieba.baidu.com/f/commit/post/add";
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);
    public String content = "这是一个正常的回帖";

    @Override
    public void process(Page page) {

        String url = page.getUrl().toString();
        if(url.equals(commentUrl)){
            System.out.println(page.getRawText());
        }else {
            System.out.println("url:" + page.getUrl());
            String fid = "";
            String tid = "";
            String kw = "";
            String tbs = "";

            String html = page.getRawText();
            Document document = Jsoup.parse(html);
            Elements elements = document.select("script");

//            System.out.println(elements.toString());
            for (Element element : elements) {
                String e = element.toString();

                int a1 = e.indexOf("dataPostor");
                if (a1 != -1) {
                    fid = getValue("fid:", e);
                    tid = getValue("tid:", e);
                    kw = getValue("kw:", e);
                    System.out.println("fid:" + fid);
                    System.out.println("tid:" + tid);
                    System.out.println("kw:" + kw);
                }
            }
            Elements tbsElement = elements.eq(1);
            String tbsJs = tbsElement.toString();
            int a = tbsJs.indexOf("=");
            tbsJs = tbsJs.substring(a + 1);
            int b = tbsJs.indexOf(";");
            tbsJs = tbsJs.substring(0, b);

            Map map = JSON.parseObject(tbsJs, Map.class);
            tbs = map.get("tbs").toString();
            System.out.println("tbs:" + tbs);

            int i = 1;
            for (int i1 = 0; i1 < i; i1++) {
//                CloseableHttpClient httpClient = HttpClients.createDefault();
//                HttpGet httpGet = new HttpGet("http://tieba.baidu.com/dc/common/tbs");
//                HttpResponse response = null;
//                try{
//                    response = httpClient.execute(httpGet);
//                    HttpEntity entity = response.getEntity();
//
//                }catch (Exception e) {}

                Request request = new Request(commentUrl);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("ie", "utf-8");
                param.put("kw", kw);
                param.put("fid", fid);
                param.put("tid", tid);
                param.put("floor_num", "1");
                param.put("rich_text", "1");
                param.put("tbs", tbs);
                param.put("content", content + i1);
                param.put("basilisk", "1");
                param.put("mouse_pwd_isclick", "1");
                param.put("mouse_pwd_t", System.currentTimeMillis() + "");
                param.put("_type_", "reply");
                param.put("geetest_success", "1");
                param.put("_BSK", getBSK(tbs));

                request.setRequestBody(HttpRequestBody.form(param, "utf-8"));
                request.setMethod("POST");
                request.setExtras(param);

//                request.addCookie("STOKEN", STOKEN)
//                        .addCookie("BAIDUID", "41B9057766E267755FCD631AA40D84FD:FG=1")
//                        .addCookie("PSTM", "1545101329")
//                        .addCookie("BIDUPSID", "2B708E7A5B09A857DF988F607B87C500")
//                        .addCookie("TIEBA_USERTYPE", "7357fc7f62b2a8febbe32dfc")
//                        .addCookie("BDUSS", "jdOM210YlljTWpwYlBSWjhDVU1LVUV3TzY1STB3eFltbHV5am9CRG4taVFPMEJjQVFBQUFBJCQAAAAAAAAAAAEAAAD6954IzfW--7LfAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJCuGFyQrhhcW")
//                        .addCookie("TIEBAUID", "9d2ff1eb7ca36277e11c2ff6")
//                        .addCookie("rpln_guide", "1")
//                        .addCookie("BDORZ", "B490B5EBF6F3CD402E515D22BCDA1598")
//                        .addCookie("wise_device", "0")
//                        .addCookie("144635898_FRSVideoUploadTip", "1")
//                        .addCookie("H_PS_PSSID", "1453_25810_21124_28328_28131_28266_27245")
//                        .addCookie("delPer", "0")
//                        .addCookie("PSINO", "1")
//                        .addCookie("Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948", "1547799734,1547799966,1547799983,1547799991")
//                        .addCookie("Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948", "1547800020")
//                        .addCookie("BDRCVFR[feWj1Vr5u3D]", "I67x6TjHwwYf0");
                request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Host", "https://tieba.baidu.com")
                        .addHeader("Origin", "https://tieba.baidu.com")
                        .addHeader("Referer", url)
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36")
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .addHeader("Cookie", "BAIDUID=41B9057766E267755FCD631AA40D84FD:FG=1; PSTM=1545101329; BIDUPSID=2B708E7A5B09A857DF988F607B87C500; TIEBA_USERTYPE=7357fc7f62b2a8febbe32dfc; BDUSS=jdOM210YlljTWpwYlBSWjhDVU1LVUV3TzY1STB3eFltbHV5am9CRG4taVFPMEJjQVFBQUFBJCQAAAAAAAAAAAEAAAD6954IzfW--7LfAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJCuGFyQrhhcW; TIEBAUID=9d2ff1eb7ca36277e11c2ff6; rpln_guide=1; bdshare_firstime=1545121485465; STOKEN=05cc85e1d551cb63285bcb756a4acf9a2b7ab9f2498c4d81978aa1bdf36f7e99; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; wise_device=0; 144635898_FRSVideoUploadTip=1; SEENKW=%1F; mo_originid=2; IS_NEW_USER=27321327522709eafddc7b64; USER_JUMP=-1; CLIENTWIDTH=376; CLIENTHEIGHT=400; pb_prompt=1; SET_PB_IMAGE_WIDTH=284.8; H_PS_PSSID=1453_25810_21124_28328_28131_28266_27245; Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948=1548049469,1548050013,1548050073,1548050235; delPer=0; PSINO=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; ZD_ENTRY=baidu; Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948=1548052062");
                page.addTargetRequest(request);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String getValue(String key, String value){
        int i = value.indexOf(key);
        value = value.substring(i + key.length());
        int index = value.indexOf(",");
        return value.substring(0, index);
    }

    public String getBSK(String tbs){
        String BSK = "";
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            String jsFileName = "F:\\BSK.js";   // 读取js文件
            File file = new File(jsFileName);
            FileReader reader = new FileReader(file);   // 执行指定脚本
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                // 调用solve_bsk方法，并传入两个参数
                BSK = invoke.invokeFunction("solve_bsk", tbs).toString();
                System.out.println("BSK = " + BSK);
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return BSK;
    }

}
