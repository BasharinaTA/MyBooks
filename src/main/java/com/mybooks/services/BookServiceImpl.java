package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Genre;
import com.mybooks.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreService genreService;

    @Override
    public Book getById(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new BaseException("Книги с указанным id не существует"));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Integer id, Book book, Integer genreId) {
        Book bookToSave = bookRepository.findById(id).orElseThrow(() ->
                new BaseException("Книги с указанным id не существует"));
        Genre genre = genreService.getById(genreId);
        bookToSave.setName(book.getName());
        bookToSave.setAuthor(book.getAuthor());
        bookToSave.setType(book.getType());
        bookToSave.setDateStart(book.getDateStart());
        bookToSave.setDateFinish(book.getDateFinish());
        bookToSave.setGenre(genre);
        return bookRepository.save(bookToSave);
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}
