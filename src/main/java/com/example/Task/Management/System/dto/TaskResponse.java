package com.example.Task.Management.System.dto;

import ch.qos.logback.core.status.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder // Enables builder pattern for easy object creation
public class TaskResponse {

    private UUID id; // Unique identifier for the task
    private String title; // Title of the task
    private String description; // Optional description of the task
    private Status status; // Enum: PENDING, IN_PROGRESS, COMPLETED
    private LocalDateTime dueDate; // Due date of the task
    private LocalDateTime createdAt; // Timestamp when task was created
    private LocalDateTime updatedAt; // Timestamp when task was last updated
}
