package com.youjunghong.studyplannerapi.dto;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class TaskResDto {
    private Long id;
    private Long subjectId;
    private LocalDate date;
    private String title;
    private TaskStatus taskStatus;

    public static TaskResDto toTaskResDto(Task task) {
        return new TaskResDto(task.getId(), task.getSubject().getId(), task.getDate(), task.getTitle(), task.getStatus());
    }
}
