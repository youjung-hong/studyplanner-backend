package com.chanel.toy.nemo.controller;

import com.chanel.toy.nemo.model.TodoItemAction;
import com.chanel.toy.nemo.model.TodoItemActionForm;
import com.chanel.toy.nemo.service.TodoItemActionService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value= "/api")
@Tag(name = "action", description = "할 일에 대해 작업한 시간과 집중도를 기록")
public class TodoItemActionController {
    @Autowired
    TodoItemService todoItemService;
    @Autowired
    TodoItemActionService todoItemActionService;

    @Operation(
        summary = "모든 action 목록을 조회합니다.",
        description = "날짜 또는 todo 아이템 ID를 기준으로 모두 가져옵니다",
        tags = { "action" }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = TodoItemAction.class)),
                mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400", description = "Invalid query string value")
    })
    @GetMapping(value="/actions", produces = "application/json")
    public ResponseEntity<List<TodoItemAction>> readAllActions(
        @Parameter(description="action 목록을 조회할 todo 아이템 ID") @RequestParam(required = false) Long todoId,
        @Parameter(description="action 목록을 조회할 날") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        if (!ObjectUtils.isEmpty(todoId) && !todoItemService.findById(todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(todoItemActionService.findActionsByTodoIdAndDate(todoId, date), HttpStatus.OK);
    }

    @Operation(
        summary = "할 일 목록에 대한 작업 기록을 생성합니다.",
        tags = { "action" }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", description = "Created",
            content = @Content(schema = @Schema(implementation = TodoItemAction.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400", description = "todoId and startAt are required")
    })
    @PostMapping(value="/actions", consumes = "application/json")
    public ResponseEntity<TodoItemAction> createAction(
        @Parameter(required=true, schema=@Schema(implementation = TodoItemActionForm.class))
            @Valid @RequestBody TodoItemActionForm todoItemActionForm
    ) {
        if (this.isInvalidInput(todoItemActionForm) ||
                !todoItemService.findById(todoItemActionForm.todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TodoItemAction todoItemAction = new TodoItemAction(
                todoItemActionForm.todoId, todoItemActionForm.startAt, todoItemActionForm.endAt
        );
        todoItemActionService.save(todoItemAction);

        return new ResponseEntity<>(todoItemAction, HttpStatus.CREATED);
    }

    @Operation(summary = "action 정보를 id로 조회합니다.", tags = { "action" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TodoItemAction.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "204", description = "action not found")
    })
    @GetMapping(value="/actions/{actionId}", produces = "application/json")
    public ResponseEntity<TodoItemAction> readAction(
        @Parameter(description="찾고자 하는 action ID", required = true) @PathVariable Long actionId
    ) {
        return todoItemActionService.findById(actionId)
                .map(action -> new ResponseEntity<>(action, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Operation(summary = "저장되어 있는 action 정보를 폼 데이터로 업데이트합니다.", tags = { "action" })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = TodoItemAction.class), mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "204", description = "action not found"),
        @ApiResponse(responseCode = "400", description = "todoId and startAt are required")
    })
    @PutMapping(value="/actions/{actionId}", consumes = "application/json")
    public ResponseEntity<TodoItemAction> updateAction(
        @Parameter(description="찾고자 하는 action ID", required = true) @PathVariable Long actionId,
        @Parameter(required=true, schema=@Schema(implementation = TodoItemActionForm.class))
            @Valid @RequestBody TodoItemActionForm todoItemActionForm
    ) {
        if (this.isInvalidInput(todoItemActionForm)
                || !todoItemService.findById(todoItemActionForm.todoId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<TodoItemAction> opt = todoItemActionService.findById(actionId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        TodoItemAction todoItemAction = opt.get();
        todoItemAction.setTodoId(todoItemActionForm.todoId);
        todoItemAction.setStartAt(todoItemActionForm.startAt);
        todoItemAction.setEndAt(todoItemActionForm.endAt);
        todoItemActionService.save(todoItemAction);
        return new ResponseEntity<>(todoItemAction, HttpStatus.OK);
    }

    @Operation(summary = "저장된 작업 기록을 삭제합니다.", tags = { "action" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
    @ApiResponse(responseCode = "204", description = "action not found"),
    })
    @DeleteMapping("/actions/{actionId}")
    public ResponseEntity<Void> deleteAction(
        @Parameter(description="삭제할 action 아이템의 ID", required=true) @PathVariable Long actionId
    ) {
        Optional<TodoItemAction> opt = todoItemActionService.findById(actionId);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        todoItemActionService.delete(opt.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isInvalidInput(TodoItemActionForm todoItemActionForm) {
        return ObjectUtils.isEmpty(todoItemActionForm.todoId) || ObjectUtils.isEmpty(todoItemActionForm.startAt);
    }
}
