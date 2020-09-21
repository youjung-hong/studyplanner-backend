package com.youjunghong.studyplannerapi.controller;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.dto.*;
import com.youjunghong.studyplannerapi.service.SubjectService;
import com.youjunghong.studyplannerapi.service.TaskService;
import com.youjunghong.studyplannerapi.service.TaskTimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TaskMetaController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTimeLogService taskTimeLogService;

    @GetMapping("/taskmeta")
    public ResponseEntity<TaskMetaResDto> getTaskMeta(
            @RequestParam(name="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date
    ) {
        if (date == null) {
            date = LocalDate.now();
        }

        Integer page = 0;
        Page<Subject> subjectPage;
        List<SubjectResDto> subjectResDtos = new ArrayList<>();

        do {
            subjectPage = subjectService.findAll(PageRequest.of(page, 100));
            subjectResDtos.addAll(
                    subjectPage.getContent().stream().map(SubjectResDto::toSubjectResDto).collect(Collectors.toList())
            );
            page += 1;
        } while (subjectPage.hasNext());

        page = 0;
        Page<Task> taskPage;
        List<TaskResDto> taskResDtos = new ArrayList<>();
        List<TaskTimeLogResDto> taskTimeLogResDtos = new ArrayList<>();

        do {
            taskPage = taskService.findAll(date, PageRequest.of(page, 100));

            taskPage.getContent().forEach(task -> {
                taskResDtos.add(TaskResDto.toTaskResDto(task));
            });
            addTaskTimeLogs(taskPage.getContent().stream(), taskTimeLogResDtos, date);
            page += 1;
        } while(taskPage.hasNext());

        return new ResponseEntity<>(new TaskMetaResDto(subjectResDtos, taskResDtos, taskTimeLogResDtos), HttpStatus.OK);
    }

    private void addTaskTimeLogs(Stream<Task> stream, List<TaskTimeLogResDto> taskTimeLogResDtos, LocalDate date) {
        stream.forEach(task -> {
            List<TaskTimeLog> taskTimeLogs = taskTimeLogService.findAll(date);
            for (TaskTimeLog taskTimeLog : taskTimeLogs) {
                taskTimeLogResDtos.add(TaskTimeLogResDto.toTaskTimeLogResDto(taskTimeLog));
            }
        });
    }
}
