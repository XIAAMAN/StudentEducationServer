package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysPermis;

import java.util.List;

public interface SysPermisService {
    SysPermis getSysPermisByPermisId(String permisId);
    SysPermis getById(String permisId);
    String getPermisValueByPermisId(String permisId);
    SysPermis getAllRankSysPermisByPermisId(String permisId);
    List<SysPermis> getPermisListByParentId(String parentId);
    String getIdByNameValue(String nameValue);

}
