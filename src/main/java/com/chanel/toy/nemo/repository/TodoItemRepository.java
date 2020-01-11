package com.chanel.toy.nemo.repository;

import com.chanel.toy.nemo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
