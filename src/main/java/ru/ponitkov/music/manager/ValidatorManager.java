package ru.ponitkov.music.manager;

import lombok.Getter;
import ru.ponitkov.music.validator.RegistrationValidator;

public class ValidatorManager {
    @Getter
    private static final RegistrationValidator registrationValidator;

    static {
        registrationValidator = new RegistrationValidator(
                RepositoryManager.getMusicArtistRepository(),
                CheckManager.getNameCheck(),
                CheckManager.getYearCheck(),
                CheckManager.getEmailCheck(),
                CheckManager.getPasswordCheck()
        );
    }
}
