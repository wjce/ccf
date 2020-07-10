package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.SmallPigCity;
import com.wjc.ccf.repository.custom.SmallPigCityDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallPigCityDao extends JpaRepository<SmallPigCity, Long>, SmallPigCityDaoCustom {

    SmallPigCity findByDomain(String domain);

    SmallPigCity findByPinyin(String Pinyin);
}
