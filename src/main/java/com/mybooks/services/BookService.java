package com.mybooks.services;

import com.mybooks.model.entities.Book;

public interface BookService {

    Book getById(Integer id);

    Book save(Book book);

    Book update(Integer id, Book book, Integer genreId);

    void delete(Integer id);
}
