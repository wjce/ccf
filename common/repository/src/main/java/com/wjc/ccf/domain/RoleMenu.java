package com.wjc.ccf.domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_role_menu")
public class RoleMenu {
    private Long id;
    private Role role;
    private SysMenu sysMenu;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "menu_id")
    public SysMenu getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "id=" + id +
                ", role=" + role +
                ", sysMenu=" + sysMenu +
                '}';
    }
}
