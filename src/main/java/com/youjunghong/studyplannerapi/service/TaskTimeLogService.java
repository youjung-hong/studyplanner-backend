package com.youjunghong.studyplannerapi.service;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.repository.TaskRepository;
import com.youjunghong.studyplannerapi.repository.TaskTimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskTimeLogService {
    @Autowired
    private TaskTimeLogRepository taskTimeLogRepository;

    public List<TaskTimeLog> findAll(LocalDate date) {
        return taskTimeLogRepository.findAllByDate(date);
    }

    public TaskTimeLog save(TaskTimeLog taskTimeLog) {
        return taskTimeLogRepository.save(taskTimeLog);
    }

    public Optional<TaskTimeLog> findOne(Long id) {
        return taskTimeLogRepository.findById(id);
    }

    public void delete(Long id) {
        this.taskTimeLogRepository.deleteById(id);
    }
}
