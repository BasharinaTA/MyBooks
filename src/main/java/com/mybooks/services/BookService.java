package com.mybooks.services;

import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Profile;

import java.util.List;

public interface BookService {

    Book getByIdAndProfile(Integer id, Profile profile);

    List<Book> getBooksBeingReadFilterByYear(Profile profile, Integer year);

    List<Book> getPlannedBooksToRead(Profile profile);

    List<Book> getPlannedBooksToReadByName(Profile profile, String name);

    List<Book> getBooksNotRead(Profile profile);

    List<Book> getBooksWithIncorrectDates(Profile profile);

    List<Book> getBooksLastRead(Profile profile);

    List<Book> getBooksThisYearRead(Profile profile);

    Book save(Book book, Integer genreId, Profile profile);

    Book update(BookUpdateDto bookData, Profile profile);

    void delete(Integer id, Profile profile);

    void updateDateStart(Integer id, Profile profile);

    void updateDateFinish(Integer id, Profile profile);
}
