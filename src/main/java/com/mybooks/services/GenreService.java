package com.mybooks.services;

import com.mybooks.model.entities.Genre;

import java.util.List;

public interface GenreService {

    Genre getById(Integer id);

    List<Genre> getAllByOrderByName();
}
