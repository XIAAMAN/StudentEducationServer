package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysUserDao extends JpaRepository<SysUser, String> {

    @Query(value = "select * from sys_user where user_name = ?1 and user_password = ?2", nativeQuery = true)
    SysUser getByNameAndPassword(String userName, String password);

    //查询角色级别比自己低的用户
    @Query(value = "select * from sys_user su where user_status = 1 and ?1 >(select max(role_rank) from sys_role ro where ro.role_id in (select role_id from user_role  ur where ur.user_id = su.user_id))", nativeQuery = true)
    Page<SysUser> getUserListByRank(int roleRank, Pageable pageable);

    //根据用户名查询用户id
    @Query(value = "select user_id from sys_user where user_name = ?1", nativeQuery = true)
    String getUserIdByUserName(String userName);
}
