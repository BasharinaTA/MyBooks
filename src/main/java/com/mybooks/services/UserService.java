package com.mybooks.services;

import com.mybooks.model.entities.User;

import java.util.Optional;

public interface UserService {

    User getByUsername(String name);

    Optional<User> getByUser(String name);

    User save(User user);
}
