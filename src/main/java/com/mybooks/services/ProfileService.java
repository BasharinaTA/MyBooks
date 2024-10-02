package com.mybooks.services;

import com.mybooks.model.entities.Profile;

import java.security.Principal;

public interface ProfileService {

    Profile getById(Integer id);

    Profile getByPrincipal(Principal principal);

    Profile save(String username, String password, String name, String surname);

    Profile update(Profile profile, String name, String surname);
}
