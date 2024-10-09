package com.mybooks.services;

import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.Book;

import java.util.List;

public interface BookService {

    Book getById(Integer id);

    List<Book> getReadingBooksByYear(Integer year);

    List<Book> getPlannedBooks();

    List<Book> getPlannedBooksByName(String name);

    List<Book> getNotReadBooks();

    List<Book> getBooksWithIncorrectDates();

    List<Book> getBooksLastRead();

    List<Book> getBooksThisYearRead();

    Book save(Book book, Integer genreId);

    Book update(BookUpdateDto bookData);

    void delete(Integer id);

    void updateDateStart(Integer id);

    void updateDateFinish(Integer id);
}
