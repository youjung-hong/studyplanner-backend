package com.chanel.toy.nemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
