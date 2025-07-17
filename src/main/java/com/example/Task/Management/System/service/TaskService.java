package com.example.Task.Management.System.service;

import com.example.Task.Management.System.Repositry.TaskRepository;
import com.example.Task.Management.System.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Create a new task and set timestamps
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    // Get all tasks with optional filtering by status and due date, with pagination
    public Page<Task> getAllTasks(Pageable pageable, Optional<Task.Status> status, Optional<LocalDateTime> dueDate) {
        if (status.isPresent() && dueDate.isPresent()) {
            return taskRepository.findByStatusAndDueDateBefore(status.get(), dueDate.get(), pageable);
        } else if (status.isPresent()) {
            return taskRepository.findByStatus(status.get(), pageable);
        } else if (dueDate.isPresent()) {
            return taskRepository.findByDueDateBefore(dueDate.get(), pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
    }

    // Get a single task by its ID
    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    // Update task details if the task with given ID exists
    public Optional<Task> updateTask(UUID id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setDueDate(updatedTask.getDueDate());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        });
    }

    // Delete a task by ID and return true if successful
    public boolean deleteTask(UUID id) {
        taskRepository.deleteById(id);
        return true; // updated to true since deletion should be considered successful
    }

    // Another method to get tasks by status with pagination (not used in controller?)
    public Page<Task> getTasks(Optional<Task.Status> status, Optional<LocalDateTime> dueDate, Pageable pageable) {
        return taskRepository.findByStatus(status.orElse(null), pageable);
    }
}
