package com.github.vitaliimak.TaskTracker.repository;

import com.github.vitaliimak.TaskTracker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepisitory extends CrudRepository<User, Long> {
}
