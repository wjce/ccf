package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商圈
 */
@Entity
@Table(name = "trading_area")
public class TradingArea {

    private Long id;

    /** 携程商圈id */
    private Long ctripId;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    private String name;

    /** 范围/详情 */
    private String detail;

    private Date createDate;

    private Long cityId;
//    @Transient
//    private List<TradingAreaReference> list = new ArrayList<TradingAreaReference>();

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCtripId() {
        return ctripId;
    }

    public void setCtripId(Long ctripId) {
        this.ctripId = ctripId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public TradingArea(){}
    public TradingArea(Long id, Long ctripId, String lng, String lat, String name, String detail, Date createDate, Long cityId) {
        this.id = id;
        this.ctripId = ctripId;
        this.lng = lng;
        this.lat = lat;
        this.name = name;
        this.detail = detail;
        this.createDate = createDate;
        this.cityId = cityId;
    }

    //    public List<TradingAreaReference> getList() {
//        return list;
//    }
//
//    public void setList(List<TradingAreaReference> list) {
//        this.list = list;
//    }
}
