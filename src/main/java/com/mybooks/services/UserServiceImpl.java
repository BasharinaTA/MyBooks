package com.mybooks.services;


import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.model.entities.User;
import com.mybooks.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new BaseException("Пользователь не найден"));
    }

    @Override
    public User getByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() ->
                new BaseException("Пользователь с указанным именем не найден"));
    }

    @Override
    public Optional<User> getByUser(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<User> getAllByOrderByName() {
        return userRepository.findAllByOrderByUsername()
                .stream()
                .filter(u -> u.getProfile() != null)
                .sorted(Comparator.comparing(u -> u.getProfile().getName()))
                .sorted(Comparator.comparing(u -> u.getProfile().getSurname()))
                .toList();
    }

    @Override
    public List<User> getAllByUsernameOrderByName(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCaseOrderByUsername(username)
                .stream()
                .filter(u -> u.getProfile() != null)
                .sorted(Comparator.comparing(u -> u.getProfile().getName()))
                .sorted(Comparator.comparing(u -> u.getProfile().getSurname()))
                .toList();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, String role, String status) {
        User user = getById(id);
        user.setRole(Role.valueOf(role));
        user.setStatus(Status.valueOf(status));
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void block(Integer id) {
        User user = getById(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public void unblock(Integer id) {
        User user = getById(id);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}
