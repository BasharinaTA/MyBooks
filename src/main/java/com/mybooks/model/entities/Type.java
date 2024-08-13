package com.mybooks.model.entities;

public enum Type {

    AUDIOBOOK ("Аудиокнига"),
    BOOK ("Бумажная"),
    EBOOK ("Электронная");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
