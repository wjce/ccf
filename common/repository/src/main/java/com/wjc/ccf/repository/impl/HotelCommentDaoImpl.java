package com.wjc.ccf.repository.impl;

import com.wjc.ccf.domain.HotelComment;
import com.wjc.ccf.repository.custom.HotelCommentDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wangjunce 2018/11/21 11:38
 */
@Repository
public class HotelCommentDaoImpl implements HotelCommentDaoCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveEntities(List<HotelComment> list) {
        String sql = "insert into tb_hotel_comment(create_date, state, hotel_id, title, `name`, comment, head_img, score, `type`, `time`, bad, comment_date, replay, comment_url) values(now(),0,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                if(null != list && list.size() != 0){
                    HotelComment hotelComment = list.get(i);
                    preparedStatement.setLong(1, hotelComment.getHotelId());
                    preparedStatement.setString(2, hotelComment.getTitle());
                    preparedStatement.setString(3, hotelComment.getName());
                    preparedStatement.setString(4, hotelComment.getComment());
                    preparedStatement.setString(5, hotelComment.getHeadImg());
                    preparedStatement.setString(6, hotelComment.getScore());
                    preparedStatement.setString(7, hotelComment.getType());
                    preparedStatement.setString(8, hotelComment.getTime());
                    preparedStatement.setString(9, hotelComment.getBad());
                    preparedStatement.setString(10, hotelComment.getCommentDate());
                    preparedStatement.setString(11, hotelComment.getReplay());
                    preparedStatement.setString(12, hotelComment.getCommentUrl());
                }
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
