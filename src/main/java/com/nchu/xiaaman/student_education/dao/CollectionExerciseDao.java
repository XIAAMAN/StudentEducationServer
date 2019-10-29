package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.CollectionExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectionExerciseDao extends JpaRepository<CollectionExercise, String> {
    //通过题目集id删除
    @Transactional
    @Modifying
    @Query(value = "delete from collection_exercise where collection_id = ?1", nativeQuery = true)
    void deleteByCollectionId(String collectionId);

    //通过题目集id和题目id删除
    @Transactional
    @Modifying
    @Query(value = "delete from collection_exercise where collection_id = ?1 and exercise_id = ?2", nativeQuery = true)
    void deleteByCollectionIdAndExerciseId(String collectionId, String exerciseId);

    //通过题目id和题目集id查询
    @Query(value = "select * from collection_exercise where collection_id = ?1 and exercise_id = ?2", nativeQuery = true)
    CollectionExercise getByCollectionIdAndExerciseId(String collectionId, String exerciseId);

    //通过题目集id查询该题目集下所有的题目id
    @Query(value = "select exercise_id from collection_exercise where collection_id = ?1", nativeQuery = true)
    List<String> getExerciseIdListByCollectionId(String collectionId);

    //根据题目id查询当前一条数据
    @Query(value = "select * from collection_exercise where exercise_id = ?1 limit 1", nativeQuery = true)
    CollectionExercise getOneByExerciseId(String exerciseId);


}
