package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.RolePermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermisDao extends JpaRepository <RolePermis, String> {
    //根据角色id查询权限
    @Query(value = "select * from role_permis where role_id = ?1", nativeQuery = true)
    List<RolePermis> getRolePermisByRoleId(String roleId);

}
