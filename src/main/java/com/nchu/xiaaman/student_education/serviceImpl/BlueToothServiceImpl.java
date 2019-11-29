package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.BlueToothDao;
import com.nchu.xiaaman.student_education.domain.BlueTooth;
import com.nchu.xiaaman.student_education.service.BlueToothService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BlueToothServiceImpl implements BlueToothService {
    @Resource
    private BlueToothDao blueToothDao;
    @Override
    public void saveBlueToothList(BlueTooth blueTooth) {
        blueToothDao.save(blueTooth);
    }
}
