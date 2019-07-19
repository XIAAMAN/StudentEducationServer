package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "role_permis")
public class RolePermis {
    @Id
    @Column(name = "role_permis_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String rolePermisId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "permis_id")
    private String permisId;

    @Column(name = "role_permis_state")
    private String rolePermisState;

    public String getRolePermisId() {
        return rolePermisId;
    }

    public void setRolePermisId(String rolePermisId) {
        this.rolePermisId = rolePermisId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermisId() {
        return permisId;
    }

    public void setPermisId(String permisId) {
        this.permisId = permisId;
    }

    public String getRolePermisState() {
        return rolePermisState;
    }

    public void setRolePermisState(String rolePermisState) {
        this.rolePermisState = rolePermisState;
    }

    //重写equals方法, 只要两个id相同则判断两个对象相同
    @Override
    public boolean equals(Object o) {
        if (o instanceof RolePermis) {
            RolePermis rolePermis = (RolePermis) o;
            return this.rolePermisId.equals(rolePermis.rolePermisId);
        }
        return super.equals(o);
    }
}
