package com.example.Task.Management.System.Repositry;

import com.example.Task.Management.System.entity.Task.Status;
import com.example.Task.Management.System.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByStatus(Status status, Pageable pageable);
    Page<Task> findByDueDateBefore(LocalDateTime dueDate, Pageable pageable);
    Page<Task> findByStatusAndDueDateBefore(Status status, LocalDateTime dueDate, Pageable pageable);
}
