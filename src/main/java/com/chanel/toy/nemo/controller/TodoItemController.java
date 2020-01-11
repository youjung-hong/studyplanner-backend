package com.chanel.toy.nemo.controller;

import com.chanel.toy.nemo.model.TodoItem;
import com.chanel.toy.nemo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoItemController {
    @Autowired
    TodoItemService todoItemService;

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> create(@RequestBody TodoItem todoItem) {
        todoItemService.save(todoItem);

        return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> readAll() {
        return new ResponseEntity<>(todoItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> read(@PathVariable Long id) {
        Optional<TodoItem> opt = todoItemService.findById(id);

        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoItem> update(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        if (!todoItemService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItem.setId(id);
        todoItemService.save(todoItem);

        return new ResponseEntity<>(todoItem, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> create(@PathVariable Long id) {
        Optional<TodoItem> opt = todoItemService.findById(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItemService.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
