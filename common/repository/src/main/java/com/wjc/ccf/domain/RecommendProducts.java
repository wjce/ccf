package com.wjc.ccf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 推荐产品
 */
@Entity
@Table(name = "tb_reommend_products")
public class RecommendProducts {
    private Long id;
    private Date createDate;
    private Date updateDate;
    private Integer state;
    private String name;
    private String url;
    private Integer isInform; //是否被举报
    private Integer informNum;

    @Id
    @GeneratedValue
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getIsInform() {
        return isInform;
    }

    public void setIsInform(Integer isInform) {
        this.isInform = isInform;
    }

    public Integer getInformNum() {
        return informNum;
    }

    public void setInformNum(Integer informNum) {
        this.informNum = informNum;
    }

    @Override
    public String toString() {
        return "RecommendProducts{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", isInform=" + isInform +
                ", informNum=" + informNum +
                '}';
    }
}
