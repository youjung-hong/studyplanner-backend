package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByDateIsLikeAndDeletedAtIsNull(LocalDate date, Pageable pageable);
}
