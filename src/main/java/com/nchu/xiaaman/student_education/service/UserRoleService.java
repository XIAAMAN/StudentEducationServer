package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getUserRoleListByUserId(String userId);
}
