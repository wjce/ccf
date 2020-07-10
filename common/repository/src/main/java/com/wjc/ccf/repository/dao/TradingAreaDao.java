package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.TradingArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingAreaDao extends JpaRepository<TradingArea, Long> {

    List<TradingArea> findByCityId(Long cityId);
}
