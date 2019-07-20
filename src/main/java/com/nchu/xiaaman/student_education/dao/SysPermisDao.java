package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysPermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysPermisDao extends JpaRepository <SysPermis, String> {
    // 根据权限id，查询对象,只查询一二级权限
    @Query(value = "select * from sys_permis where permis_id = ?1 and permis_type<=2", nativeQuery = true)
    SysPermis getSysPermisByPermisId(String permisId);
}
