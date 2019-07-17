package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysUserDao extends JpaRepository<SysUser, String> {

    @Query(value = "select * from sys_user where user_name = ?1 and user_password = ?2", nativeQuery = true)
    SysUser getByNameAndPassword(String userName, String password);
}
