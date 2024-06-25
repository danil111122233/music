package ru.ponitkov.music.dto.musicartist.controller;

import lombok.Builder;

@Builder
public record MusicArtistController(Long id,
                                    String name,
                                    String country,
                                    String genre,
                                    int debutYear,
                                    String email,
                                    String password) {
}
