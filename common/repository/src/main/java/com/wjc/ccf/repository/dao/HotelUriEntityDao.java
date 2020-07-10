package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.HotelUriEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjunce 2018/11/2 11:21
 */
public interface HotelUriEntityDao extends JpaRepository<HotelUriEntity, Long> {
    List<HotelUriEntity> findByCityIdOrderByIdAsc(Long cityId);
}
