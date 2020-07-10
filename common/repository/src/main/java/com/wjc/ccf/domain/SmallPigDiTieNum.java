package com.wjc.ccf.domain;

import javax.persistence.*;

/**
 * @author wjc
 * @description
 * @date 2019/8/6
 */
@Entity
@Table(name = "tb_small_pig_ditie_num")
public class SmallPigDiTieNum {

    private Long id;

    private Long cityId;

    private String title;

    private String num;

    private String lat;

    private String lng;

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
