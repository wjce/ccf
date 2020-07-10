package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.vdurmont.emoji.EmojiParser;
import com.wjc.ccf.EmojiUtil;
import com.wjc.ccf.domain.HotelComment;
import com.wjc.ccf.domain.HotelCommentImages;
import com.wjc.ccf.domain.HotelUriEntity;
import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.dao.HotelCommentDao;
import com.wjc.ccf.repository.dao.HotelUriEntityDao;
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
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.*;

/** page = 377 beijing
 * @author wangjunce 2018/10/29 15:06
 */
@Component
public class HotelListPageProcessor implements PageProcessor {

    @Autowired
    private HotelCommentDao hotelCommentDao;
    @Autowired
    private ProxyEntityDao proxyEntityDao;
    @Autowired
    private HotelUriEntityDao hotelUriEntityDao;

    private static final String hotelListUrl = "http://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx";

    private int count = 0;
    private int errorCount = 0;

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);
    private List<String> hidList = new ArrayList<String>();
    {
        hidList.add("435383");
        hidList.add("429778");
    }

    @Override
    public void process(Page page) {
        try {
            String cityId = "";
            String cityPY = "";
            String cityCode = "";
            String cityName = "";

            String currentuUrl = page.getRequest().getUrl();
            System.out.println("page---url:" + currentuUrl);
            System.out.println("thread name------> " + Thread.currentThread().getName());
            ++count;
            String test = page.getRawText();
            if(test.equals("{\"notice\":\"非法的参数！\"}")) {
                errorCount++;
                System.out.println("errorCount is : " + errorCount);
                Thread.currentThread().sleep(5000l);
                return;
            }
            test = test.replaceAll("\\\\", "");
            Elements elements = Jsoup.parse(test).getElementsByClass("c_page_num");
            Elements ep = Jsoup.parse(test).getElementsByClass("current");
            String currentPage = "";
            for (Element element : ep) {
                try {
                    Integer.valueOf(element.text());
                    currentPage = element.text();
                } catch (Exception e) {
                    continue;
                }
            }
            Integer cpage = Integer.valueOf(currentPage);

            String pageSize = "";
            for (Element element : elements) {
                pageSize = element.select("input").attr("data-pagecount");
                System.out.println("pageSize:" + pageSize);
            }
            if (cpage <= Integer.valueOf(pageSize)) {

                String uri = "http://hotels.ctrip.com/hotel/";
                if (cpage == 1) {
                    if(!currentuUrl.equals(hotelListUrl)) {

                        String url = currentuUrl.substring(currentuUrl.lastIndexOf("/"));
                        Element e1 = Jsoup.parse(test).getElementById("cityId");
                        Element e2 = Jsoup.parse(test).getElementById("cityPY");
                        Element e3 = Jsoup.parse(test).getElementById("cityCode");
                        Element e4 = Jsoup.parse(test).getElementById("cityName");
                        cityId = e1.attr("value");
                        cityPY = e2.attr("value");
                        cityCode = e3.attr("value");
                        cityName = e4.attr("value");
                        System.out.println("cityId:" + cityId + "cityPY" + cityPY + "cityCode" + cityCode + "cityName:" + cityName + "currentPage:" + currentPage);
                        for (int i = 0; i < Integer.valueOf(pageSize); i++) {
                            Map<String, Object> param = getHotelListParams(cityName, cityPY, cityId, cityCode, cpage.toString());
                            Request request = new Request();
                            request.setExtras(param);
                            request.setMethod(HttpConstant.Method.POST);
                            request.setRequestBody(HttpRequestBody.form(param, "utf-8"));
                            request.setUrl(hotelListUrl);
                            request.setMethod("post");
                            request.addHeader("Host", "hotels.ctrip.com")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Origin", "http://hotels.ctrip.com")
                                    .addHeader("Cache-Control", "max-age=0")
                                    .addHeader("If-Modified-Since", "Thu, 01 Jan 1970 00:00:00 GMT")
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36")
                                    .addHeader("Referer", "http://hotels.ctrip.com/hotel/" + url);
//                request.setRequestBody(httpRequestBody);
                            page.addTargetRequest(request);
                            ++cpage;
                        }
                    }
                    Elements elements1 = Jsoup.parse(test).select("div .hotel_new_list.J_HotelListBaseCell");
                    for (int i = 0; i < elements1.size(); i++) {
                        Elements h2 = elements1.get(i).select("h2");
                        for (Element element : h2) {
                            Elements p = elements1.get(i).select(".hotel_item_htladdress");
                            String id = element.attr("data-id");
                            System.out.println("id:" + id);
//                    if(id.equals("435383") || id.equals("429778"))
//                        continue;
                            getHotel(id);
                            String name = element.getElementsByTag("a").attr("title");
                            HotelUriEntity hotelUriEntity = new HotelUriEntity();
                            hotelUriEntity.setUrl(uri + id + ".html");
                            hotelUriEntity.setName(name);
                            hotelUriEntity.setAddress(p.text());
                            hotelUriEntity.setCityId(Long.valueOf(cityId));
                            hotelUriEntity.setHotelId(Long.valueOf(id));
                            hotelUriEntityDao.save(hotelUriEntity);
                        }
                    }
                }
//                else {
                    test = updateResult(test);
                    JSONObject jsonObject = JSON.parseObject(test);
                    Map map = jsonObject.getInnerMap();
                    System.out.println(map.size());

                    List<Map<String, Object>> list = (List) map.get("hotelPositionJSON");
                    for (Map<String, Object> stringObjectMap : list) {
                        stringObjectMap.put("cityId", cityId);
                        String hid = stringObjectMap.get("id").toString();
//                        if(StringUtils.isNotBlank(hid)){
//                            hidList.forEach(t -> {
//                                if (t.equals(hid))
//                                    return;
//                            });
//                        }
                        stringObjectMap.put("hotelId", stringObjectMap.get("id"));
                        stringObjectMap.remove("id");
                        HotelUriEntity entity = JSON.parseObject(JSON.toJSONString(stringObjectMap), HotelUriEntity.class);
                        entity.setUrl(uri + stringObjectMap.get("hotelId") + ".html");
                        hotelUriEntityDao.save(entity);
                        System.out.println(entity.toString());
                    }
//                }
                if (currentPage.equals(pageSize))
                    Thread.currentThread().interrupt();
                Thread.sleep(400l);

            }
        }catch (StringIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
//            try {
//                Thread.sleep(30000l);
//            }catch (Exception e1){
//                e1.printStackTrace();
//            }
        }catch (InterruptedException e2){
            System.out.println(e2.getMessage());
        }
    }

    public Map getHead(String url){
        Map map = new HashMap();
        map.put("Host", "hotels.ctrip.com");
        map.put("Connection", "keep-alive");
        map.put("Accept-Encoding", "gzip, deflate");
        map.put("Accept", "*/*");
        map.put("Cache-Control", "max-age=0");
        map.put("If-Modified-Since", "Thu, 01 Jan 1970 00:00:00 GMT");
        map.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        map.put("Accept-Language", "zh-CN,zh;q=0.9");
        map.put("Referer", "http://hotels.ctrip.com/hotel/" + url);
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
        return  map;
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

    public void getHotel(String id){

        try {
            String uri="http://hotels.ctrip.com/hotel/"+ id +".html";
            HttpClient h=new JavaHttpClient();
            HttpResponse s = h.doGet(uri,null);//可以自己实现get请求链接  或者参考本人的。
            String hotel=s.getResponseString();
            Document doc=Jsoup.parse(hotel);//获取酒店信息
            Element e= doc.selectFirst("#J_htl_info > div.name > h2.cn_n");
            String hotelname=e.text();
            Map cookies=s.getCookies();
            Map head=gethead(id);
            String even= oceanball(id,head,cookies);//获取even
            int currentPage=0;
            getEiinfo(currentPage,id,head,even,cookies,hotelname);//获取评价信息

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getEiinfo(int currentPage, String hotelid, Map headMap, String eleven, Map cookies, String hotelname){
        try {
            ++count;
            System.out.println("count:  " + count);
            String pl = "http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx?" +
                    "MasterHotelID=" + hotelid + "&hotel=" + hotelid + "&NewOpenCount=0&AutoExpiredCount=0&RecordCount=2365&OpenDate=&keywordPress=1&card=-1&property=-1" +
                    "&UserType=&productcode=&keyword=&roomName=&orderBy=2&currentPage="+currentPage+"&viewVersion=c&contyped=0" +
                    "&eleven="+eleven+"&callback="+getcallback(15)+"&_="+System.currentTimeMillis();

//            List<ProxyEntity> entities = proxyEntityDao.findByState(0);
            JavaHttpClient h = new JavaHttpClient();
//            if(entities.size() != 0){
//                ProxyEntity proxyEntity = entities.get((int)(Math.random() * entities.size()));
//                h.setUseProxy(true);
//                SocketAddress address = new InetSocketAddress(proxyEntity.getUrl(), proxyEntity.getPort());
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
//                h.setProxy(proxy);
//            }
            HttpResponse r = h.doGet2(pl,headMap,cookies);

            String result = r.getResponseString();
            Document doc = Jsoup.parse(result);
            Element element = doc.selectFirst("#divCtripComment > div.comment_detail_list");//得到所有的评论信息div
            if(element != null){
                Elements elements = element.getElementsByClass("comment_block J_asyncCmt");
                if(count >310 && elements != null && elements.size()>0){

                    for (Element e: elements){
                        HotelComment hotelComment = new HotelComment();
                        List<HotelCommentImages> list = new ArrayList<HotelCommentImages>();
                        Element eltImg=e.selectFirst("div.user_info > p.head > span.img > img");
                        String img=eltImg==null?null:eltImg.attr("src");//用户头像
                        System.out.println("img:"+img);
                        Element eltName=e.selectFirst("div.user_info > p.name > span");
                        String name=eltName==null?null:eltName.text();//用户名称
                        System.out.println("name:"+name);
                        Element eltscore=e.selectFirst("div.comment_main > p > span.score > span");
                        String score=eltscore==null?0+"":eltscore.text();//评分总分
                        System.out.println("score:"+score);
                        Element eltggscore=e.selectFirst("div.comment_main > p > span.small_c");
                        String ggscore=eltggscore==null?null:eltggscore.attr("data-value");//各个维度评分
                        System.out.println("ggscore:"+ggscore);
                        Element elttype=e.selectFirst("div.comment_main > p > span.type");
                        String type=elttype==null?null:elttype.text();//出游类型
                        System.out.println("type:"+type);
                        Element elttime=e.selectFirst("div.comment_main > p > span.date");
                        String time=elttime==null?null:elttime.text();//出游时间
                         System.out.println("time:"+time);
                        Element eltbad=e.selectFirst("div.comment_main > p > a");
                        String bad=eltbad==null?null:eltbad.text();//房型
                        System.out.println("bad:"+bad);
                        Element eltdate=e.selectFirst("div.comment_main > div.comment_txt > div.comment_bar > p > span");
                        String date=eltdate==null?null:eltdate.text().replace("发表于","");//评论时间
                        System.out.println("date:"+date);
                        Element eltintro=e.selectFirst("div.comment_main > div.comment_txt > div.J_commentDetail");
                        String intro=eltintro==null?null:eltintro.text();//评论内容
                        intro = EmojiUtil.emojiConvert1(intro);
                        System.out.println("intro:"+intro);
                        Element eltreplay=e.selectFirst("div.comment_main > div.htl_reply > p.text");
                        String replay=eltreplay==null?null:eltreplay.text();//酒店回复内容
                        System.out.println("replay:"+replay);
                        Element picturediv=e.selectFirst("div.comment_main > div > div.comment_pic");
                        String purl="";
                        if(picturediv!=null){
                            Elements picture=picturediv.getElementsByClass("pic");//评论的图集
                            if(picture!=null&&picture.size()>0){
                                for (Element pic:picture){
                                    Element imgs=pic.selectFirst("img.p");
                                    String url=imgs==null?null:imgs.attr("src");
                                    HotelCommentImages hotelCommentImages = new HotelCommentImages();
                                    hotelCommentImages.setUrl(url);
                                    list.add(hotelCommentImages);
                                    purl=purl+url+";";
                                    System.out.println("url:"+url);
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
                        hotelComment.setType(hotelname);
                        hotelComment.setHotelId(Long.valueOf(hotelid));
                        hotelComment.setCommentUrl(purl);
                        hotelComment = hotelCommentDao.save(hotelComment);
                        if(hotelComment.getId()==null) {
                            throw new RuntimeException("保存失败");
                        }
//                        try {
//                            Thread.sleep(300l);
//                        }catch (InterruptedException e1){
//                            System.out.println(e1.getMessage());
//                            continue;
//                        }
                    }
                }
                Elements adiv=doc.select("#divCtripComment > div.c_page_box > div > div.c_page_list.layoutfix > a");//获取分页信息的a标签
                int tpage=Integer.parseInt(adiv.last().text());//得到总页数的值
                Element cPage=doc.selectFirst("#divCtripComment > div.c_page_box > div > div.c_page_list.layoutfix > a.current");//得到当前页的a标签
                int cpage=Integer.parseInt(cPage.text());//得到当前页的值
                if (cpage+1<=tpage){//进行循环读取
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    String even=oceanball(hotelid,headMap,cookies);//获取even
                    getEiinfo(cpage+1,hotelid,headMap,even,cookies,hotelname);//获取评价信息
                }

            }else{
                String even=oceanball(hotelid,headMap,cookies);//获取even
                getEiinfo(currentPage,hotelid,headMap,even,cookies,hotelname);//获取评价信息
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

    public Map<String, Object> getHotelListParams(String cityName, String cityPY, String cityId, String cityCode, String pageNum){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("__VIEWSTATEGENERATOR","DB1FBB6D");
        params.put("cityName", cityName);
        params.put("cityCode", cityCode);
        params.put("RoomGuestCount","1,1,0");
        params.put("sid","0");
        params.put("operationtype","NEWHOTELORDER");
        params.put("IsOnlyAirHotel","F");
        params.put("cityId", cityId);
        params.put("cityPY", cityPY);
        params.put("htlPageView","0");
        params.put("hotelType","F");
        params.put("hasPKGHotel","F");
        params.put("requestTravelMoney","F");
        params.put("isusergiftcard","F");
        params.put("useFG","F");
        params.put("priceRange","-2");
        params.put("promotion","F");
        params.put("prepay","F");
        params.put("IsCanReserve","F");
        params.put("OrderBy","99");
        params.put("hidTestLat","0%7C0");
        params.put("HideIsNoneLogin","T");
        params.put("isfromlist","T");
        params.put("ubt_price_key","htl_search_result_promotion");
        params.put("isHuaZhu","False");
        params.put("htlFrom","hotellist");
        params.put("markType", "1");
        params.put("a", "0");
        params.put("contrast", "0");
        params.put("page", pageNum);
        params.put("contyped","0");
//        HttpClient httpClient = new JavaHttpClient();
//
//        byte[] bt = null;
//        InputStream stream = null;
//        String result = "";
//        try {
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(os);
//            oos.writeObject(params);
//            bt = os.toByteArray();
//            stream = new ByteArrayInputStream(bt);
//            oos.close();
//            os.close();
//            result = httpClient.dopost(hotelListUrl, getHead(url), stream, null).getResponseString();//HttpUtil.getInstance().httpPost(hotelUrl, params);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return params;
    }

    public String updateResult(String result){
        // 数据中有转义符直接转JSON报错，所以这里重新拼接所需要的JSON数据
        String tempHotel = result.substring(result.indexOf("hotelPositionJSON")-1, result.length());
        // 确保截取到indexOf("biRecord"), 减2是因为需要]符号
        String hotelArray = tempHotel.substring(0, tempHotel.indexOf("biRecord") - 2);
        String tempTotalCount = result.substring(result.indexOf("hotelAmount")-1, result.length());
        String totalCount = tempTotalCount.substring(0, tempTotalCount.indexOf(","));
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(totalCount);
        sb.append(",");
        sb.append(hotelArray);
        sb.append("}");
        return sb.toString().replace("\\", "");
    }
    @Override
    public Site getSite() {
        return this.site;
    }
}
