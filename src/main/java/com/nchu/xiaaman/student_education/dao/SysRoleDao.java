package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleDao extends JpaRepository <SysRole, String> {
    //根据用户id查询
}
