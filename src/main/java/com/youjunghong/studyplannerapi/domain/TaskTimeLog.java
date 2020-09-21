package com.youjunghong.studyplannerapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youjunghong.studyplannerapi.domain.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
public class TaskTimeLog {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Builder
    public TaskTimeLog(Task task, LocalDateTime startAt, LocalDateTime endAt) {
        this.task = task;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}