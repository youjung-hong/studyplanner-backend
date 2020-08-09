package com.youjunghong.studyplannerapi.service;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.repository.SubjectRepository;
import com.youjunghong.studyplannerapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> findAll(LocalDate date, Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize()
        );
        return taskRepository.findAllByDateIsLikeAndDeletedAtIsNull(date, pageable);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findOne(Long id) {
        return taskRepository.findById(id);
    }

    public void delete(Task task) {
        task.setDeletedAt(LocalDateTime.now());
        this.save(task);
    }
}
