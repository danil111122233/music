package ru.ponitkov.music.dto.album.controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AlbumController(Long id,
                              Long musicArtistId,
                              LocalDate release,
                              String title) {
}
