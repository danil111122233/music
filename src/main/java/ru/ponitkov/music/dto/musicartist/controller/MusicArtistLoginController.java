package ru.ponitkov.music.dto.musicartist.controller;

import lombok.Builder;

@Builder
public record MusicArtistLoginController(String email,
                                         String password) {
}
