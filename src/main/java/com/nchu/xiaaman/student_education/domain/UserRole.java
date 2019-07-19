package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String userRoldId;

    @Column(name = "user_id")
    private String userId;          //用户id

    @Column(name = "role_id")
    private String roleId;          //角色id

    public String getUserRoldId() {
        return userRoldId;
    }

    public void setUserRoldId(String userRoldId) {
        this.userRoldId = userRoldId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
