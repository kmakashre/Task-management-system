package com.example.Task.Management.System.dto;

import ch.qos.logback.core.status.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
