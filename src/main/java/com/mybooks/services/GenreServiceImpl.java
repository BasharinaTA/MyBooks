package com.mybooks.services;

import com.mybooks.exceptions.BaseException;
import com.mybooks.model.entities.Genre;
import com.mybooks.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Override
    public Genre getById(Integer id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new BaseException("Жанр не найден"));
    }

    @Override
    public List<Genre> getAllByOrderByName() {
        return genreRepository.findAllByOrderByName();
    }
}
