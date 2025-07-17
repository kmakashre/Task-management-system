package com.example.Task.Management.System.controller;

import com.example.Task.Management.System.Jwt.JwtHelper;
import com.example.Task.Management.System.entity.Task;
import com.example.Task.Management.System.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test for TaskController.
 * Uses @WebMvcTest to test only the web layer (TaskController) with MockMvc.
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the TaskService to isolate controller logic
    @MockBean
    private TaskService taskService;

    // Mock the JwtHelper if it's injected in the controller
    @MockBean
    private JwtHelper jwtHelper;

    // Used for converting objects to JSON
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test for GET /api/tasks endpoint
     * Verifies that the controller returns the correct task list with status 200
     */
    @Test
    void testGetTasks() throws Exception {
        // Create a sample Task object
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Test Task")
                .description("Test Description")
                .dueDate(LocalDateTime.now().plusDays(2))
                .status(Task.Status.valueOf("PENDING"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Create a pageable request
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        // Mock the service response
        Mockito.when(taskService.getAllTasks(pageable, null, null))
                .thenReturn(new PageImpl<>(Collections.singletonList(task)));

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));
    }
}
