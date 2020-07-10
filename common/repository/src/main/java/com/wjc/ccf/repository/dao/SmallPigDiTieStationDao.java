package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.SmallPigDiTieStation;
import com.wjc.ccf.repository.custom.SmallPigDiTieStationDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallPigDiTieStationDao extends JpaRepository<SmallPigDiTieStation, Long>, SmallPigDiTieStationDaoCustom {
}
