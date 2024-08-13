package com.mybooks.repositories;

import com.mybooks.model.entities.Comment;
import com.mybooks.model.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
