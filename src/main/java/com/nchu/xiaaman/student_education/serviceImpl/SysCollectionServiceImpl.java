package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysCollectionDao;
import com.nchu.xiaaman.student_education.domain.SysCollection;
import com.nchu.xiaaman.student_education.service.SysCollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCollectionServiceImpl implements SysCollectionService {
    @Resource
    private SysCollectionDao sysCollectionDao;
    @Override
    public Page<SysCollection> getCollection(Pageable pageable) {
        return sysCollectionDao.getCollection(pageable);
    }

    @Override
    public void deleteById(String collectionId) {
        sysCollectionDao.deleteById(collectionId);
    }

    @Override
    public void saveCollection(SysCollection sysCollection) {
        sysCollectionDao.save(sysCollection);
    }

    @Override
    public SysCollection getByCollectionName(String collectionName) {
        return sysCollectionDao.getByCollectionName(collectionName);
    }

    @Override
    public List<String> getCollectionName() {
        return sysCollectionDao.getCollectionName();
    }

    @Override
    public String getNameById(String collectionId) {
        return sysCollectionDao.getNameById(collectionId);
    }

    @Override
    public Page<SysCollection> getCollectionByCourseId(String courseId, Pageable pageable) {
        return sysCollectionDao.getCollectionByCourseId(courseId, pageable);
    }
}
