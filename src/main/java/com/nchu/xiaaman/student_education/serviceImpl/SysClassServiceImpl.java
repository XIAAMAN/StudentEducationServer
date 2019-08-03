package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysClassDao;
import com.nchu.xiaaman.student_education.domain.SysClass;
import com.nchu.xiaaman.student_education.service.SysClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysClassServiceImpl implements SysClassService {
    @Resource
    private SysClassDao sysClassDao;
    @Override
    public SysClass getSysClassByNumber(String classNumber) {
        return sysClassDao.getSysClassByNumber(classNumber);
    }

    @Override
    public void saveClass(SysClass sysClass) {
        sysClassDao.save(sysClass);
    }
}
