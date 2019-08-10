package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysCollectionService {
    Page<SysCollection> getCollection(Pageable pageable);
    void deleteById(String collectionId);
    void saveCollection(SysCollection sysCollection);
    SysCollection getByCollectionName(String collectionName);
    List<String> getCollectionName();
    String getNameById(String collectionId);
}
