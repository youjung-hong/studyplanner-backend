package com.youjunghong.studyplannerapi.controller;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.dto.TaskReqDto;
import com.youjunghong.studyplannerapi.dto.TaskResDto;
import com.youjunghong.studyplannerapi.dto.TaskTimeLogReqDto;
import com.youjunghong.studyplannerapi.dto.TaskTimeLogResDto;
import com.youjunghong.studyplannerapi.service.SubjectService;
import com.youjunghong.studyplannerapi.service.TaskService;
import com.youjunghong.studyplannerapi.service.TaskTimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TaskTimeLogController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTimeLogService taskTimeLogService;

    @GetMapping("/tasktimelogs")
    public ResponseEntity<List<TaskTimeLogResDto>> findAllByDate(
            @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date
    ) {
        List<TaskTimeLogResDto> taskTimeLogResDtos = taskTimeLogService.findAll(date)
                .stream().map(TaskTimeLogResDto::toTaskTimeLogResDto)
                .collect(Collectors.toList());;
        return new ResponseEntity<>(taskTimeLogResDtos, HttpStatus.OK);
    }

//    @GetMapping("/tasks/{taskId}/logs")
//    public ResponseEntity<List<TaskTimeLogResDto>> findAll(
//            @PathVariable Long taskId,
//            @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date
//    ) {
//        Optional<Task> opt = taskService.findAllByTaskId(taskId);
//
//        if (!opt.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        List<TaskTimeLogResDto> taskTimeLogResDtos = taskTimeLogService.findAll(opt.get(), date)
//                .stream().map(TaskTimeLogResDto::toTaskTimeLogResDto)
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(taskTimeLogResDtos, HttpStatus.OK);
//    }

    @PostMapping("/tasks/{taskId}/logs")
    public ResponseEntity<TaskTimeLogResDto> create(@PathVariable Long taskId, @RequestBody TaskTimeLogReqDto taskTimeLogReqDto) {
        Optional<Task> opt = taskService.findOne(taskId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        TaskTimeLog created = taskTimeLogService.save(TaskTimeLog.builder()
                .task(opt.get())
                .startAt(taskTimeLogReqDto.getStartAt())
                .endAt(taskTimeLogReqDto.getEndAt())
                .build()
        );

        return new ResponseEntity<>(TaskTimeLogResDto.toTaskTimeLogResDto(created), HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskId}/logs/{id}")
    public ResponseEntity<TaskTimeLogResDto> findOne(@PathVariable Long taskId, @PathVariable("id") Long id) {
        Optional<Task> opt = taskService.findOne(taskId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<TaskTimeLog> optLog = taskTimeLogService.findOne(id);

        if (!optLog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(TaskTimeLogResDto.toTaskTimeLogResDto(optLog.get()), HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskId}/logs/{id}")
    public ResponseEntity<TaskTimeLogResDto> update(@PathVariable Long taskId, @PathVariable Long id, @RequestBody TaskTimeLogReqDto taskTimeLogReqDto) {
        Optional<Task> opt = taskService.findOne(taskId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        opt = taskService.findOne(taskId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Optional<TaskTimeLog> optLog = taskTimeLogService.findOne(id);

        if (!optLog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        TaskTimeLog found = optLog.get();
        found.setTask(opt.get());
        found.setStartAt(taskTimeLogReqDto.getStartAt());
        found.setEndAt(taskTimeLogReqDto.getEndAt());
        TaskTimeLog updated = taskTimeLogService.save(found);

        return new ResponseEntity<>(TaskTimeLogResDto.toTaskTimeLogResDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}/logs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId, @PathVariable Long id) {
        Optional<TaskTimeLog> opt = taskTimeLogService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        taskTimeLogService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
