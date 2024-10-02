package com.mybooks.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Report {

    NOT_READ("Недочитанные книги", "notRead"),
    INCORRECT_DATES("Книги с некорректными датами", "incorrectDates"),
    LAST_READ("Последние прочитанные книги", "lastRead"),
    THIS_YEAR_READ("Дочитанные за последний год книги", "thisYearRead");

    private final String name;
    private final String url;
}
