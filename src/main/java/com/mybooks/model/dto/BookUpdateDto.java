package com.mybooks.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class BookUpdateDto {

    private Integer id;
    private String type;
    private Integer genreId;
    private String author;
    private String name;
    private Date dateStart;
    private Date dateFinish;
}
