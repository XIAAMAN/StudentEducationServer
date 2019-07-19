package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleDao extends JpaRepository <UserRole, String> {
    // 根据用户id 查询所有的角色对象
    @Query(value = "select * from user_role where user_id = ?1", nativeQuery = true)
    List<UserRole> getUserRoleListByUserId(String userId);
}
