package com.wjc.ccf.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.domain.TradingArea;
import com.wjc.ccf.domain.TradingAreaReference;
import com.wjc.ccf.repository.dao.TradingAreaDao;
import com.wjc.ccf.repository.dao.TradingAreaReferenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TradingAreaPageProcessor implements PageProcessor {

    @Autowired
    private TradingAreaDao tradingAreaDao;
    @Autowired
    private TradingAreaReferenceDao tradingAreaReferenceDao;

    public Long cityId;
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(3000);

    @Override
    public void process(Page page) {

        String str = page.getRawText();
        str = str.substring(str.indexOf("{"), str.length());
        Map<String, JSONArray> map = (Map) JSON.parse(str);
        map.forEach((k,v) -> {
            for (Object o : v) {
                JSONObject jsonObject = (JSONObject) o;
                TradingArea tradingArea = new TradingArea(null, jsonObject.getLong("id"), jsonObject.getString("lng"), jsonObject.getString("lat"), jsonObject.getString("name"), jsonObject.getString("desc"), new Date(), cityId);
                tradingArea = tradingAreaDao.save(tradingArea);
                JSONArray pathArr = jsonObject.getJSONArray("path");
                for (Object arr : pathArr) {
                    JSONObject jsonObject2 = (JSONObject) arr;
                    TradingAreaReference tradingAreaReference = new TradingAreaReference(null, tradingArea.getId(), jsonObject2.getString("lng"), jsonObject2.getString("lat"), new Date());
                    tradingAreaReferenceDao.save(tradingAreaReference);
                }
            }
            return;
        });

        List<TradingAreaReference> tradingAreaReferences = JSON.parseArray(str, TradingAreaReference.class);
    }

    @Override
    public Site getSite() {
        return site;
    }

}
