package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.SmallPigSchool;
import com.wjc.ccf.repository.custom.SmallPigSchoolDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallPigSchoolDao extends JpaRepository<SmallPigSchool, Long>, SmallPigSchoolDaoCustom {
}
