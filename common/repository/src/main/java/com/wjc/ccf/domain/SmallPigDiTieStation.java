package com.wjc.ccf.domain;

import javax.persistence.*;

/**
 * @author wjc
 * @description
 * @date 2019/8/6
 */
@Entity
@Table(name = "tb_small_pig_ditie_station")
public class SmallPigDiTieStation {

    private Long id;

    private Long ditieId;

    private String name;

    private Long cityId;

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

    public Long getDitieId() {
        return ditieId;
    }

    public void setDitieId(Long ditieId) {
        this.ditieId = ditieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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
