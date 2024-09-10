package com.mybooks.services;

import com.mybooks.model.entities.Comment;

public interface CommentService {

    Comment getById(Integer id);

    Comment save(String text, Integer id);

    Comment update(Comment comment, String text);
}
