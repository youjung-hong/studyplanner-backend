package com.youjunghong.studyplannerapi.controller;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.dto.TaskReqDto;
import com.youjunghong.studyplannerapi.dto.TaskResDto;
import com.youjunghong.studyplannerapi.service.SubjectService;
import com.youjunghong.studyplannerapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<Page<TaskResDto>> findAll(
            @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
            @PageableDefault Pageable pageable
    ) {
        if (date == null) {
            date = LocalDate.now();
        }

        return new ResponseEntity<>(taskService.findAll(date, pageable).map(TaskResDto::toTaskResDto), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResDto> create(@RequestBody TaskReqDto taskReqDto) {
        Optional<Subject> opt = subjectService.findOne(taskReqDto.getSubjectId());

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Task created = taskService.save(Task.builder()
                .subject(opt.get())
                .date(taskReqDto.getDate())
                .title(taskReqDto.getTitle())
                .status(taskReqDto.getStatus())
                .build()
        );

        return new ResponseEntity<>(TaskResDto.toTaskResDto(created), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResDto> findOne(@PathVariable("id") Long id) {
        Optional<Task> opt = taskService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(TaskResDto.toTaskResDto(opt.get()), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResDto> update(@PathVariable("id") Long id, @RequestBody TaskReqDto taskReqDto) {
        Optional<Subject> subjectOpt = subjectService.findOne(taskReqDto.getSubjectId());
        if (!subjectOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Task> taskOpt = taskService.findOne(id);
        if (!taskOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task found = taskOpt.get();
        found.setDate(taskReqDto.getDate());
        found.setTitle(taskReqDto.getTitle());
        found.setStatus(taskReqDto.getStatus());
        found.setSubject(subjectOpt.get());
        Task updated = taskService.save(found);

        return new ResponseEntity<>(TaskResDto.toTaskResDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Task> opt = taskService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        taskService.delete(opt.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
