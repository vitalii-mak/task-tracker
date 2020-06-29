package com.github.vitaliimak.TaskTracker.repository;

import com.github.vitaliimak.TaskTracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByEmailIgnoreCase(String email);

    Page<User> findAll(Pageable pageable);
}
