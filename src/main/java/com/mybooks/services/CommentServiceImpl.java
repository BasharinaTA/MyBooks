package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;
import com.mybooks.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Override
    public Comment getById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new BaseException("Комментарий не найден"));
    }

    @Override
    public Comment getByIdAndBook(Integer id, Book book) {
        return commentRepository.findByIdAndBook(id, book).orElseThrow(() ->
                new BaseException("Комментарий для указанной книги не найден"));
    }

    @Override
    public Comment save(Comment comment, Book book) {
        if (book.getComment() != null) {
            throw new BaseException("Комментарий для указанной книги уже есть");
        }
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Integer id, Book book, String text) {
        Comment comment = getByIdAndBook(id, book);
        comment.setText(text);
        return commentRepository.save(comment);
    }
}
