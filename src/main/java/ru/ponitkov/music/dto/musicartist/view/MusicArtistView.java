package ru.ponitkov.music.dto.musicartist.view;

import lombok.Builder;

@Builder
public record MusicArtistView(String id,
                              String name,
                              String country,
                              String genre,
                              String debutYear,
                              String email,
                              String password) {
}
