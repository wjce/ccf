package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_role")
public class Role {
    private Long id;
    private String name;
    private List<UserRole> userRoleList;
    private List<RoleMenu> roleMenuList;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    public List<RoleMenu> getRoleMenuList() {
        return roleMenuList;
    }

    public void setRoleMenuList(List<RoleMenu> roleMenuList) {
        this.roleMenuList = roleMenuList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userRoleList=" + userRoleList +
                ", roleMenuList=" + roleMenuList +
                '}';
    }
}
