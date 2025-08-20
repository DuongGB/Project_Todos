/*
 * @ {#} TaskController.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.dtos.request.TaskRequest;
import vn.edu.iuh.fit.backend.dtos.response.BaseResponse;
import vn.edu.iuh.fit.backend.dtos.response.TaskResponse;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.TaskService;

import java.util.List;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all origins for CORS
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<TaskResponse>>> getAll() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.ok(
                    new BaseResponse<>("success", "No tasks found", tasks)
            );
        }
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Tasks retrieved successfully", tasks)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<TaskResponse>> getById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>("error", "Task not found", null)
            );
        }
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task retrieved successfully", task)
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<TaskResponse>> create(@Validated @RequestBody TaskRequest taskRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            return ResponseEntity.status(401).body(
                    new BaseResponse<>("error", "User not found", null)
            );
        }
        TaskResponse saved = taskService.createTask(taskRequest, user.getId());
        if (saved == null) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>("error", "Failed to create task", null)
            );
        }
        return ResponseEntity.status(201).body(
                new BaseResponse<>("success", "Task created successfully", saved)
        );
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<TaskResponse>> update(@PathVariable Long id, @Validated @RequestBody TaskRequest taskRequest) {
        TaskResponse updated = taskService.updateTask(id, taskRequest);
        if (updated == null) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>("error", "Task not found or update failed", null)
            );
        }
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task updated successfully", updated)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task deleted successfully", null)
        );
    }

}

