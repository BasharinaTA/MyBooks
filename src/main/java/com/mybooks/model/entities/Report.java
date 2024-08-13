package com.mybooks.model.entities;

public enum Report {

    notRead("Недочитанные книги"),
    wrongDates("Книги с некорректными датами"),
    lastRead("Последние прочитанные книги"),
    thisYearRead("Дочитанные за последний год книги");

    private final String name;
    Report(String name) {
        this. name = name;
    }

    public String getName() {
        return name;
    }
}
