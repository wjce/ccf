package com.wjc.ccf.repository.custom;

import com.wjc.ccf.domain.ProxyEntity;

import java.util.List;

/**
 * @author wangjunce 2018/11/1 15:45
 */
public interface ProxyEntityDaoCustom {

    void updateProxtEntity();

    void updateProxtEntity(List<Long> ids);

    void saveEntitis(List<ProxyEntity> list);
}
