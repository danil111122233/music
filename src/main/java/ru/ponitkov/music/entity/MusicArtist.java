package ru.ponitkov.music.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class MusicArtist {
    private Long id;
    private String name;
    private int debutYear;
    private String email;
    private String password;
}
