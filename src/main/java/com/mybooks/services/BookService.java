package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    public Book getById(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Книги с указанным  id не существует"));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Integer id, Book book) {
        Book bookToSave = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Книги с указанным  id не существует"));
        bookToSave.setName(book.getName());
        bookToSave.setAuthor(book.getAuthor());
        bookToSave.setType(book.getType());
        bookToSave.setDateStart(book.getDateStart());
        bookToSave.setDateFinish(book.getDateFinish());
        return bookRepository.save(bookToSave);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}
