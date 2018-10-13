package com.wjc.ccf.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;
import java.util.Set;

/**
 * 百度图片-头像
 * @author wangjunce 2018/10/12 16:14
 */
@Document(collection = "tb_images")
public class ImagesModel {

    @Id
    private ObjectId objectId;

    private Set url;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Set getUrl() {
        return url;
    }

    public void setUrl(Set url) {
        this.url = url;
    }
}
