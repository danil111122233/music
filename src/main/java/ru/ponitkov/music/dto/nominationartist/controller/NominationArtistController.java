package ru.ponitkov.music.dto.nominationartist.controller;

import lombok.Builder;

@Builder
public record NominationArtistController(Long id,
                                         Long musicArtistId,
                                         Long awardId,
                                         String name,
                                         String result) {
}
