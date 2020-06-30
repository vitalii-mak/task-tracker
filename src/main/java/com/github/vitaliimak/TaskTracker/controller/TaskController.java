package com.github.vitaliimak.TaskTracker.controller;

import com.github.vitaliimak.TaskTracker.model.Task;
import com.github.vitaliimak.TaskTracker.model.enumeration.TaskStatus;
import com.github.vitaliimak.TaskTracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/tasks"))
                .body(taskService.saveTask(task));
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "status", required = false, defaultValue = "") TaskStatus status,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(taskService.getAllTasks(pageNumber, pageSize, sort, status));
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<Page<Task>> getAllTasksByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "status", required = false, defaultValue = "") TaskStatus status,
            Pageable pageable) {
        return ResponseEntity.ok().body(taskService.getAllTasksByUserId(userId, status, pageable));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) {
        return taskService.updateTask(task)
                .map(existedTask -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/status")
    public ResponseEntity<Task> changeTaskStatus(@RequestParam Long taskId,
                                                 @RequestParam("status") TaskStatus status) {
        return taskService.changeTaskStatus(taskId, status)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user")
    public ResponseEntity<Task> changeTaskUser(@RequestParam Long taskId,
                                               @RequestParam("userId") Long userId) {
        return taskService.changeTaskUser(taskId, userId)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }
}
