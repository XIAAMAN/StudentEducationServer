package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysClassDao extends JpaRepository<SysClass, String> {

    //根据班级号查询
    @Query(value = "select * from sys_class where class_number = ?1", nativeQuery = true)
    SysClass getSysClassByNumber(String classNumber);

}
