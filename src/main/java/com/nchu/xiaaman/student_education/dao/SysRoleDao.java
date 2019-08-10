package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysRoleDao extends JpaRepository <SysRole, String> {
    //根据角色名称查询角色id
    @Query(value = "select role_id from sys_role where role_name = ?1", nativeQuery = true)
    String getRoleIdByRoleName(String roleName);

    //根据角色名称查询角色id
    @Query(value = "select role_id from sys_role where role_name = ?1", nativeQuery = true)
    String getSysRoleIdByName(String roleName);

    //查询所有比自己角色等级小的角色
    @Query(value = "select * from sys_role where role_rank < ?1", nativeQuery = true)
    Page<SysRole> getRoleList(int rankValue, Pageable pageable);
}
