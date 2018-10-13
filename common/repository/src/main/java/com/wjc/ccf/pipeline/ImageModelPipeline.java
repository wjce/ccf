package com.wjc.ccf.pipeline;

import com.wjc.ccf.model.ImagesModel;
import com.wjc.ccf.repository.dao.ImageMongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author wangjunce 2018/10/12 16:34
 */
@Component
public class ImageModelPipeline implements PageModelPipeline<ImagesModel> {

    @Autowired
    private ImageMongoDao imageMongoDao;

    @Override
    public void process(ImagesModel imagesModel, Task task) {
        imageMongoDao.save(imagesModel);
        Thread.currentThread().interrupt();
    }
}
