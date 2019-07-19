package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysPermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysPermisDao extends JpaRepository <SysPermis, String> {
    // 根据权限id，查询对象
    @Query(value = "select * from sys_permis where permis_id = ?1", nativeQuery = true)
    SysPermis getSysPermisByPermisId(String permisId);
}
