package com.wjc.ccf.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

@TargetUrl("http://www.tsxsw.com/html/\\w+/\\w+/")
@Document(collection = "tb_books")
//@HelpUrl("http://www.tsxsw.com/html/\\w+/\\w+/\\w+.html")
public class BooksModel {

    @Id
    private ObjectId objectId;
    /**
     * 章节名称
     */
    @ExtractBy("//div[@class='main w']/div[@class='chapterlist']/ul/li/a/text()")
    private List chapter;

    /**
     * 章节链接
     */
    @ExtractBy("//div[@class='main w']/div[@class='chapterlist']/ul/li/a/@href")
    private List url;

    @Override
    public String toString() {
        return "BooksModel{" +
                "objectId=" + objectId +
                ", chapter=" + chapter +
                ", url=" + url +
                '}';
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public List getChapter() {
        return chapter;
    }

    public void setChapter(List chapter) {
        this.chapter = chapter;
    }

    public List getUrl() {
        return url;
    }

    public void setUrl(List url) {
        this.url = url;
    }
}
