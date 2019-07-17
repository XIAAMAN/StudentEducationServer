package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysUser;

public interface SysUserService {
    SysUser getByNameAndPassword(String userName, String password);
    void saveUser(SysUser user);
}
