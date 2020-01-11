package com.chanel.toy.nemo.service;

import com.chanel.toy.nemo.model.TodoItem;
import com.chanel.toy.nemo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {
    @Autowired
    TodoItemRepository todoItemRepository;

    public void save(TodoItem todoItem) {
        todoItemRepository.save(todoItem);
    }

    // @todo 삭제된 것 미노출
    public Optional<TodoItem> findById(Long id) {
        return todoItemRepository.findById(id);
    }

    public List<TodoItem> findAll() { // @todo 삭제된 것 미노출
        return todoItemRepository.findAll();
    }

    public void delete(TodoItem todoItem) {
        todoItem.setDeletedAt(LocalDateTime.now());
        todoItemRepository.save(todoItem);
    }
}
