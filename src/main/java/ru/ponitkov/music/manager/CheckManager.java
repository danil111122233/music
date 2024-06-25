package ru.ponitkov.music.manager;

import lombok.Getter;
import ru.ponitkov.music.util.check.EmailCheck;
import ru.ponitkov.music.util.check.NameCheck;
import ru.ponitkov.music.util.check.PasswordCheck;
import ru.ponitkov.music.util.check.YearCheck;

public class CheckManager {
    @Getter
    private static final NameCheck nameCheck;

    @Getter
    private static final YearCheck yearCheck;

    @Getter
    private static final EmailCheck emailCheck;

    @Getter
    private static final PasswordCheck passwordCheck;

    static {
        nameCheck = new NameCheck();
        yearCheck = new YearCheck();
        emailCheck = new EmailCheck();
        passwordCheck = new PasswordCheck();
    }
}
