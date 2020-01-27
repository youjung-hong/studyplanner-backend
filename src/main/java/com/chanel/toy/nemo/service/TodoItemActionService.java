package com.chanel.toy.nemo.service;

import com.chanel.toy.nemo.model.TodoItem;
import com.chanel.toy.nemo.model.TodoItemAction;
import com.chanel.toy.nemo.repository.TodoItemActionRepository;
import com.chanel.toy.nemo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemActionService {
    @Autowired
    TodoItemActionRepository todoItemActionRepository;

    public void save(TodoItemAction todoItemAction) {
        todoItemActionRepository.save(todoItemAction);
    }

    // @todo 삭제된 것 미노출
    public Optional<TodoItemAction> findById(Long id) {
        return todoItemActionRepository.findById(id);
    }

    // @todo 삭제된 것 미노출,
    // TODO: 2020/01/27  todoId와 Date 사용하여 검색하도록 변경 필요
    public List<TodoItemAction> findAllByTodoIdOrDate(Long todoId, LocalDate date) {
        return todoItemActionRepository.findAllByTodoIdOrDate(todoId, date);
    }

    public void delete(TodoItemAction todoItemAction) {
        todoItemAction.setDeletedAt(LocalDateTime.now());
        todoItemActionRepository.save(todoItemAction);
    }
}
