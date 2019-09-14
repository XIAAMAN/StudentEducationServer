package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysClass;

import java.util.List;

public interface SysClassService {
    SysClass getSysClassByNumber(String classNumber);
    void saveClass(SysClass sysClass);
    List<String> getClassNameList();
    String getClassIdByNumber(String classNumber);
    SysClass getById(String classId);
}
