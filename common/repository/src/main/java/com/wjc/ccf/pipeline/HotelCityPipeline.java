package com.wjc.ccf.pipeline;

import com.wjc.ccf.domain.HotelCityUrl;
import com.wjc.ccf.repository.dao.HotelCityUrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author wangjunce 2018/10/30 15:17
 */
@Component
public class HotelCityPipeline implements PageModelPipeline<HotelCityUrl> {

    @Autowired
    private HotelCityUrlDao hotelCityUrlDao;

    public HotelCityUrl hotelCityUrl;

    @Override
    public void process(HotelCityUrl hotelCityUrl, Task task) {
        HotelCityUrl hotelCityUrl1 = hotelCityUrlDao.save(this.hotelCityUrl);
        if(hotelCityUrl1.getId() != null)
            System.out.println("保存成功");
        Thread.currentThread().interrupt();
    }
}
