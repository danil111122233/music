package ru.ponitkov.music.dto.musicartist.view;

import lombok.Builder;

@Builder
public record MusicArtistRegistrationView(String name,
                                          String debutYear,
                                          String email,
                                          String password) {
}
