package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSON;
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
 * 百度贴吧手机版回帖
 * @author wangjunce 2019/1/21 15:33
 */
@Component
public class BaiDuPhoneArticlePageProcessor implements PageProcessor {
    public static void main(String[] args) {
        String BSK = getBSK("4d592e81816b4a7b1548055858");
//        String bsk = "JVwIUmcLBF83AFZzQylHElBHd2drEkUCD3xLBDEHZGpxAWVbC3lxSkELaHtGSRtLcwoRYTIcGmt8DlBBfxMBHAdQAW0VLgUVWFVgZBsARwUKKVIfd1B+YWkBZg0HKHMfRAZpb1QYH1F8EHoGTHUPA3xSQwJxEx4LIxRfLARgVFxYRX93LlcAUhJvFBVnD2YkI0U1QxEndl9PE29nVRoaXHgZcxYSYRp9fAQJTgZ/BgdnAgJ9W244ZSYrZ3t4V0QVBG8BUytWMjk+XnAdUicgEhgbdHcfDXEHKFw2QhtwWyg6GzxDOBMICTZEEWVBfkMHRkUyZXgfV3lrASsEaRcrYXMKcg1SOi0RHEA2CAVhXFkjT30YXCAKZWRcNgorAhYJaVdSbkN2VgRaV2l1NBRXDR5/VxdyBXNhYBxyDgFrfl1HBmp7RkMZS3MIbQRPZwhyb09NQSYABhFlAUEqBGBURVtFf3UXSg9eUiEGCXAbdnB5fDkBRjF/XTRdOSULRE5JfwZvD14eXT8rDUFWZXNRQikRHBIzDUMIJE5lFipVGVJpKAVtLEFpZWIHflwFaWw2PWcQG0gNRgAiTX9zGzNTKHdeIgs3XklOakMEcVFiRQNTUWtgYwU4WFwkC0NlZic2MEI5QAZ6c1NGBX97Rl0ZS3MIOVUSI11rfAlSQX8RQkopBlZzQz9EElBHcWdqCVdACm9dBDVaNSQcVSMcUi4hURdfKCVIS0UKPFtzVxI/SyJyGBMCKFRXBzYQXzlNOx9eDggyeypEB1JQOUtJNVAoNSMcJABDZSgYG1QpP0hORgY6TTsYEj9bJioXDg1pVUtIMBhWMRVgGUIDACw5dksUWlthD082QSkiKBw8AFAoMBQaXT82FgFHDCddPVUMfEgiLA0ODSRdRko3WUA8EyMaXAgGNyR4CVdDD29dBCNAKDMlWT8BEz0rLgFBNDkDBQNJMggEWh8kUTE7XgIMIVR5CzhXH30Af1QKSgEkOylACA==";
//        System.out.println(bsk.equals(BSK));
        System.out.println(BSK);

    }
    private final String STOKEN = "05cc85e1d551cb63285bcb756a4acf9a2b7ab9f2498c4d81978aa1bdf36f7e99";
    private final String commentUrl = "https://tieba.baidu.com/mo/q/apubpost?_t=";
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);
    public String content = "这是一个正常的回帖";

    @Override
    public void process(Page page) {

        String url = page.getUrl().toString();
        if(url.contains(commentUrl)){
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

                Request request = new Request(commentUrl+System.currentTimeMillis());
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("co", content);
                param.put("_t", System.currentTimeMillis());
                param.put("tag", 11);
                param.put("upload_img_info", null);
                param.put("fid", fid);
                param.put("src", "1");
                param.put("word", "王钧策");
                param.put("tbs", tbs);
                param.put("z", tid);
                param.put("lp", "6026");
                param.put("nick_name", "王钧策");
                param.put("_BSK", getBSK(tbs));
                String json = JSON.toJSONString(map);

                request.setRequestBody(HttpRequestBody.json(json, "utf-8"));
                request.setMethod("POST");
//                request.setExtras(param);

                request.addHeader("Accept", "application/json")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Host", "tieba.baidu.com")
                        .addHeader("Origin", "https://tieba.baidu.com")
                        .addHeader("Referer", url)
                        .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Mobile Safari/537.36")
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .addHeader("Cookie", "BAIDUID=41B9057766E267755FCD631AA40D84FD:FG=1; PSTM=1545101329; BIDUPSID=2B708E7A5B09A857DF988F607B87C500; TIEBA_USERTYPE=7357fc7f62b2a8febbe32dfc; BDUSS=jdOM210YlljTWpwYlBSWjhDVU1LVUV3TzY1STB3eFltbHV5am9CRG4taVFPMEJjQVFBQUFBJCQAAAAAAAAAAAEAAAD6954IzfW--7LfAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJCuGFyQrhhcW; TIEBAUID=9d2ff1eb7ca36277e11c2ff6; rpln_guide=1; bdshare_firstime=1545121485465; STOKEN=05cc85e1d551cb63285bcb756a4acf9a2b7ab9f2498c4d81978aa1bdf36f7e99; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; wise_device=0; 144635898_FRSVideoUploadTip=1; mo_originid=2; IS_NEW_USER=27321327522709eafddc7b64; USER_JUMP=-1; CLIENTWIDTH=376; CLIENTHEIGHT=400; pb_prompt=1; H_PS_PSSID=1453_25810_21124_28328_28131_28266_27245; Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948=1548049469,1548050013,1548050073,1548050235; delPer=0; PSINO=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; ZD_ENTRY=baidu; SEENKW=%E7%8E%8B%E9%92%A7%E7%AD%96%23%1F; BAIDU_WISE_UID=wapp_1548055833644_764; Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948=1548055860; SET_PB_IMAGE_WIDTH=189.60000000000002; bdps_login_cookie=19");
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

    public static String getBSK(String tbs){
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
