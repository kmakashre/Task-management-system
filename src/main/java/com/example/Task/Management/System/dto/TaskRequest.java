package com.example.Task.Management.System.dto;

import ch.qos.logback.core.status.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
    private LocalDateTime dueDate;
}
