package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityDao extends JpaRepository<City, Long> {

    List<City> findByLevelBetween(int level, int leve);
}
