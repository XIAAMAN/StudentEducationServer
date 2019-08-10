package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.RolePermis;

import java.util.List;

public interface RolePermisService {
    List<RolePermis> getRolePermisByRoleId(String roleId);
    String[] getPermisIdListByRoleId(String roleId);
    void deleteByRoleId(String roleId);
    void saveRolePermis(RolePermis rolePermis);
    RolePermis getByRoleAndPermis(String roleId, String permisId);
}
