package com.wjc.ccf;

import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.dao.ProxyEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjunce 2018/11/1 14:37
 */
@Service
public class ProxyEntityService {

    @Autowired
    private ProxyEntityDao proxyEntityDao;

    @Transactional(readOnly = true)
    public List<ProxyEntity> findAll(){
        return proxyEntityDao.findByState(0);
    }

    @Transactional
    public void save(ProxyEntity proxyEntity){
        proxyEntityDao.save(proxyEntity);
    }

    @Transactional
    public void setState(){
        proxyEntityDao.updateProxtEntity();
    }
}
