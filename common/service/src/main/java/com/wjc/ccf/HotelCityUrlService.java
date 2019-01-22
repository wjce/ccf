package com.wjc.ccf;

import com.wjc.ccf.domain.HotelCityUrl;
import com.wjc.ccf.repository.dao.HotelCityUrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjunce 2018/11/21 11:28
 */
@Service
public class HotelCityUrlService {
    @Autowired
    private HotelCityUrlDao hotelCityUrlDao;

    public List<String> findHotelList(){
        List<HotelCityUrl> list = hotelCityUrlDao.findAll();
        List<String> urls = new ArrayList<String>(list.size());
        list.forEach(t -> urls.add(t.getUrl()));
        return urls;
    }
}
