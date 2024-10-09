package com.mybooks.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookUpdateDto {

    private Integer id;
    private String type;
    private Integer genreId;
    private String author;
    private String name;
    private LocalDate dateStart;
    private LocalDate dateFinish;
}
