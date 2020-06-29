package com.github.vitaliimak.TaskTracker.repository;

import com.github.vitaliimak.TaskTracker.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Page<Task> findAllTasksByUserId(Long userId, Pageable pageable);
}
