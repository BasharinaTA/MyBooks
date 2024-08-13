package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;
import com.mybooks.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private BookService bookService;

    @Override
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Комментария с указанным  id не существует"));
    }

    @Override
    public Comment save(String text) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCreated(new Date());
        comment.setRate(true);
        return commentRepository.save(comment);
    }
}
