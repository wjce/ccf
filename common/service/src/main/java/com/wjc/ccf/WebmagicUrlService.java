package com.wjc.ccf;

import com.wjc.ccf.domain.WebmagicUrl;
import com.wjc.ccf.repository.dao.WebmagicUrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangjunce 2018/10/20 9:55
 */
@Service
public class WebmagicUrlService {
    @Autowired
    private WebmagicUrlDao webmagicUrlDao;
    private final TransactionTemplate transactionTemplate;

    public WebmagicUrlService(PlatformTransactionManager platformTransactionManager){
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
//        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
//        this.transactionTemplate.setReadOnly(true);
    }


    //使用transactionTemplate进行事物的控制
    public WebmagicUrl someServiceMethod(){
        return transactionTemplate.execute(t -> {
            WebmagicUrl webmagicUrl = new WebmagicUrl();
            try {
                webmagicUrl = webmagicUrlDao.save(new WebmagicUrl());
                webmagicUrlDao.findById(webmagicUrl.getId()).orElseThrow(() -> new RuntimeException());
            }catch (Exception e){
                t.setRollbackOnly();
            }
            return webmagicUrl;
        });
    }

    public WebmagicUrl findUrl(Long id){
        return webmagicUrlDao.findById(id).get();
    }

}
