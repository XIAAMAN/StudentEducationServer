package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysUserService {
    SysUser getByNameAndPassword(String userName, String password);
    void saveUser(SysUser user);
    Page<SysUser> getUserListByRank(int roleRank, Pageable pageable);
    String getUserIdByUserName(String userName);
    SysUser getUserById(String userId);
    List<String> getUserNames();
    List<String> getUserNumbers();
    Page<SysUser> getUserListByUserNameAndUserNumber(String userName, String userNumber, int roleRank, Pageable pageable);
    @Query(value = "select * from sys_user where user_name = ?1", nativeQuery = true)
    SysUser getUserByUserName(String userName);
    SysUser getUserByUserNumber(String userNumber);
}
