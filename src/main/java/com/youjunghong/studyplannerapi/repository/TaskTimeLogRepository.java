package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, Long> {
    Page<TaskTimeLog> findAllByTask(Task task, Pageable pageable);
}
