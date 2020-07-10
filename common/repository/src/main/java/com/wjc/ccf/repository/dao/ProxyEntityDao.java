package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.custom.ProxyEntityDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjunce 2018/11/1 14:36
 */
public interface ProxyEntityDao extends JpaRepository<ProxyEntity, Long>, ProxyEntityDaoCustom {

    List<ProxyEntity> findByState(int state);
}
