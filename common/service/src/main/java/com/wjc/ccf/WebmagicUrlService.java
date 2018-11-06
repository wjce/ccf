package com.wjc.ccf;

import com.wjc.ccf.domain.WebmagicUrl;
import com.wjc.ccf.repository.dao.WebmagicUrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjunce 2018/10/20 9:55
 */
@Service
public class WebmagicUrlService {
    @Autowired
    private WebmagicUrlDao webmagicUrlDao;

    public WebmagicUrl findUrl(Long id){
        return webmagicUrlDao.findById(id).get();
    }
}
