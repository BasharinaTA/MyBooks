package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;

public interface CommentService {

    Comment getById(Integer id);

    Comment getByIdAndBook(Integer id, Book book);

    Comment save(Comment comment, Book book);

    Comment update(Integer id, Book book, String text);
}
