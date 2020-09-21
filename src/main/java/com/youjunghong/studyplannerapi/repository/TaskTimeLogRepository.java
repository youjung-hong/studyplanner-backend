package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.repository.custom.TaskTimeLogCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, Long>, TaskTimeLogCustomRepository {
}
