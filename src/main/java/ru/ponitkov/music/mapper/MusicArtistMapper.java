package ru.ponitkov.music.mapper;

import ru.ponitkov.music.dto.musicartist.controller.MusicArtistController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistLoginController;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistRegistrationController;
import ru.ponitkov.music.dto.musicartist.view.MusicArtistLoginView;
import ru.ponitkov.music.dto.musicartist.view.MusicArtistRegistrationView;
import ru.ponitkov.music.dto.musicartist.view.MusicArtistView;
import ru.ponitkov.music.entity.MusicArtist;
import ru.ponitkov.music.util.HashPassword;

public class MusicArtistMapper {
    public MusicArtist mapToEntity(MusicArtistController obj) {
        return MusicArtist.builder()
                .id(obj.id())
                .name(obj.name())
                .debutYear(obj.debutYear())
                .email(obj.email())
                .password(obj.password())
                .build();
    }

    public MusicArtistController mapToController(MusicArtist obj) {
        return MusicArtistController.builder()
                .id(obj.getId())
                .name(obj.getName())
                .debutYear(obj.getDebutYear())
                .email(obj.getEmail())
                .password(obj.getPassword())
                .build();
    }

    public MusicArtistRegistrationController mapToRegistrationController(MusicArtistRegistrationView obj) {
        return MusicArtistRegistrationController.builder()
                .name(obj.name())
                .debutYear(Integer.parseInt(obj.debutYear()))
                .email(obj.email())
                .password(obj.password())
                .build();
    }

    public MusicArtistLoginController mapToLoginController(MusicArtistLoginView obj) {
        return MusicArtistLoginController.builder()
                .email(obj.email())
                .password(obj.password())
                .build();
    }

    public MusicArtist mapToEntity(MusicArtistRegistrationController obj) {
        return MusicArtist.builder()
                .name(obj.name())
                .debutYear(obj.debutYear())
                .email(obj.email())
                .password(HashPassword.hash(obj.password()))
                .build();
    }

    public MusicArtistView mapToView(MusicArtistController obj) {
        return MusicArtistView.builder()
                .id(String.valueOf(obj.id()))
                .name(obj.name())
                .debutYear(String.valueOf(obj.debutYear()))
                .email(obj.email())
                .password(obj.password())
                .build();
    }
}
