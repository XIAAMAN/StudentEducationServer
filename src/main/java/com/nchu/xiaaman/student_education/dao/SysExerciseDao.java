package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SysExerciseDao extends JpaRepository<SysExercise, String> {
    //查询题库
    @Query(value = "select * from sys_exercise where exercise_status = 1 and exercise_state = 1", nativeQuery = true)
    Page<SysExercise> getAll(Pageable pageable);

    //删除题目,设置为不可见
    @Transactional
    @Modifying
    @Query(value = "update sys_exercise set exercise_status = 0 where exercise_id =?1", nativeQuery = true)
    void deleteExerciseById(String exerciseId);

    @Query(value = "select * from sys_exercise where exercise_name =?1", nativeQuery = true)
    SysExercise getSysExerciseByName(String exerciseName);

    //通过题目名称和题目标签进行模糊查询
    @Query(value = "select * from sys_exercise where exercise_name like ?1 and exercise_label like ?2", nativeQuery = true)
    Page<SysExercise> getSysExerciseByNameAndLabel(String exerciseName, String exerciseLabel, Pageable pageable);

    //查询待审核的题目
    @Query(value = "select * from sys_exercise where exercise_state = 0 and exercise_status = 1", nativeQuery = true)
    Page<SysExercise> getCheckExercise(Pageable pageable);

    //修改题目，设置不可见，修改审核人
    @Transactional
    @Modifying
    @Query(value = "update sys_exercise set exercise_status = 0, exercise_check_user_name = ?1 where exercise_id =?2", nativeQuery = true)
    void modifyByIdAndCheckName(String checkName, String exerciseId);

    //修改题目，状态设置为通过审核
    @Transactional
    @Modifying
    @Query(value = "update sys_exercise set exercise_state = 1, exercise_check_user_name = ?1 where exercise_id =?2", nativeQuery = true)
    void modifyState(String checkName, String exerciseId);

    //学生查询题目练习
    @Query(value = "select * from sys_exercise where exercise_state = 1 and exercise_status = 1 and exercise_free = 1", nativeQuery = true)
    Page<SysExercise> getExerciseFree(Pageable pageable);

    //查询所有可用通过审核的题目
    @Query(value = "select exercise_name from sys_exercise where exercise_state = 1 and exercise_status = 1", nativeQuery = true)
    List<String> getExerciseNameList();

    //查询所有可用通过审核的题目
    @Query(value = "select exercise_name from sys_exercise where exercise_state = 1 and exercise_status = 1 and exercise_type = ?1", nativeQuery = true)
    List<String> getExerciseNameList(int exerciseType);

    //根据题目名称查询题目id
    @Query(value = "select exercise_id from sys_exercise where exercise_name = ?1", nativeQuery = true)
    String getExerciseIdByName(String exerciseName);

}
