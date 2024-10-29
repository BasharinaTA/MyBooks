package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Genre;
import com.mybooks.model.entities.Profile;
import com.mybooks.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreService genreService;
    private ProfileService profileService;

    @Override
    public Book getById(Integer id) {
        Profile profile = profileService.getByUser();
        return bookRepository.findByIdAndProfile(id, profile).orElseThrow(() ->
                new BaseException("Книга не найдена"));
    }

    @Override
    public List<Book> getReadingBooksByYear(Integer year) {
        Profile profile = profileService.getByUser();
        return bookRepository.findReadingBooksByYear(profile, year);
    }

    @Override
    public List<Book> getPlannedBooks() {
        Profile profile = profileService.getByUser();
        return bookRepository.findPlannedBooks(profile);
    }

    @Override
    public List<Book> getPlannedBooksByName(String name) {
        Profile profile = profileService.getByUser();
        return bookRepository.findPlannedBooks(profile)
                .stream()
                .filter(b -> b.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> getNotReadBooks() {
        Profile profile = profileService.getByUser();
        return bookRepository.findNotReadBooks(profile);
    }

    @Override
    public List<Book> getBooksWithIncorrectDates() {
        Profile profile = profileService.getByUser();
        return bookRepository.findBooksWithIncorrectDates(profile);
    }

    @Override
    public List<Book> getBooksLastRead() {
        Profile profile = profileService.getByUser();
        Optional<LocalDate> maxDate = bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .map(Book::getDateFinish)
                .max(LocalDate::compareTo);

        if (maxDate.isEmpty()) {
            return Collections.emptyList();
        }

        return bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .filter(b -> b.getDateFinish().equals(maxDate.get()))
                .toList();
    }

    @Override
    public List<Book> getBooksThisYearRead() {
        Profile profile = profileService.getByUser();
        return bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .filter(b -> b.getDateFinish().getYear() == (LocalDate.now()).getYear())
                .toList();
    }

    @Override
    public Book save(Book book, Integer genreId) {
        Profile profile = profileService.getByUser();
        Genre genre = genreService.getById(genreId);
        book.setGenre(genre);
        book.setProfile(profile);
        return bookRepository.save(book);
    }

    @Override
    public Book update(BookUpdateDto bookData) {
        Book book = getById(bookData.getId());
        Genre genre = genreService.getById(bookData.getGenreId());
        book.setName(bookData.getName());
        book.setAuthor(bookData.getAuthor());
        book.setType(bookData.getType());
        book.setGenre(genre);
        book.setDateStart(bookData.getDateStart());
        book.setDateFinish(bookData.getDateFinish());
        return bookRepository.save(book);
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public void updateDateStart(Integer id) {
        Book book = getById(id);
        book.setDateStart(LocalDate.now());
        book.setDateFinish(null);
        bookRepository.save(book);
    }

    @Override
    public void updateDateFinish(Integer id) {
        Book book = getById(id);
        book.setDateFinish(LocalDate.now());
        bookRepository.save(book);
    }
}
