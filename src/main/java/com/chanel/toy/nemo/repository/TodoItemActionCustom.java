package com.chanel.toy.nemo.repository;

import com.chanel.toy.nemo.model.TodoItemAction;

import java.time.LocalDate;
import java.util.List;

public interface TodoItemActionCustom {
    List<TodoItemAction> findActionsByTodoIdAndDate(Long todoId, LocalDate date);
}