package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.RolePermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RolePermisDao extends JpaRepository <RolePermis, String> {
    //根据角色id查询权限
    @Query(value = "select * from role_permis where role_id = ?1", nativeQuery = true)
    List<RolePermis> getRolePermisByRoleId(String roleId);

    //根据角色id查询权限id
    @Query(value = "select permis_id from role_permis where role_id = ?1", nativeQuery = true)
    String[] getPermisIdListByRoleId(String roleId);

    //根据角色id删除该角色所有权限
    @Transactional
    @Modifying
    @Query(value = "delete from role_permis where role_id = ?1", nativeQuery = true)
    void deleteByRoleId(String roleId);

    //根据角色id和权限id查询
    @Query(value = "select * from role_permis where role_id = ?1 and permis_id = ?2", nativeQuery = true)
    RolePermis getByRoleAndPermis(String roleId, String permisId);

}
