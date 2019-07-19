package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_role")
// 系统用户表
public class SysRole {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_name_value")
    private String roleNameValue;

    @Column(name = "role_description")
    private String roleDescription;

    @Column(name = "role_rank")         //角色等级，等级低的用户不能对等级高的用户进行操作
    private int roleRank;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameValue() {
        return roleNameValue;
    }

    public void setRoleNameValue(String roleNameValue) {
        this.roleNameValue = roleNameValue;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public int getRoleRank() {
        return roleRank;
    }

    public void setRoleRank(int roleRank) {
        this.roleRank = roleRank;
    }
}
