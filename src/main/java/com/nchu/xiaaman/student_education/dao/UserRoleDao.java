package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleDao extends JpaRepository <UserRole, String> {
    // 根据用户id 查询所有的角色对象
    @Query(value = "select * from user_role where user_id = ?1", nativeQuery = true)
    List<UserRole> getUserRoleListByUserId(String userId);

    //查询用户最大角色等级值
    @Query(value = "select max(role_rank) from sys_role where role_id in (select role_id from user_role where user_id = ?1)", nativeQuery = true)
    int getMaxRoleRank(String userId);

    //查询比自己角色等级小的所有角色名称
    @Query(value = "select role_name from sys_role where role_rank < ?1", nativeQuery = true)
    List<String> getRoles(int roleRank);
}
