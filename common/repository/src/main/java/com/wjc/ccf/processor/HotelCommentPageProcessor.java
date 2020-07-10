package com.wjc.ccf.processor;

import com.wjc.ccf.EmojiUtil;
import com.wjc.ccf.domain.HotelComment;
import com.wjc.ccf.domain.HotelCommentImages;
import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.dao.HotelCommentDao;
import com.wjc.ccf.repository.dao.ProxyEntityDao;
import com.wjc.ccf.util.HttpClient;
import com.wjc.ccf.util.HttpResponse;
import com.wjc.ccf.util.JavaHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjunce 2018/10/30 11:24
 */
@Component
public class HotelCommentPageProcessor implements PageProcessor {

    //id=1218334 fen ye = 189
    @Autowired
    private HotelCommentDao hotelCommentDao;

    private Site site = Site.me().setCharset("utf-8").setSleepTime(3000);

    private static final String hotelCommentUrl = "http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx";
    private String id = "";
    private int pageNumber = 0;
    private String key1;
    private String key2;
    private String value1;
    private String value2;
    private int currentPage;

    @Override
    public void process(Page page) {

        try {
            String url = page.getRequest().getUrl();
            url = url.substring(url.lastIndexOf("/") + 1);
            int index = url.indexOf(".html");
            if(page.getRawText().contains("亲爱的用户，请完成验证后继续访问")){
                Thread.sleep(3000l);
            }
            if (index == -1) {

                String u = page.getRequest().getUrl();
                int i1 = u.indexOf("&");
                String a = u.substring(0,i1);
                int i2 = u.indexOf("=");
                String id = a.substring(i2+1);
                setId(id);
                String aa = u.substring(0, u.indexOf("&viewVersion"));
                setCurrentPage(Integer.valueOf(aa.substring(aa.lastIndexOf("=")+1)));

                String text = page.getRawText();
                if(StringUtils.isBlank(text)) {
                    addTargetUrl(page, getId(), oceanball(getId(), gethead(getId()), null), getCurrentPage());
                    return;
                }
                if(text.contains("此酒店暂无点评"))
                    return;
                Document document = Jsoup.parse(text);
                Elements ep = document.select("#divCtripComment > div.c_page_box > div > div.c_page_list.layoutfix > a");//获取分页信息的a标签
                Element cPage = document.selectFirst("#divCtripComment > div.c_page_box > div > div.c_page_list.layoutfix > a.current");//得到当前页的a标签

                try {
                    setPageNumber(Integer.parseInt(ep.last().text()));//得到总页数的值
                    setCurrentPage(Integer.parseInt(cPage.text()));//得到当前页的值
                }catch (NullPointerException e){
                    setPageNumber(1);
                    setCurrentPage(1);
                }

                Element element = document.selectFirst("#divCtripComment > div.comment_detail_list");//得到所有的评论信息div
                if (element != null) {
                    Elements elements = element.getElementsByClass("comment_block J_asyncCmt");
                    if (elements != null && elements.size() > 0) {
                        List<HotelComment> hotelComments = new ArrayList<HotelComment>();
                        for (Element e : elements) {
                            try {
                                HotelComment hotelComment = new HotelComment();
                                List<HotelCommentImages> list = new ArrayList<HotelCommentImages>();
                                Element eltImg = e.selectFirst("div.user_info > p.head > span.img > img");
                                String img = eltImg == null ? null : eltImg.attr("src");//用户头像
                                System.out.println("img:" + img);
                                Element eltName = e.selectFirst("div.user_info > p.name > span");
                                String name = eltName == null ? null : eltName.text();//用户名称
                                System.out.println("name:" + name);
                                Element eltscore = e.selectFirst("div.comment_main > p > span.score > span");
                                String score = eltscore == null ? 0 + "" : eltscore.text();//评分总分
                                System.out.println("score:" + score);
                                Element eltggscore = e.selectFirst("div.comment_main > p > span.small_c");
                                String ggscore = eltggscore == null ? null : eltggscore.attr("data-value");//各个维度评分
                                System.out.println("ggscore:" + ggscore);
                                Element elttype = e.selectFirst("div.comment_main > p > span.type");
                                String type = elttype == null ? null : elttype.text();//出游类型
                                System.out.println("type:" + type);
                                Element elttime = e.selectFirst("div.comment_main > p > span.date");
                                String time = elttime == null ? null : elttime.text();//出游时间
                                System.out.println("time:" + time);
                                Element eltbad = e.selectFirst("div.comment_main > p > a");
                                String bad = eltbad == null ? null : eltbad.text();//房型
                                System.out.println("bad:" + bad);
                                Element eltdate = e.selectFirst("div.comment_main > div.comment_txt > div.comment_bar > p > span");
                                String date = eltdate == null ? null : eltdate.text().replace("发表于", "");//评论时间
                                System.out.println("date:" + date);
                                Element eltintro = e.selectFirst("div.comment_main > div.comment_txt > div.J_commentDetail");
                                String intro = eltintro == null ? null : EmojiUtil.emojiConvert1(eltintro.text());//评论内容
                                System.out.println("intro:" + intro);
                                Element eltreplay = e.selectFirst("div.comment_main > div.htl_reply > p.text");
                                String replay = eltreplay == null ? null : EmojiUtil.emojiConvert1(eltreplay.text());//酒店回复内容
                                System.out.println("replay:" + replay);
                                Element picturediv = e.selectFirst("div.comment_main > div > div.comment_pic");
                                String purl = "";
                                if (picturediv != null) {
                                    Elements picture = picturediv.getElementsByClass("pic");//评论的图集
                                    if (picture != null && picture.size() > 0) {
                                        for (Element pic : picture) {
                                            Element imgs = pic.selectFirst("img.p");
                                            String url1 = imgs == null ? null : imgs.attr("src");
                                            HotelCommentImages hotelCommentImages = new HotelCommentImages();
                                            hotelCommentImages.setUrl(url1);
                                            list.add(hotelCommentImages);
                                            purl = purl + "https:" + url1 + ";";
                                            System.out.println("url1:" + url1);
                                        }
                                    }
                                }

                                hotelComment.setComment(intro);
                                hotelComment.setBad(bad);
                                hotelComment.setCommentDate(date);
                                hotelComment.setHeadImg(img);
                                hotelComment.setName(name);
                                hotelComment.setReplay(replay);
                                hotelComment.setTime(time);
                                hotelComment.setType(type);
                                hotelComment.setScore(score);
                                hotelComment.setHotelId(Long.valueOf(getId()));
                                hotelComment.setCommentUrl(purl);
                                hotelComments.add(hotelComment);
//                            hotelComment = hotelCommentDao.save(hotelComment);
//                                if (hotelComment.getId() == null) {
//                                    throw new RuntimeException("保存失败");
//                                }

                            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                                unsupportedEncodingException.printStackTrace();
                            }
                        }
                        hotelCommentDao.saveEntities(hotelComments);
                    }
                }
                if (getCurrentPage() == getPageNumber())
                    Thread.currentThread().interrupt();
                if (getCurrentPage() < getPageNumber()) {
                    String even = oceanball(getId(), gethead(getId()), null);
                    addTargetUrl(page, getId(), even, getCurrentPage() + 1);
                }
            } else {
                try {
                    setId(url.replace(".html", ""));

                    String hotel = page.getRawText();
                    Document doc = Jsoup.parse(hotel);//获取酒店信息
                    Element e = doc.selectFirst("#J_htl_info > div.name > h2.cn_n");
                    String even = oceanball(getId(), gethead(getId()), null);//获取even
                    int currentPage = 1;
                    addTargetUrl(page, getId(), even, currentPage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException n){
//            try {
                n.printStackTrace();
                return;
//                Thread.sleep(30000l);
//            }catch (InterruptedException interrupted){
//                System.out.println(interrupted.getMessage());
//            }
        }catch (InterruptedException interruptedException){
            System.out.println(interruptedException.getMessage());
        }
    }

    public void addTargetUrl(Page page, String id, String even, int currentPage){
        String pl = hotelCommentUrl +
                "?MasterHotelID=" + id + "&hotel=" + id + "&NewOpenCount=0&AutoExpiredCount=0&RecordCount=2656&OpenDate=&keywordPress=1&card=-1&property=-1" +
                "&UserType=&productcode=&keyword=&roomName=&orderBy=2&currentPage="+currentPage+"&viewVersion=c&contyped=0" +
                "&eleven="+even+"&callback="+getcallback(15)+"&_="+System.currentTimeMillis();
        Request request = new Request();
        request.addHeader("Host", "hotels.ctrip.com");
        request.addHeader("Accept", "*/*");
        request.addHeader("Cache-Control", "max-age=0");
        request.addHeader("If-Modified-Since", "Thu, 01 Jan 1970 00:00:00 GMT");
        request.addHeader("Content-Type","application/x-javascript; charset=utf-8");
        request.addHeader("Referer", "http://hotels.ctrip.com/hotel/" + id + ".html");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
        request.setUrl(pl);
        request.setMethod("get");

        List<String> list = page.getHeaders().get("Set-Cookie");
        if(null != list && list.size()>0){
            String o1 = list.get(0);
            setKey1(o1.substring(0, o1.indexOf("=")));
            setValue1(o1.substring(o1.indexOf("=")+1));
            if(list.size()>1){
                String o2 = list.get(1);
                setKey2(o2.substring(0, o2.indexOf("=")));
                setValue2(o2.substring(o2.indexOf("=")+1));
            }
        }

        request.addCookie(getKey1(), getValue1());
        request.addCookie(getKey2(), getValue2());

        page.addTargetRequest(request);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public Map gethead(String hotelid){
        Map map = new HashMap();
        map.put("Host", "hotels.ctrip.com");

        map.put("Accept", "*/*");
        map.put("Cache-Control", "max-age=0");
        map.put("If-Modified-Since", "Thu, 01 Jan 1970 00:00:00 GMT");
        map.put("Content-Type","application/x-javascript; charset=utf-8");
        map.put("Accept-Language", "zh-CN,zh;q=0.8");
        map.put("Referer", "http://hotels.ctrip.com/hotel/" + hotelid + ".html");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
        return  map;
    }

    public String oceanball(String hotelid, Map headMap, Map cookies){

        try {
            String callback = getcallback(15);
            HttpClient h=new JavaHttpClient();
            long currtime = System.currentTimeMillis();
            String oceanball = "http://hotels.ctrip.com/domestic/cas/oceanball?callback="+callback+"&_="+currtime+"";
            HttpResponse res=h.doGet2(oceanball,headMap,cookies);
            String ocean = res.getResponseString();
            ocean = ocean.replace("eval","JSON.stringify");
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            ocean = String.valueOf( engine.eval(ocean));
            ocean = ocean.replace(callback,"var eleven=" + callback);
            ocean = String.valueOf(engine.eval(new StringReader(ocean)));
            ScriptEngineManager manager1 = new ScriptEngineManager();
            ScriptEngine engine1 = manager1.getEngineByName("javascript");

            engine1.eval("var hotel_id = \""+hotelid+"\"; var site = {}; site.getUserAgent = function(){}; var Image = function(){}; var window = {}; window.document = window.document = {body:{innerHTML:\"1\"}, documentElement:{attributes:{webdriver:\"1\"}}, createElement:function(x){return {innerHTML:\"1\"}}}; var document = window.document;window.navigator = {\"appCodeName\":\"Mozilla\", \"appName\":\"Netscape\", \"language\":\"zh-CN\", \"platform\":\"Win\"}; window.navigator.userAgent = site.getUserAgent(); var navigator = window.navigator; window.location = {}; window.location.href = \"http://hotels.ctrip.com/hotel/\"+hotel_id+\".html\"; var location = window.location;" +
                    " var navigator = {userAgent:{indexOf: function(x){return \"1\"}}, geolocation:\"1\"};var getEleven = 'zgs';  " );
            engine1.eval("var "+callback+" = function(a){getEleven = a;};");
            engine1.eval(ocean);
            String eleven = "";
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine1;
                eleven = (String) invocable.invokeFunction("getEleven");//4.使用 invocable.invokeFunction掉用js脚本里的方法，第一個参数为方法名，后面的参数为被调用的js方法的入参
            }
            return eleven;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  String getcallback(int number){
        String s[]={"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String cal="CAS";
        for(int i=0;i<number;i++){
            int t= (int) Math.ceil(51 * Math.random());
            cal=cal+s[t];
        }
        return cal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
