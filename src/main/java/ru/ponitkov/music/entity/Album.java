package ru.ponitkov.music.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Album {
    private Long id;
    private Long musicArtistId;
    private LocalDate release;
    private String title;
}
