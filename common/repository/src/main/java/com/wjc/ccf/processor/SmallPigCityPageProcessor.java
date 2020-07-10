package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.domain.*;
import com.wjc.ccf.repository.dao.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjc
 * @description
 * @date 2019/8/5
 */
@Component
public class SmallPigCityPageProcessor implements PageProcessor {

    @Autowired
    private SmallPigCityDao smallPigCityDao;
    @Autowired
    private SmallPigDiTieNumDao smallPigDiTieNumDao;
    @Autowired
    private SmallPigDiTieStationDao smallPigDiTieStationDao;
    @Autowired
    private SmallPigSchoolDao smallPigSchoolDao;
    @Autowired
    private SmallPigHotelDao smallPigHotelDao;

    String http = "http://";
    String url = ".xiaozhu.com/search-duanzufang-0/";
    Long cityId = 0l;

    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {
        try {
            if(page.getRequest().getUrl().contains(url)){
                saveOthers(page);
            }else{
                saveCItys(page);
            }
        }catch (Exception e){
            try {
                Thread.sleep(5000l);
            }catch (Exception ee){
                ee.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveCItys(Page page){
        String html = page.getRawText();
        try {
            html = new String(html.getBytes("utf-8"), "utf-8");
            JSONObject jsonObject = JSONObject.parseObject(html);
            JSONObject sucmsg = (JSONObject)jsonObject.get("sucmsg");
            JSONObject internalAllCity = (JSONObject)sucmsg.get("internalAllCity");

            List<SmallPigCity> listAll = new ArrayList<SmallPigCity>();
            for (String key : internalAllCity.keySet()) {
                JSONArray cityArr = (JSONArray) internalAllCity.get(key);
                for (int i = 0; i < cityArr.size(); i++) {
                    JSONObject object = JSONObject.parseObject(cityArr.get(i).toString());
                    String shortName = object.get("short_name").toString();
                    String provinceName = object.get("province_name").toString();
                    object.put("short_name", new String( shortName.getBytes("utf-8") , "utf-8"));
                    object.put("province_name", new String( provinceName.getBytes("utf-8") , "utf-8"));
                }
                List<SmallPigCity> list = JSONArray.parseArray(cityArr.toJSONString(), SmallPigCity.class);
                listAll.addAll(list);
            }

//            smallPigCityDao.saveAll(listAll);

            List<String> urlList = new ArrayList<String>(listAll.size());
            for (SmallPigCity smallPigCity : listAll) {
                urlList.add(http + smallPigCity.getDomain() + url);
            }

            page.addTargetRequests(urlList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveOthers(Page page){
        String urls = page.getRequest().getUrl();
        String py = urls.substring(urls.indexOf("//")+2, urls.indexOf("."));
        SmallPigCity smallPigCity = smallPigCityDao.findByDomain(py);
        cityId = smallPigCity.getCityId();

        String html = page.getRawText();
        Elements elements = Jsoup.parse(html).getElementsByClass("position_box");

        int size = elements.size();
        for (int i = 0; i < size; i++) {
            if(size == 7) {
                if (i == 2) {
                    getDiTie(elements.get(i));
                } else if (i == 5) {
                    getSchool(elements.get(i));
                } else if (i == 6) {
                    getHotel(elements.get(i));
                }
            }else if(size == 6){
                if (i == 4) {
                    getSchool(elements.get(i));
                } else if (i == 5) {
                    getHotel(elements.get(i));
                }
            }
        }
    }

    public void getHotel(Element element){
        Elements elements = element.getElementsByTag("div");
        Elements hotels = elements.get(1).getElementsByTag("span");

        List<SmallPigHotel> hotelList = new ArrayList<SmallPigHotel>();
        for (Element hotel : hotels) {
            String spanText = hotel.text();
            SmallPigHotel pigHotel = new SmallPigHotel();
            pigHotel.setName(spanText);
            pigHotel.setCityId(cityId);
            hotelList.add(pigHotel);
            System.out.println("学校：" + spanText);
        }
        smallPigHotelDao.saveAll(hotelList);
    }

    public void getSchool(Element element){
        Elements elements = element.getElementsByTag("div");
        Elements schools = elements.get(1).getElementsByTag("span");

        List<SmallPigSchool> hotelList = new ArrayList<SmallPigSchool>();
        for (Element school : schools) {
            String spanText = school.text();
            SmallPigSchool pigSchool = new SmallPigSchool();
            pigSchool.setName(spanText);
            pigSchool.setCityId(cityId);
            hotelList.add(pigSchool);
            System.out.println("学校：" + spanText);
        }
        smallPigSchoolDao.saveAll(hotelList);
    }

    public void getDiTie(Element element){

        /** 地铁线路*/
        Elements elements = element.getElementsByTag("div");
        Elements nums = elements.get(1).getElementsByTag("span");
        List<SmallPigDiTieNum> diTieNumList = new ArrayList<SmallPigDiTieNum>();

        for (Element num : nums) {
            SmallPigDiTieNum diTieNum = new SmallPigDiTieNum();
            String spanClass = num.attr("key");
            String spanText = num.text();

            diTieNum.setTitle(spanText);
            diTieNum.setCityId(cityId);
            diTieNum.setNum(spanClass);

            diTieNumList.add(diTieNum);
            System.out.println("地铁线路: "+spanClass + ",text: " + spanText);
        }
        smallPigDiTieNumDao.saveAll(diTieNumList);

        /**地铁站*/
        Elements stationSpan = elements.get(2).getElementsByTag("span");
        List<SmallPigDiTieStation> stationList = new ArrayList<SmallPigDiTieStation>();
        for (Element station : stationSpan) {
            String spanClass = station.attr("class");
            String spanText = station.text();
            String num = spanClass.substring(5, spanClass.length());
            SmallPigDiTieNum smallPigDiTieNum = smallPigDiTieNumDao.findByNumAndCityId(num, cityId);

            SmallPigDiTieStation pigDiTieStation = new SmallPigDiTieStation();
            pigDiTieStation.setName(spanText);
            pigDiTieStation.setDitieId(smallPigDiTieNum.getId());
            pigDiTieStation.setCityId(smallPigDiTieNum.getCityId());

            stationList.add(pigDiTieStation);
            System.out.println("地铁站class: " + spanClass + ",站名" + spanText);
        }

        smallPigDiTieStationDao.saveAll(stationList);
    }

    @Override
    public Site getSite() {
        site.addHeader("xsrf-token", "facb00f2765cad8bff7f2775ffcba2b8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        return site;
    }
}
