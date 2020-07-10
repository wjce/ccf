package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wangjunce 2018/11/1 14:34
 */
@Entity
@Table(name = "tb_proxy_entity")
public class ProxyEntity {

    private Long id;

    private Date createDate = new Date();

    private int state = 0;

    private String url;

    private int port;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
