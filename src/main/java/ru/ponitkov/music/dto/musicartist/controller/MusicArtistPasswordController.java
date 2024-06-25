package ru.ponitkov.music.dto.musicartist.controller;

import lombok.Builder;

import java.util.Optional;

@Builder
public record MusicArtistPasswordController(Optional<String> password) {
}
