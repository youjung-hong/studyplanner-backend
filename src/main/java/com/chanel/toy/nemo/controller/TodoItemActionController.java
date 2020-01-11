package com.chanel.toy.nemo.controller;

import com.chanel.toy.nemo.model.TodoItemAction;
import com.chanel.toy.nemo.service.TodoItemActionService;
import com.chanel.toy.nemo.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoItemActionController {
    @Autowired
    TodoItemService todoItemService;
    @Autowired
    TodoItemActionService todoItemActionService;

    @PostMapping("/todos/{todoId}/actions")
    public ResponseEntity<TodoItemAction> create(@PathVariable Long todoId, @RequestBody TodoItemAction todoItemAction) {
        if (!todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // @todo 어떻게 반환하는 게 맞는 지 찾아보기
        }

        todoItemAction.setTodoId(todoId);
        todoItemActionService.save(todoItemAction);

        return new ResponseEntity<>(todoItemAction, HttpStatus.CREATED);
    }

    @GetMapping("/todos/{todoId}/actions")
    public ResponseEntity<List<TodoItemAction>> readAll(@PathVariable Long todoId) {
        if (!todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // @todo 어떻게 반환하는 게 맞는 지 찾아보기
        }

        return new ResponseEntity<>(todoItemActionService.findAllByTodoId(todoId), HttpStatus.OK);
    }

    @GetMapping("/todos/{todoId}/actions/{id}")
    public ResponseEntity<TodoItemAction> read(@PathVariable Long todoId, @PathVariable Long id) {
        if (!todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // @todo 어떻게 반환하는 게 맞는 지 찾아보기
        }

        Optional<TodoItemAction> opt = todoItemActionService.findById(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(opt.get(), HttpStatus.OK);
    }

    @PutMapping("/todos/{todoId}/actions/{id}")
    public ResponseEntity<TodoItemAction> update(@PathVariable Long todoId, @PathVariable Long id, @RequestBody TodoItemAction todoItemAction) {
        if (!todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // @todo 어떻게 반환하는 게 맞는 지 찾아보기
        }

        Optional<TodoItemAction> opt = todoItemActionService.findById(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItemAction.setId(id);
        todoItemAction.setTodoId(todoId);
        todoItemActionService.save(todoItemAction);
        return new ResponseEntity<>(todoItemAction, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{todoId}/actions/{id}")
    public ResponseEntity<Void> create(@PathVariable Long todoId, @PathVariable Long id) {
        if (!todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // @todo 어떻게 반환하는 게 맞는 지 찾아보기
        }

        Optional<TodoItemAction> opt = todoItemActionService.findById(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItemActionService.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
