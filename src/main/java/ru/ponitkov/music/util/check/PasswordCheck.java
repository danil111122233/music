package ru.ponitkov.music.util.check;

public class PasswordCheck {
    private static final String PASSWORD_PATTERN = "^.{5,}$";

    public boolean isCorrect(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
}
