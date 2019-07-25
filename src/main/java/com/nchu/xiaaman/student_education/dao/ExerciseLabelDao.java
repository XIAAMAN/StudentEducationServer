package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.ExerciseLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseLabelDao extends JpaRepository<ExerciseLabel, String> {
}
