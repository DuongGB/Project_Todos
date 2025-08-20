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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.dtos.response.ChecklistDto;
import vn.edu.iuh.fit.backend.dtos.response.TaskDto;
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

    private TaskDto toDto(Task task) {
        List<ChecklistDto> checklistDtos = task.getChecklist().stream()
                .map(c -> ChecklistDto.builder()
                        .content(c.getContent())
                        .checked(c.isChecked())
                        .build())
                .collect(Collectors.toList());
        return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .checklist(checklistDtos)
                .build();
    }

    private Task toEntity(TaskDto taskDto) {
        Task t = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .deadline(taskDto.getDeadline())
                .build();
        if (taskDto.getChecklist() != null) {
            taskDto.getChecklist().forEach(c -> {
                ChecklistItem item = ChecklistItem.builder()
                        .content(c.getContent())
                        .checked(c.isChecked())
                        .build();
                t.addChecklistItem(item);
            });
        }
        return t;
    }

    @Override
    @Cacheable(value = "tasks") // đánh dấu phương thức này để kết quả sẽ được lưu vào cache
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id).map(this::toDto).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true) // đánh dấu phương thức này để xóa cache khi có thay đổi
    public TaskDto createTask(TaskDto taskDto) {
        // Lấy thông tin người dùng hiện tại
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Chuyển đổi DTO sang Entity và gắn User
        Task entity = toEntity(taskDto);
        entity.setUser(user);

        Task savedTask = taskRepository.save(entity);
        return toDto(savedTask);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        // Cập nhật thông tin của task
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setDeadline(taskDto.getDeadline());
        // Cập nhật checklist
        if (taskDto.getChecklist() != null) {
            task.getChecklist().clear(); // Xóa checklist cũ
            taskDto.getChecklist().forEach(c -> {
                ChecklistItem item = ChecklistItem.builder()
                        .content(c.getContent())
                        .checked(c.isChecked())
                        .build();
                task.addChecklistItem(item);
            });
        } else {
            task.getChecklist().clear(); // Xóa checklist nếu không có checklist mới
            task.setChecklist(null);
        }
        Task updatedTask = taskRepository.save(task);
        return toDto(updatedTask);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true) // đánh dấu phương thức này để xóa cache khi có thay đổi
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
}

