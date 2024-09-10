package com.mybooks.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    AUDIOBOOK ("Аудиокнига"),
    BOOK ("Бумажная"),
    EBOOK ("Электронная");

    private final String description;
}
