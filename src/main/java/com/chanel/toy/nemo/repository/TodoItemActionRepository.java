package com.chanel.toy.nemo.repository;

import com.chanel.toy.nemo.model.TodoItemAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemActionRepository extends JpaRepository<TodoItemAction, Long>, TodoItemActionCustom {
}
