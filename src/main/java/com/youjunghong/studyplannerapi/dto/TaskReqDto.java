package com.youjunghong.studyplannerapi.dto;

import com.youjunghong.studyplannerapi.domain.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskReqDto {
    private Long subjectId;
    private LocalDate date;
    private String title;
    private TaskStatus status;
}
