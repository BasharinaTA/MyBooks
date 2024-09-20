package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Profile;
import com.mybooks.model.entities.User;

import java.security.Principal;
import java.util.List;

public interface ProfileService {

    Profile getById(Integer id);

    Profile getByUser(User user);

    Profile getProfileByPrincipal(Principal principal);

    List<Book> getBooks(Profile profile, Integer year);

    List<Book> getPlannedBooks(Profile profile);

    List<Book> getPlannedBooksByName(Profile profile, String name);

    List<Book> getNotReadBooks(Profile profile);

    List<Book> getWrongDatesBooks(Profile profile);

    List<Book> getBooksLastRead(Profile profile);

    List<Book> getBooksThisYearRead(Profile profile);

    Profile save(String username, String password, String name, String surname);

    Profile update(Profile profile, String name, String surname);
}
