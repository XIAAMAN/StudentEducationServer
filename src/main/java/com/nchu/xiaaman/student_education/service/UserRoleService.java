package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getUserRoleListByUserId(String userId);
    int getMaxRoleRank(String userId);
    List<String> getRoles(int roleRank);
    void saveUserRole(UserRole userRole);
}
