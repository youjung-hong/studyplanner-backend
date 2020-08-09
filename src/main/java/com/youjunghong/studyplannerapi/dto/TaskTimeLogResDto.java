package com.youjunghong.studyplannerapi.dto;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import com.youjunghong.studyplannerapi.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class TaskTimeLogResDto {
    private Long id;
    private Long taskId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static TaskTimeLogResDto toTaskTimeLogResDto(TaskTimeLog taskTimeLog) {
        return new TaskTimeLogResDto(taskTimeLog.getId(), taskTimeLog.getTask().getId(), taskTimeLog.getStartAt(), taskTimeLog.getEndAt());
    }
}
