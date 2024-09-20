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
                new BaseException("Профиль с указанным id не существует"));
    }

    @Override
    public Profile getByUser(User user) {
        return profileRepository.findByUser(user).orElseThrow(() ->
                new BaseException("Профиль для указанного пользователя не существует"));
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
                .sorted(Comparator.comparing(Book::getDateStart)
                        .thenComparing(Book::getId))
                .toList();
    }

    @Override
    public List<Book> getPlannedBooks(Profile profile) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() == null)
                .sorted(Comparator.comparing(Book::getCreated, Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public List<Book> getPlannedBooksByName(Profile profile, String name) {
        return profile.getBooks()
                .stream()
                .filter(b -> b.getDateStart() == null
                        && b.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Book::getCreated, Comparator.reverseOrder()))
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
                .sorted(Comparator.comparing(Book::getDateStart, Comparator.nullsLast(Comparator.reverseOrder())))
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
