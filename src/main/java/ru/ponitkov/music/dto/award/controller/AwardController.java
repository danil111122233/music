package ru.ponitkov.music.dto.award.controller;

import lombok.Builder;

@Builder
public record AwardController(Long id,
                              Long musicArtistId,
                              int year,
                              int place) {
}
