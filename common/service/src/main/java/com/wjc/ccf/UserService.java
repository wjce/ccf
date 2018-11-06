package com.wjc.ccf;

import com.wjc.ccf.dao.mapper.UserMapper;
import com.wjc.ccf.domain.RoleMenu;
import com.wjc.ccf.domain.User;
import com.wjc.ccf.domain.UserRole;
import com.wjc.ccf.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
//    @Autowired
//    private UserMapper userMapper;

    public Page<User> list(Pageable pageable){

        return userDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id){
        return userDao.findById(id).get();
    }

    @Transactional
    public User save(User user){
        return userDao.save(user);
    }

    @Transactional
    public Set<String> listPermissions(String name){
        User user = userDao.findByName(name);
        Set<String> set = new HashSet<>();

        for(UserRole userRole : user.getUserRoleList()){
            for(RoleMenu roleMenu : userRole.getRole().getRoleMenuList()){
                set.add(roleMenu.getPermission());
            }
        }
        return set;
    }

    @Transactional
    public User getUserForName(String name){
        return userDao.findByName(name);
    }

}
