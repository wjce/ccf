package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wangjunce 2018/10/30 10:53
 */
@Entity
@Table(name = "tb_hotel_comment_images")
public class HotelCommentImages {
    private Long id;
    private Date createDate = new Date();
    private String url;
    private HotelComment hotelComment;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_comment_id")
    public HotelComment getHotelComment() {
        return hotelComment;
    }

    public void setHotelComment(HotelComment hotelComment) {
        this.hotelComment = hotelComment;
    }
}
