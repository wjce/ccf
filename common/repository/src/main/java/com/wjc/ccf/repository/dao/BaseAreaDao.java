package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.BaseArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseAreaDao extends JpaRepository<BaseArea, Long> {

    BaseArea findByNameAndCode(String name, String code);

    BaseArea findByNameLike(String name);
}
