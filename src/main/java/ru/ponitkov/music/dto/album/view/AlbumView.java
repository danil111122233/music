package ru.ponitkov.music.dto.album.view;

import lombok.Builder;

@Builder
public record AlbumView(String id,
                        String musicArtistId,
                        String release,
                        String title) {
}
