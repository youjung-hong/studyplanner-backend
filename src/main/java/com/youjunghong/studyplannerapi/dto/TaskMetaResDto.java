package com.youjunghong.studyplannerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class TaskMetaResDto {
    List<SubjectResDto> subjects;
    List<TaskResDto> tasks;
    List<TaskTimeLogResDto> taskTimeLogs;
}
