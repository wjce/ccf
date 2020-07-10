package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.HotelCityUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjunce 2018/10/30 15:17
 */
public interface HotelCityUrlDao extends JpaRepository<HotelCityUrl, Long> {

    List<HotelCityUrl> findByName(String name);
}
