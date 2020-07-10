package com.wjc.ccf.repository.impl;

import com.wjc.ccf.domain.ProxyEntity;
import com.wjc.ccf.repository.custom.ProxyEntityDaoCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wangjunce 2018/11/1 15:46
 */
@Repository
public class ProxyEntityDaoImpl implements ProxyEntityDaoCustom {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateProxtEntity() {
        String sql = " update tb_proxy_entity set state = 1 where state = 0";
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateProxtEntity(List<Long> ids) {
        String sql = " update tb_proxy_entity set state = 1 where state = 0 and id in (?)";
        jdbcTemplate.update(sql, ids.toArray());
    }

    public void saveEntitis(List<ProxyEntity> list){
        String sql = "insert into tb_proxy_entity(create_date, state, url, prot) values(now(), 0, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                if(null != list && list.size() != 0){
                    ProxyEntity proxyEntity = list.get(i);
                    preparedStatement.setString(1, proxyEntity.getUrl());
                    preparedStatement.setInt(2, proxyEntity.getPort());
                }
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
