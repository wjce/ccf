package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.HotelComment;
import com.wjc.ccf.repository.custom.HotelCommentDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author wangjunce 2018/10/30 11:38
 */
public interface HotelCommentDao extends PagingAndSortingRepository<HotelComment, Long>, JpaSpecificationExecutor<HotelComment>, HotelCommentDaoCustom {
}
