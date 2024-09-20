package com.mybooks.services;


import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.model.entities.User;
import com.mybooks.repositories.ProfileRepository;
import com.mybooks.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;

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
    public List<User> getAllByOrderBySurnameAndName() {
        return userRepository.findAllByOrderByUsername()
                .stream()
                .sorted(Comparator.comparing(u -> u.getProfile().getName()))
                .sorted(Comparator.comparing(u -> u.getProfile().getSurname()))
                .toList();
    }

    @Override
    public List<User> getAllByUsernameOrderBySurnameAndName(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCaseOrderByUsername(username)
                .stream()
                .sorted(Comparator.comparing(u -> u.getProfile().getName()))
                .sorted(Comparator.comparing(u -> u.getProfile().getSurname()))
                .toList();
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

    @Override
    public User blockUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new BaseException("Пользователя с указанным id не существует"));
        user.setStatus(Status.INACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User unblockUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new BaseException("Пользователя с указанным id не существует"));
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }
}
