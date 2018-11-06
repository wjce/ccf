package com.wjc.ccf.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User {
    private Long id;
    private Date createDate;
    private Date updateDate;
    //用户名/账号
    private String name;
    private String phone;
    //昵称
    private String nickname;
    private String password;
    private String salt;
    private Integer erroCount;
    //状态 0正常 1错误次数达上限 2冻结
    private Integer state;
    private List<UserRole> userRoleList;

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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getErroCount() {
        return erroCount;
    }

    public void setErroCount(Integer erroCount) {
        this.erroCount = erroCount;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", erroCount=" + erroCount +
                ", state=" + state +
                ", userRoleList=" + userRoleList +
                '}';
    }

    public User(){}
    public User(Long id, Date createDate, Date updateDate, String name, String phone, String nickname, String password, String salt, Integer erroCount, Integer state, List<UserRole> userRoleList) {
        this.id = id;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.password = password;
        this.salt = salt;
        this.erroCount = erroCount;
        this.state = state;
        this.userRoleList = userRoleList;
    }

    public User(String name, String phone, String nickname, String password, Date createDate, Date updateDate, String salt) {
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.salt = salt;
    }
}
