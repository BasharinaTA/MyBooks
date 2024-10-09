package com.mybooks.services;

import com.mybooks.model.entities.Profile;

public interface ProfileService {

    Profile getById(Integer id);

    Profile getByUser();

    Profile save(String username, String password, String name, String surname);

    Profile update(Profile profile, String name, String surname);
}
