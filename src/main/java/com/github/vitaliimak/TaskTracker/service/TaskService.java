package com.github.vitaliimak.TaskTracker.service;

import com.github.vitaliimak.TaskTracker.model.Task;
import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.model.enumeration.TaskStatus;
import com.github.vitaliimak.TaskTracker.repository.TaskRepository;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Task saveTask(Task task) {
        User user = userService.getCurrentUser();
        task.setUser(user);
        task.setStatus(TaskStatus.VIEW);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Page<Task> getAllTasksByUserId(Long userId, Pageable pageable) {
        return taskRepository.findAllTasksByUserId(userId, pageable);
    }

    public Optional<Task> updateTask(Task task) {
        return taskRepository.findById(task.getId())
                .map(existedTask -> {
                    existedTask.setTitle(task.getTitle());
                    existedTask.setDescription(task.getDescription());
                    return taskRepository.save(existedTask);
                });
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> changeTaskStatus(Long id, TaskStatus status) {
        return taskRepository.findById(id)
                .map(existedTask -> {
                    existedTask.setStatus(status);
                    return taskRepository.save(existedTask);
                });
    }

    public Optional<Task> changeTaskUser(Long taskId, Long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> taskRepository.findById(taskId)
                        .map(existedTask -> {
                            existedTask.setUser(user);
                            return taskRepository.save(existedTask);
                        }));
    }
}
