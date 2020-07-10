package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunce 2018/10/30 10:40
 */
@Entity
@Table(name = "tb_hotel_comment")
public class HotelComment {
    private Long id;
    private Date createDate = new Date();
    //酒店id
    private Long hotelId;
    //酒店名称
    private String title;
    //姓名
    private String name;
    //评论
    private String comment;
    //头像
    private String headImg;
    //评分
    private String score;
    //出游类型
    private String type;
    //出游时间
    private String time;
    //房型
    private String bad;
    //评论时间
    private String commentDate;
    //回复
    private String replay;

    private int state = 0;

    //评论图片
    private String commentUrl;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Lob
    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    private List<HotelCommentImages> hotelCommentImagesList = new ArrayList<HotelCommentImages>();

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

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    @Lob
    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    @OneToMany(mappedBy = "hotelComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<HotelCommentImages> getHotelCommentImagesList() {
        return hotelCommentImagesList;
    }

    public void setHotelCommentImagesList(List<HotelCommentImages> hotelCommentImagesList) {
        this.hotelCommentImagesList = hotelCommentImagesList;
    }
}
