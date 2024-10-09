package com.mybooks.services;

import com.mybooks.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(Integer id);

    User getByUsername(String name);

    Optional<User> getByUser(String name);

    List<User> getAllByOrderByName();

    List<User> getAllByUsernameOrderByName(String username);

    User save(User user);

    User update(Integer id, String role, String status);

    void delete(Integer id);

    void block(Integer id);

    void unblock(Integer id);
}
