package com.wjc.ccf.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.tsxsw.com/html/\\w+/\\w+/\\w+.html")
@Document(collection = "tb_book")
public class BookModel {

    @Id
    private ObjectId objectId;

    @ExtractBy("//head/title/text()")
    private String title;

    @ExtractBy("//div[@class='main w chapter']/div[@class='content']/p/text()")
    private String content;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
