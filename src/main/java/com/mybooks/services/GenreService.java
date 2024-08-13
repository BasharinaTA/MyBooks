package com.mybooks.services;

import com.mybooks.repositories.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreService {

    private GenreRepository genreRepository;
}
