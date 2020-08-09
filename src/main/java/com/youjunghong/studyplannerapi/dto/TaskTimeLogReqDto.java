package com.youjunghong.studyplannerapi.dto;

import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskTimeLogReqDto {
    private Long taskId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
