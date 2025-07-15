package com.example.Task.Management.System.dto;

import ch.qos.logback.core.status.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
    private LocalDateTime dueDate;
}
