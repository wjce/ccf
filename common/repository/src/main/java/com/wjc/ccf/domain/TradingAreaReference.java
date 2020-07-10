package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trading_area_reference")
public class TradingAreaReference {

    private Long id;

    /** 商圈id */
    private Long tradingAreaId;

    private String lng;

    private String lat;

    private Date createDate;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTradingAreaId() {
        return tradingAreaId;
    }

    public void setTradingAreaId(Long tradingAreaId) {
        this.tradingAreaId = tradingAreaId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TradingAreaReference(){}
    public TradingAreaReference(Long id, Long tradingAreaId, String lng, String lat, Date createDate) {
        this.id = id;
        this.tradingAreaId = tradingAreaId;
        this.lng = lng;
        this.lat = lat;
        this.createDate = createDate;
    }
}
