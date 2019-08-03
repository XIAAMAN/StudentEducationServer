package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysClass;

public interface SysClassService {
    SysClass getSysClassByNumber(String classNumber);
    void saveClass(SysClass sysClass);
}
