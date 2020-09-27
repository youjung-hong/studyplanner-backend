package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.repository.custom.TaskTimeLogCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, Long>, TaskTimeLogCustomRepository {
    List<TaskTimeLog> findAllByTaskId(Long taskId);
}
