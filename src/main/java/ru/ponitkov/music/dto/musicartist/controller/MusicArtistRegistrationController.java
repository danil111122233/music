package ru.ponitkov.music.dto.musicartist.controller;

import lombok.Builder;

@Builder
public record MusicArtistRegistrationController(String name,
                                                int debutYear,
                                                String email,
                                                String password) {
}
