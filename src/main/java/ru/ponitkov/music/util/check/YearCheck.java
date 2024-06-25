package ru.ponitkov.music.util.check;

import java.time.LocalDate;

public class YearCheck {
    private static final int MAX_YEAR = LocalDate.now().getYear();

    public boolean isCorrect(String year) {
        try {
            return Integer.parseInt(year) <= MAX_YEAR;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
