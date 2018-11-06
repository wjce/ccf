package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 爬虫连接
 * @author wangjunce 2018/10/20 9:42
 */
@Table(name = "tb_webmagic_url")
@Entity
public class WebmagicUrl {

    private Long id;

    private Date createDate;

    private Date updateDate;

    /**
     * 连接作用
     */
    private String name;

    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
