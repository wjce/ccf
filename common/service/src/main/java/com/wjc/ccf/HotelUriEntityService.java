package com.wjc.ccf;

import com.wjc.ccf.domain.HotelUriEntity;
import com.wjc.ccf.repository.dao.HotelUriEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjunce 2018/11/21 13:04
 */
@Service
public class HotelUriEntityService {

    @Autowired
    private HotelUriEntityDao hotelUriEntityDao;

    public List<String> findHotelList(){
        List<HotelUriEntity> list = hotelUriEntityDao.findAll();
        List<String> urls = new ArrayList<String>(list.size());
        list.forEach(t -> urls.add(t.getUrl()));
        return urls;
    }

    public String findOne(Long id){
        HotelUriEntity hotelUriEntity = hotelUriEntityDao.findById(id).orElse(new HotelUriEntity());
        return hotelUriEntity.getUrl();
    }

    public List<String> findByCityId(Long cityId){
        List<HotelUriEntity> list = hotelUriEntityDao.findByCityIdOrderByIdAsc(cityId);
        List<String> urls = new ArrayList<String>(list.size());
        list.forEach(t -> urls.add(t.getUrl()));
        return urls;
    }

    public List<Long> findByCityId2(Long cityId){
        List<HotelUriEntity> list = hotelUriEntityDao.findByCityIdOrderByIdAsc(cityId);
        List<Long> urls = new ArrayList<Long>(list.size());
        list.forEach(t -> urls.add(t.getHotelId()));
        return urls;
    }
}
