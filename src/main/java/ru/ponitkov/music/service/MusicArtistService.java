package ru.ponitkov.music.service;

import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistLoginController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistPasswordController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistRegistrationController;
import ru.ponitkov.music.entity.MusicArtist;
import ru.ponitkov.music.mapper.MusicArtistMapper;
import ru.ponitkov.music.repository.MusicArtistRepository;
import ru.ponitkov.music.util.HashPassword;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.logging.Logger;

public class MusicArtistService {
    private static final Logger LOGGER = Logger.getLogger(MusicArtistService.class.getName());
    private final MusicArtistRepository musicArtistRepository;
    private final MusicArtistMapper musicArtistMapper;

    public MusicArtistService(MusicArtistRepository musicArtistRepository, MusicArtistMapper musicArtistMapper) {
        this.musicArtistRepository = musicArtistRepository;
        this.musicArtistMapper = musicArtistMapper;
    }

    public void insert(MusicArtistRegistrationController controller) {
        musicArtistRepository.insert(musicArtistMapper.mapToEntity(controller));
    }

    public Optional<MusicArtistController> login(MusicArtistLoginController controller) throws NoSuchAlgorithmException, InvalidKeySpecException {
        MusicArtistPasswordController passwordController = musicArtistRepository.findPasswordByLogin(controller.email());

        if (passwordController == null || passwordController.password().isEmpty() ||
                !HashPassword.verify(controller.password(), passwordController.password().orElse(""))) {
            return Optional.empty();
        }

        MusicArtist artist = musicArtistRepository.findByLogin(controller.email());
        MusicArtistController controllerDto = musicArtistMapper.mapToController(artist);

        return Optional.of(controllerDto);
    }

    public void update(MusicArtistController controller) {
        musicArtistRepository.update(musicArtistMapper.mapToEntity(controller));
    }

    public void delete(Long id) {
        musicArtistRepository.delete(id);
    }
}
