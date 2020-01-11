package com.chanel.toy.nemo.repository;

import com.chanel.toy.nemo.model.TodoItemActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemActionLogRepository extends JpaRepository<TodoItemActionLog, Long> {
}
