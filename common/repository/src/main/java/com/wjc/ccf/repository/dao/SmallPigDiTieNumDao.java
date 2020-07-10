package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.SmallPigDiTieNum;
import com.wjc.ccf.repository.custom.SmallPigDiTieNumDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallPigDiTieNumDao extends JpaRepository<SmallPigDiTieNum, Long>, SmallPigDiTieNumDaoCustom {

    SmallPigDiTieNum findByNumAndCityId(String num, Long cityId);
}
