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
    private BookServiceImpl bookServiceImpl;

    @Override
    public Comment getById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new BaseException("Комментария с указанным id не существует"));
    }

    @Override
    public Comment save(String text, Integer id) {
        Book book = bookServiceImpl.getById(id);
        Comment comment = Comment.builder()
                .text(text)
                .book(book)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment, String text) {
        comment.setText(text);
        return commentRepository.save(comment);
    }
}
