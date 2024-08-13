package com.mybooks.services;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;

import javax.persistence.criteria.CriteriaBuilder;

public interface CommentService {

    Comment getCommentById(Integer id);

    Comment save(String text);
}
