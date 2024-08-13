package com.mybooks.repositories;

import com.mybooks.model.entities.Profile;
import com.mybooks.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByUser(User user);
}
