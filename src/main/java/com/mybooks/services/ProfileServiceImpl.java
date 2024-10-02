package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.*;
import com.mybooks.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;
    private UserService userService;

    @Override
    public Profile getById(Integer id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new BaseException("Профиль не найден"));
    }

    @Override
    public Profile getByPrincipal(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        return profileRepository.findByUser(user).orElseThrow(() ->
                new BaseException("Профиль для указанного пользователя не найден"));
    }

    @Override
    @Transactional
    public Profile save(String username, String password, String name, String surname) {
        User user = userService.save(User.builder()
                .username(username)
                .hashPassword(new BCryptPasswordEncoder(12).encode(password))
                .created(new Date())
                .role(Role.ROLE_USER)
                .status((Status.ACTIVE))
                .build());
        Profile profile = Profile.builder()
                .name(name)
                .surname(surname)
                .user(user)
                .build();
        return profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile, String name, String surname) {
        profile.setName(name);
        profile.setSurname(surname);
        return profileRepository.save(profile);
    }
}
