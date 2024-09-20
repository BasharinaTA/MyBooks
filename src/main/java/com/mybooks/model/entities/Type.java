package com.mybooks.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Type {

    AUDIOBOOK ("Аудиокнига"),
    BOOK ("Бумажная"),
    EBOOK ("Электронная");

    private final String description;

    public static Type fromDescription(String name) {
        return Arrays.stream(Type.values())
                .filter(t -> t.getDescription().equals(name))
                .findFirst().orElseThrow(() ->
                        new IllegalArgumentException("Тип с указанным именем не существует"));
    }
}
