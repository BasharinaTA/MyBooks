package com.mybooks.services;

import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(Integer id);

    User getByUsername(String name);

    Optional<User> getByUser(String name);

    List<User> getAllByOrderBySurnameAndName();

    List<User> getAllByUsernameOrderBySurnameAndName(String username);

    User save(User user);

    User update(User user, Role role, Status status);

    void delete(Integer id);

    User blockUser(Integer id);

    User unblockUser(Integer id);
}
