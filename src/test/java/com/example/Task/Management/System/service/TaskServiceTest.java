package com.example.Task.Management.System.service;

import com.example.Task.Management.System.Repositry.TaskRepository;
import com.example.Task.Management.System.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    // Set up a sample task object before each test
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = Task.builder()
                .id(UUID.randomUUID())
                .title("Test Task")
                .description("Test Description")
                .status(Task.Status.PENDING)
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Test case: Create a task and verify it's returned correctly
    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task created = taskService.createTask(task);
        assertNotNull(created);
        assertEquals("Test Task", created.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    // Test case: Fetch tasks by status filter
    @Test
    public void testGetAllTasksWithStatusFilter() {
        Task.Status status = Task.Status.PENDING;
        Pageable pageable = PageRequest.of(0, 10);

        List<Task> taskList = List.of(task);
        Page<Task> taskPage = new PageImpl<>(taskList);

        when(taskRepository.findByStatus(status, pageable)).thenReturn(taskPage);

        Page<Task> result = taskService.getTasks(Optional.of(status), Optional.empty(), pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(task, result.getContent().get(0));
    }

    // Test case: Get task by ID and verify it matches the expected data
    @Test
    public void testGetTaskById() {
        UUID taskId = task.getId();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(taskId);
        assertNotNull(result);
        assertEquals(task.getTitle(), result.get().getTitle());
    }

    // Test case: Update a task and verify changes are saved
    @Test
    public void testUpdateTask() {
        UUID taskId = task.getId();
        Task updatedTask = Task.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status(Task.Status.COMPLETED)
                .dueDate(LocalDateTime.now().plusDays(2))
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Optional<Task> result = taskService.updateTask(taskId, updatedTask);
        assertEquals("Updated Task", result.get().getTitle());
        assertEquals(updatedTask.getTitle(), result.get().getTitle());
    }

    // Test case: Delete a task and ensure repository's delete method is called
    @Test
    public void testDeleteTask() {
        UUID taskId = task.getId();
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        assertDoesNotThrow(() -> taskService.deleteTask(taskId));
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
