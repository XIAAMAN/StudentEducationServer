package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysPermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysPermisDao extends JpaRepository <SysPermis, String> {
    // 根据权限id，查询对象,只查询一二级权限
    @Query(value = "select * from sys_permis where permis_id = ?1 and permis_type<=2", nativeQuery = true)
    SysPermis getSysPermisByPermisId(String permisId);

    // 根据权限id，查询对象,只查询权限
    @Query(value = "select * from sys_permis where permis_id = ?1", nativeQuery = true)
    SysPermis getAllRankSysPermisByPermisId(String permisId);

    //根据权限id查询权限值
    @Query(value = "select permis_name_value from sys_permis where permis_id = ?1", nativeQuery = true)
    String getPermisValueByPermisId(String permisId);

    //根据父id，查找所有子权限对象
    @Query(value = "select * from sys_permis where permis_parent_id = ?1", nativeQuery = true)
    List<SysPermis> getPermisListByParentId(String parentId);

    //根据权限值，查询权限id
    @Query(value = "select permis_id from sys_permis where permis_name_value = ?1", nativeQuery = true)
    String getIdByNameValue(String nameValue);

}
