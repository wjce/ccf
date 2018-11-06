package com.wjc.ccf.repository.dao;

import com.wjc.ccf.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByName(String name);
}
