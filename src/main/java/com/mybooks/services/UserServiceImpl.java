package com.mybooks.services;


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
}
