package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysRoleDao extends JpaRepository <SysRole, String> {
    //根据角色名称查询角色id
    @Query(value = "select role_id from sys_role where role_name = ?1", nativeQuery = true)
    String getRoleIdByRoleName(String roleName);

    //根据角色名称查询角色id
    @Query(value = "select role_id from sys_role where role_name = ?1", nativeQuery = true)
    String getSysRoleIdByName(String roleName);
}
