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

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Page<Task> getTasks(int page, int size, Task.Status status, LocalDateTime dueDate) {
        Pageable pageable = PageRequest.of(page, size);

        if (status != null && dueDate != null) {
            return taskRepository.findByStatusAndDueDateBefore(status, dueDate, pageable);
        } else if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        } else if (dueDate != null) {
            return taskRepository.findByDueDateBefore(dueDate, pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

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

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
