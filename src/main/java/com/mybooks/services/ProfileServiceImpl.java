package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Profile;
import com.mybooks.model.entities.User;
import com.mybooks.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;
    private UserService userService;

    @Override
    public Profile get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Профиль не существует"));
    }

    @Override
    public Profile getByUser(User user) {
        return profileRepository.findByUser(user).orElseThrow(() ->
                new RuntimeException("Профиль не существует"));
    }

    @Override
    public Profile getProfileByPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userService.getByUsername(username);
        return getByUser(user);
    }

    @Override
    public List<Book> getBooks(Profile profile, Integer year) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() != null
                        && b.getDateStart().getYear() + 1900 == year)
                .sorted(Comparator.comparing(Book::getDateStart))
                .toList();
    }

    @Override
    public List<Book> getPlannedBooks(Profile profile) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() == null
                        && b.getDateFinish() == null)
                .sorted(Comparator.comparing(Book::getCreated, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public List<Book> getPlannedBooksByName(Profile profile, String name) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() == null
                        && b.getDateFinish() == null
                        && b.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> getNotReadBooks(Profile profile) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() != null
                        && b.getDateFinish() == null)
                .sorted(Comparator.comparing(Book::getDateStart, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public List<Book> getWrongDatesBooks(Profile profile) {
        return profile.getBooks()
                .stream()
                .filter(b -> (b.getDateStart() == null && b.getDateFinish() != null)
                        || (b.getDateStart() != null && b.getDateStart().after(new Date()))
                        || (b.getDateFinish() != null && b.getDateFinish().after(new Date()))
                        || (b.getDateStart() != null && b.getDateFinish() != null && b.getDateStart().after(b.getDateFinish())))
                .toList();
    }

    @Override
    public List<Book> getBooksLastRead(Profile profile) {
        Optional<Date> maxDate = getVerifiedBooks(profile)
                .stream()
                .map(Book::getDateFinish)
                .max(Date::compareTo);

        if (maxDate.isEmpty()) {
            return Collections.emptyList();
        }

        return getVerifiedBooks(profile)
                .stream()
                .filter(b -> b.getDateFinish().equals(maxDate.get()))
                .sorted(Comparator.comparing(Book::getDateStart))
                .toList();
    }

    @Override
    public List<Book> getBooksThisYearRead(Profile profile) {
        return getVerifiedBooks(profile)
                .stream()
                .filter(b -> b.getDateFinish().getYear() == (new Date()).getYear())
                .sorted(Comparator.comparing(Book::getDateStart))
                .toList();
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile, String name, String surname) {
        profile.setName(name);
        profile.setSurname(surname);
        return profileRepository.save(profile);
    }

    private static List<Book> getVerifiedBooks(Profile profile) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() != null
                        && b.getDateFinish() != null
                        && !b.getDateStart().after(new Date())
                        && !b.getDateFinish().after(new Date())
                        && !b.getDateStart().after(b.getDateFinish()))
                .toList();
    }
}
