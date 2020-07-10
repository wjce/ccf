package com.wjc.ccf.domain;

import javax.persistence.*;

/**
 * 携程城市-id
 */
@Entity
@Table(name = "crtip_city")
public class CtripCity {

    private Long id;

    /** 携程城市id */
    private Long cid;

    private String title;

    private String url;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
