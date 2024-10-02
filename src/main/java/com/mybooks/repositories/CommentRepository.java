package com.mybooks.repositories;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findByIdAndBook(Integer id, Book book);
}
