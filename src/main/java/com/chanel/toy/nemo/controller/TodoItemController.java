package com.chanel.toy.nemo.controller;

import com.chanel.toy.nemo.model.TodoItem;
import com.chanel.toy.nemo.model.TodoItemForm;
import com.chanel.toy.nemo.service.TodoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value= "/api")
@Tag(name = "todo", description = "할 일 목록")
public class TodoItemController {
    @Autowired
    TodoItemService todoItemService;

    @Operation(summary = "모든 할 일 목록을 조회합니다.", tags = { "todo" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = TodoItem.class)),
                mediaType = "application/json")
        )
    })
    @GetMapping(value="/todos", produces = "application/json")
    public ResponseEntity<List<TodoItem>> readAll() {
        return new ResponseEntity<>(todoItemService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "새 할 일을 추가합니다.", tags = { "todo" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", description = "Created",
            content = @Content(schema = @Schema(implementation = TodoItem.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400", description = "name and color are required")
    })
    @PostMapping(value="/todos", consumes = "application/json")
    public ResponseEntity<TodoItem> create(
        @Parameter(required=true, schema=@Schema(implementation = TodoItemForm.class))
           @Valid @RequestBody TodoItemForm todoItemForm) {
        if (this.isInvalidInput(todoItemForm)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TodoItem todoItem = new TodoItem(todoItemForm.name, todoItemForm.color);
        todoItemService.save(todoItem);

        return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
    }

    @Operation(summary = "ID로 todo 아이템을 조회합니다.", tags = { "todo" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TodoItem.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "204", description = "Todo not found")
    })
    @GetMapping(value="/todos/{todoId}", produces = "application/json")
    public ResponseEntity<TodoItem> read(
        @Parameter(description="찾고자 하는 todo 아이템의 ID", required=true) @PathVariable Long todoId
    ) {

        Optional<TodoItem> opt = todoItemService.findById(todoId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(opt.get(), HttpStatus.OK);
    }

    @Operation(summary = "저장되어 있는 todo 아이템을 폼 데이터로 업데이트합니다.", tags = { "todo" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TodoItem.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "204", description = "Todo not found"),
        @ApiResponse(responseCode = "400", description = "name and color are required")
    })
    @PutMapping(value="/todos/{todoId}", consumes = "application/json")
    public ResponseEntity<TodoItem> update(
        @Parameter(description="업데이트할 ID", required=true) @PathVariable Long todoId,
        @Parameter(required=true, schema=@Schema(implementation = TodoItemForm.class))
            @Valid @RequestBody TodoItemForm todoItemForm
    ) {
        if (this.isInvalidInput(todoItemForm)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<TodoItem> opt = todoItemService.findById(todoId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        TodoItem todoItem = opt.get();
        todoItem.setName(todoItemForm.name);
        todoItem.setColor(todoItemForm.color);
        todoItemService.save(todoItem);

        return new ResponseEntity<>(todoItem, HttpStatus.OK);
    }

    @Operation(summary = "todo 아이템을 지웁니다.", tags = { "todo" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "204", description = "Todo not found"),
    })
    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> delete(
        @Parameter(description="삭제할 todo 아이템의 ID", required=true) @PathVariable Long todoId
    ) {
        Optional<TodoItem> opt = todoItemService.findById(todoId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItemService.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isInvalidInput(TodoItemForm todoItemForm) {
        return todoItemForm.name.isEmpty() || todoItemForm.color.isEmpty();
    }
}
