package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SysCollectionDao extends JpaRepository<SysCollection, String> {
    @Query(value = "select * from sys_collection", nativeQuery = true)
    Page<SysCollection> getCollection(Pageable pageable);

    //通过id删除题目集
    @Transactional
    @Modifying
    @Query(value = "delete from sys_collection where collection_id = ?1", nativeQuery = true)
    void deleteById(String collectionId);

    //通过题目集名称查询
    @Query(value = "select * from sys_collection where collection_name = ?1", nativeQuery = true)
    SysCollection getByCollectionName(String collectionName);

    //查询所有题目集名称
    @Query(value = "select collection_name from sys_collection", nativeQuery = true)
    List<String> getCollectionName();

    //根据id查询题目集名称
    @Query(value = "select collection_name from sys_collection where collection_id = ?1", nativeQuery = true)
    String getNameById(String collectionId);
}