package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysClassDao extends JpaRepository<SysClass, String> {

    //根据班级号查询
    @Query(value = "select * from sys_class where class_number = ?1", nativeQuery = true)
    SysClass getSysClassByNumber(String classNumber);

    //查询所有班级名称
    @Query(value = "select class_number from sys_class", nativeQuery = true)
    List<String> getClassNameList();

    //根据班级号查询班级id
    @Query(value = "select class_id from sys_class where class_number = ?1", nativeQuery = true)
    String getClassIdByNumber(String classNumber);

}
