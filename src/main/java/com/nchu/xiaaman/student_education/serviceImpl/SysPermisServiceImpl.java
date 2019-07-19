package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysPermisDao;
import com.nchu.xiaaman.student_education.domain.SysPermis;
import com.nchu.xiaaman.student_education.service.SysPermisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPermisServiceImpl implements SysPermisService {
    @Resource
    private SysPermisDao sysPermisDao;
    @Override
    public SysPermis getSysPermisByPermisId(String permisId) {
        return sysPermisDao.getSysPermisByPermisId(permisId);
    }
}
