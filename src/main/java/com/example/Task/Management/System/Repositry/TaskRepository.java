package com.example.Task.Management.System.Repositry;

import com.example.Task.Management.System.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    // Custom filtering method (null-safe)
    @Query("SELECT t FROM Task t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
    Page<Task> findByFilters(@Param("status") Task.Status status,
                             @Param("dueDate") LocalDateTime dueDate,
                             Pageable pageable);

    Page<Task> findByStatus(Task.Status status, Pageable pageable);

    Page<Task> findByDueDateBefore(LocalDateTime dueDate, Pageable pageable);

    Page<Task> findByStatusAndDueDateBefore(Task.Status status, LocalDateTime dueDate, Pageable pageable);
}
