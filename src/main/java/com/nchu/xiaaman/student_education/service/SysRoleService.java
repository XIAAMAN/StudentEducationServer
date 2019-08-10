package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysRoleService {

    String getRoleIdByRoleName(String roleName);
    String getSysRoleIdByName(String roleName);
    Page<SysRole> getRoleList(int rankValue, Pageable pageable);
}
