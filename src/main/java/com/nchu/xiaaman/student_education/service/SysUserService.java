package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService {
    SysUser getByNameAndPassword(String userName, String password);
    void saveUser(SysUser user);
    Page<SysUser> getUserListByRank(int roleRank, Pageable pageable);
    String getUserIdByUserName(String userName);
    SysUser getUserById(String userId);
}
