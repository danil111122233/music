package ru.ponitkov.music.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class NominationArtist {
    private Long id;
    private Long musicArtistId;
    private Long awardId;
    private String name;
}
