/*
 * @ {#} TaskServiceImpl.java   1.0     8/8/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */

package vn.edu.iuh.fit.backend.services.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.dtos.request.TaskRequest;
import vn.edu.iuh.fit.backend.dtos.response.ChecklistResponse;
import vn.edu.iuh.fit.backend.dtos.response.TaskResponse;
import vn.edu.iuh.fit.backend.models.ChecklistItem;
import vn.edu.iuh.fit.backend.models.Task;
import vn.edu.iuh.fit.backend.models.User;
import vn.edu.iuh.fit.backend.repositories.TaskRepository;
import vn.edu.iuh.fit.backend.repositories.UserRepository;
import vn.edu.iuh.fit.backend.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @description:
 * @author: Nguyen Tan Thai Duong
 * @date:   8/8/2025
 * @version:    1.0
 */
@Service
@RequiredArgsConstructor
@Transactional // dùng để đảm bảo tính toàn vẹn của giao dịch
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private TaskResponse toTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDeadline(task.getDeadline());
        response.setStatus(task.getStatus() != null ? task.getStatus().name() : null);
        if (task.getChecklist() != null) {
            List<ChecklistResponse> checklistResponses = task.getChecklist().stream()
                    .map(item -> {
                        ChecklistResponse itemResponse = new ChecklistResponse();
                        itemResponse.setId(item.getId());
                        itemResponse.setContent(item.getContent());
                        itemResponse.setChecked(item.isChecked());
                        return itemResponse;
                    })
                    .toList();
            response.setChecklist(checklistResponses);
        }
        return response;
    }


    @Override
    @Cacheable(value = "tasks") // đánh dấu phương thức này để kết quả sẽ được lưu vào cache
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().
                map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskRepository.findById(id).map(this::toTaskResponse)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskResponse createTask(TaskRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        // Tạo mới task
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setStatus(request.getStatus() != null ? request.getStatus() : null);
        task.setUser(user);
        // Tạo checklist items nếu có
        List<ChecklistItem> items = request.getChecklist().stream().map(itemReq -> {
            ChecklistItem item = new ChecklistItem();
            item.setContent(itemReq.getContent());
            item.setChecked(itemReq.isChecked());
            item.setTask(task);
            return item;
        }).collect(Collectors.toList());
        task.setChecklist(items);
        taskRepository.save(task);
        // convert to TaskResponse
        return toTaskResponse(task);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        // Cập nhật thông tin task
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDeadline(taskRequest.getDeadline());
        // Nếu có status thì cập nhật
        if (taskRequest.getStatus() != null) {
            task.setStatus(taskRequest.getStatus());
        }
        // Cập nhật checklist items
        if (taskRequest.getChecklist() != null) {
            task.getChecklist().clear();
            List<ChecklistItem> items = taskRequest.getChecklist().stream().map(itemReq -> {
                ChecklistItem item = new ChecklistItem();
                item.setContent(itemReq.getContent());
                item.setChecked(itemReq.isChecked());
                item.setTask(task);
                return item;
            }).collect(Collectors.toList());
            task.setChecklist(items);
        } else {
            task.getChecklist().clear();
            task.setChecklist(null);
        }
        Task updatedTask = taskRepository.save(task);
        return toTaskResponse(updatedTask);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true) // đánh dấu phương thức này để xóa cache khi có thay đổi
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
}

