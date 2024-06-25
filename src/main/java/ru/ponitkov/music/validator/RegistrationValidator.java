package ru.ponitkov.music.validator;

import ru.ponitkov.music.dto.musicartist.view.MusicArtistRegistrationView;
import ru.ponitkov.music.repository.MusicArtistRepository;
import ru.ponitkov.music.util.check.EmailCheck;
import ru.ponitkov.music.util.check.NameCheck;
import ru.ponitkov.music.util.check.PasswordCheck;
import ru.ponitkov.music.util.check.YearCheck;

public class RegistrationValidator {
    private final MusicArtistRepository repository;
    private final NameCheck nameCheck;
    private final YearCheck yearCheck;
    private final EmailCheck emailCheck;
    private final PasswordCheck passwordCheck;

    public RegistrationValidator(MusicArtistRepository musicArtistRepository,
                                 NameCheck nameCheck,
                                 YearCheck yearCheck,
                                 EmailCheck emailCheck,
                                 PasswordCheck passwordCheck) {
        this.repository = musicArtistRepository;
        this.nameCheck = nameCheck;
        this.yearCheck = yearCheck;
        this.emailCheck = emailCheck;
        this.passwordCheck = passwordCheck;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean checkName(MusicArtistRegistrationView dto) {
        return nameCheck.isCorrect(dto.name());
    }

    private boolean checkYear(MusicArtistRegistrationView dto) {
        return yearCheck.isCorrect(dto.debutYear());
    }

    private boolean checkEmail(MusicArtistRegistrationView dto) {
        return emailCheck.isCorrect(dto.email());
    }

    private boolean checkPassword(MusicArtistRegistrationView dto) {
        return passwordCheck.isCorrect(dto.password());
    }

    public boolean validate(MusicArtistRegistrationView registrationViewDto) {
        return checkName(registrationViewDto) &&
                checkYear(registrationViewDto) &&
                checkEmail(registrationViewDto) &&
                checkPassword(registrationViewDto);
    }
}
