package com.mybooks.services;


import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.model.entities.User;
import com.mybooks.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new BaseException("Пользователя с указанным id не существует"));
    }

    @Override
    public User getByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() ->
                new UsernameNotFoundException("Пользователя с указанным именем не существует"));
    }

    @Override
    public Optional<User> getByUser(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user, Role role, Status status) {
        user.setRole(role);
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
