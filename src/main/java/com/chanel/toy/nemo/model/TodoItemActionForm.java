package com.chanel.toy.nemo.model;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class TodoItemActionForm {
    public Long todoId;
    public LocalDateTime startAt;
    public LocalDateTime endAt;
}
