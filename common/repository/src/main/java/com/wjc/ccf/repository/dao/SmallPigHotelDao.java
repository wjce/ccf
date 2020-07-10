package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.SmallPigHotel;
import com.wjc.ccf.repository.custom.SmallPigHotelDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallPigHotelDao extends JpaRepository<SmallPigHotel, Long>, SmallPigHotelDaoCustom {
}
