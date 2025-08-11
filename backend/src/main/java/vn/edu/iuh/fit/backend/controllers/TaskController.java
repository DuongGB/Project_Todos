/*
 * @ {#} TaskController.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.dtos.response.BaseResponse;
import vn.edu.iuh.fit.backend.dtos.response.TaskDto;
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

    @GetMapping
    public ResponseEntity<BaseResponse<List<TaskDto>>> getAll() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Tasks retrieved successfully", tasks)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TaskDto>> getById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task retrieved successfully", task)
        );
    }

    @PostMapping
    public ResponseEntity<BaseResponse<TaskDto>> create(@Validated @RequestBody TaskDto dto) {
        TaskDto saved = taskService.createTask(dto);
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task created successfully", saved)
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<BaseResponse<TaskDto>> update(@PathVariable Long id, @Validated @RequestBody TaskDto dto) {
        TaskDto updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task updated successfully", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(
                new BaseResponse<>("success", "Task deleted successfully", null)
        );
    }

}

