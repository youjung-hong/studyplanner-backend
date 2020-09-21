package com.youjunghong.studyplannerapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskTimeLogReqDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;
}
