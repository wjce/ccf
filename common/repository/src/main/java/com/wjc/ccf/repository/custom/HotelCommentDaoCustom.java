package com.wjc.ccf.repository.custom;

import com.wjc.ccf.domain.HotelComment;

import java.util.List;

/**
 * @author wangjunce 2018/11/21 11:37
 */
public interface HotelCommentDaoCustom {

    void saveEntities(List<HotelComment> list);
}
