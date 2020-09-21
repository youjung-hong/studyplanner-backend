package com.youjunghong.studyplannerapi.repository.custom;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;

import java.time.LocalDate;
import java.util.List;

public interface TaskTimeLogCustomRepository {
    public List<TaskTimeLog> findAllByDate(LocalDate date);
}
