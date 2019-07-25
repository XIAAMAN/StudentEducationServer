package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysExerciseService {
    Page<SysExercise> getAll(Pageable pageable);
}
