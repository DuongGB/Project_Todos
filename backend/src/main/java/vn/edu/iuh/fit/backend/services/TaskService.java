/*
 * @ {#} TaskService.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.dtos.TaskDto;

import java.util.List;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Long id);

    TaskDto createTask(TaskDto taskDto);

    TaskDto updateTask(Long id, TaskDto taskDto);

    void deleteTask(Long id);

    TaskDto moveTaskStatus(Long id, String status);
}

