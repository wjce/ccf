package com.wjc.ccf;

import com.wjc.ccf.domain.User;
import com.wjc.ccf.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @Transactional
    public User getUser(Long id){
        return userDao.findOne(id);
    }

    public User save(User user){
        return userDao.save(user);
    }
}
