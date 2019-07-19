package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.RolePermis;

import java.util.List;

public interface RolePermisService {
    List<RolePermis> getRolePermisByRoleId(String roleId);
}
