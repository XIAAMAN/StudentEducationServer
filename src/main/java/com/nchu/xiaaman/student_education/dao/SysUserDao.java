package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysUserDao extends JpaRepository<SysUser, String> {

    @Query(value = "select * from sys_user where user_name = ?1 and user_password = ?2", nativeQuery = true)
    SysUser getByNameAndPassword(String userName, String password);

    //查询角色级别比自己低的用户
    @Query(value = "select * from sys_user su where user_status = 1 and ?1 >(select max(role_rank) from sys_role ro where ro.role_id in (select role_id from user_role  ur where ur.user_id = su.user_id))", nativeQuery = true)
    Page<SysUser> getUserListByRank(int roleRank, Pageable pageable);

    //查询角色级别比自己低的用户
    @Query(value = "select * from sys_user su where user_status = 1 and user_recommend_name = ?1 and ?2 >(select max(role_rank) from sys_role ro where ro.role_id in (select role_id from user_role  ur where ur.user_id = su.user_id))", nativeQuery = true)
    Page<SysUser> getUserListByRank(String userRecommendName, int roleRank, Pageable pageable);

    //查询角色级别比自己低的用户,通过用户名和用户工号进行模糊查询
    @Query(value = "select * from sys_user su where user_status = 1 and user_name like '%?1%' and " +
            "user_number like '%?2%' and ?3 >(select max(role_rank) from sys_role ro where ro.role_id in (select role_id from user_role  ur where ur.user_id = su.user_id))", nativeQuery = true)
    Page<SysUser> getUserListByUserNameAndUserNumber(String userName, String userNumber, int roleRank, Pageable pageable);

    //查询角色级别比自己低的用户,通过用户名和用户工号进行模糊查询
    @Query(value = "select * from sys_user su where user_status = 1 and user_name like '%?1%' and " +
            "user_number like '%?2%' and user_recommend_name=?3 and ?4 >(select max(role_rank) from sys_role ro where ro.role_id in (select role_id from user_role  ur where ur.user_id = su.user_id))", nativeQuery = true)
    Page<SysUser> getUserListByUserNameAndUserNumber(String userName, String userNumber,String userRecommendName, int roleRank, Pageable pageable);

    //根据用户名查询用户id
    @Query(value = "select user_id from sys_user where user_name = ?1", nativeQuery = true)
    String getUserIdByUserName(String userName);

    @Query(value = "select user_name from sys_user", nativeQuery = true)
    List<String> getUserNames();

    @Query(value = "select user_number from sys_user", nativeQuery = true)
    List<String> getUserNumbers();

    @Query(value = "select * from sys_user where user_name = ?1", nativeQuery = true)
    SysUser getUserByUserName(String userName);

    @Query(value = "select * from sys_user where user_number = ?1", nativeQuery = true)
    SysUser getUserByUserNumber(String userNumber);

    //通过班级查询一个班的用户
    @Query(value = "select * from sys_user where user_class = ?1", nativeQuery = true)
    List<SysUser> getUserListByClass(String userClass);

    //根据mac地址查询用户
    @Query(value = "select * from sys_user where user_mac_address = ?1", nativeQuery = true)
    SysUser getSysUserByMacAddress(String macAddress);
}
