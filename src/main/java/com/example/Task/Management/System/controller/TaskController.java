package com.example.Task.Management.System.controller;

import com.example.Task.Management.System.entity.Task;
import com.example.Task.Management.System.entity.Task.Status;
import com.example.Task.Management.System.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks") // Base URL for task APIs
@AllArgsConstructor // Lombok annotation to generate constructor for final fields
public class TaskController {

    private final TaskService taskService;

    // Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Get paginated list of tasks with optional filtering by status and dueDate
    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(
            @RequestParam(defaultValue = "0") int page,        // Default page number
            @RequestParam(defaultValue = "10") int size,       // Default page size
            @RequestParam Optional<Status> status,             // Optional task status filter
            @RequestParam Optional<LocalDateTime> dueDate      // Optional due date filter
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.getAllTasks(pageRequest, status, dueDate));
    }

    // Get a specific task by its UUID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Update an existing task by its UUID
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Task>> updateTask(@PathVariable UUID id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask));
    }

    // Delete a task by its UUID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
