package com.chanel.toy.nemo.repository;

import com.chanel.toy.nemo.model.TodoItemAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoItemActionRepository extends JpaRepository<TodoItemAction, Long> {
    public List<TodoItemAction> findAllByTodoIdOrDate(Long todoId, LocalDate date);
}
