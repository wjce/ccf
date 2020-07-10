package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wangjunce 2018/10/30 15:15
 */
@Entity
@Table(name = "tb_hotel_city_url")
public class HotelCityUrl {

    private Long id;

    private Date createDate = new Date();

    private String url;

    private String name;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
