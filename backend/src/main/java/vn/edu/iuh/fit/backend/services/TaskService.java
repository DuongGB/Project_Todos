/*
 * @ {#} TaskService.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.dtos.request.TaskRequest;
import vn.edu.iuh.fit.backend.dtos.response.TaskResponse;

import java.util.List;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
public interface TaskService {
    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse createTask(TaskRequest taskRequest, Long userId);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    void deleteTask(Long id);
}

