package com.mybooks.repositories;

import com.mybooks.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String name);

    List<User> findAllByOrderByUsername();

    List<User> findAllByUsernameContainingIgnoreCaseOrderByUsername(String username);
}
