package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Genre;
import com.mybooks.model.entities.Profile;
import com.mybooks.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreService genreService;

    @Override
    public Book getByIdAndProfile(Integer id, Profile profile) {
        return bookRepository.findByIdAndProfile(id, profile).orElseThrow(() ->
                new BaseException("Книга не найдена"));
    }

    @Override
    public List<Book> getBooksBeingReadFilterByYear(Profile profile, Integer year) {
        return bookRepository.findBooksBeingReadFilterByYear(profile, year);
    }

    @Override
    public List<Book> getPlannedBooksToRead(Profile profile) {
        return bookRepository.findPlannedBooksToRead(profile);
    }

    @Override
    public List<Book> getPlannedBooksToReadByName(Profile profile, String name) {
        return bookRepository.findPlannedBooksToRead(profile)
                .stream()
                .filter(b -> b.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> getBooksNotRead(Profile profile) {
        return bookRepository.findBooksNotRead(profile);
    }

    @Override
    public List<Book> getBooksWithIncorrectDates(Profile profile) {
        return bookRepository.findBooksWithIncorrectDates(profile);
    }

    @Override
    public List<Book> getBooksLastRead(Profile profile) {
        Optional<Date> maxDate = bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .map(Book::getDateFinish)
                .max(Date::compareTo);

        if (maxDate.isEmpty()) {
            return Collections.emptyList();
        }

        return bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .filter(b -> b.getDateFinish().equals(maxDate.get()))
                .toList();
    }

    @Override
    public List<Book> getBooksThisYearRead(Profile profile) {
        return bookRepository.findReadBooksWithCorrectDates(profile)
                .stream()
                .filter(b -> b.getDateFinish().getYear() == (new Date()).getYear())
                .toList();
    }

    @Override
    public Book save(Book book, Integer genreId, Profile profile) {
        Genre genre = genreService.getById(genreId);
        book.setGenre(genre);
        book.setProfile(profile);
        return bookRepository.save(book);
    }

    @Override
    public Book update(BookUpdateDto bookData, Profile profile) {
        Book book = getByIdAndProfile(bookData.getId(), profile);
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
    public void delete(Integer id, Profile profile) {
        Book book = getByIdAndProfile(id, profile);
        bookRepository.deleteById(id);
    }

    @Override
    public void updateDateStart(Integer id, Profile profile) {
        Book book = getByIdAndProfile(id, profile);
        book.setDateStart(new Date());
        book.setDateFinish(null);
        bookRepository.save(book);
    }

    @Override
    public void updateDateFinish(Integer id, Profile profile) {
        Book book = getByIdAndProfile(id, profile);
        book.setDateFinish(new Date());
        bookRepository.save(book);
    }
}
