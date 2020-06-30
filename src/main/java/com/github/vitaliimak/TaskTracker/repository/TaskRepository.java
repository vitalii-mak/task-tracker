package com.github.vitaliimak.TaskTracker.repository;

import com.github.vitaliimak.TaskTracker.model.Task;
import com.github.vitaliimak.TaskTracker.model.enumeration.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Page<Task> findAllTasksByUserId(Pageable pageable, Long userId);

    @Query("select t from Task t where (t.status = :status) and (t.user.id = :userId)")
    Page<Task> findAllTasksByUserId(
            Pageable pageable, @Param("userId") Long userId, @Param("status") TaskStatus status);

    Page<Task> findAll(Pageable pageable);

    @Query("select t from Task t where (t.status = :status)")
    Page<Task> findAll(Pageable pageable, @Param("status") TaskStatus status);
}
