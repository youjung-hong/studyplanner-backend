package com.chanel.toy.nemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemActionLog {
    @Id
    @GeneratedValue
    private Long id;
    private Long todoItemId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long period;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
