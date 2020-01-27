package com.chanel.toy.nemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
public class TodoItemAction {
    @Id
    @GeneratedValue
    private Long id;
    private Long todoId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long period;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public TodoItemAction() {}

    public TodoItemAction(Long todoId, LocalDateTime startAt, LocalDateTime endAt) {
        this.todoId = todoId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.calculatePeriod();
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
        this.calculatePeriod();
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
        this.calculatePeriod();
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    private void calculatePeriod() {
        if (ObjectUtils.isEmpty(this.startAt) || ObjectUtils.isEmpty(this.endAt)) {
            this.period = 0L;
            return;
        }

        this.period = Duration.between(this.startAt, this.endAt).toMillis();
    }
}
