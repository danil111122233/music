package ru.ponitkov.music.mapper;

import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.dto.album.view.AlbumView;
import ru.ponitkov.music.entity.Album;

public class AlbumMapper {
    public Album mapToEntity(AlbumController obj) {
        return Album.builder()
                .id(obj.id())
                .musicArtistId(obj.musicArtistId())
                .release(obj.release())
                .title(obj.title())
                .build();
    }

    public AlbumController mapToController(Album obj) {
        return AlbumController.builder()
                .id(obj.getId())
                .musicArtistId(obj.getMusicArtistId())
                .release(obj.getRelease())
                .title(obj.getTitle())
                .build();
    }

    public AlbumView mapToView(AlbumController obj) {
        return AlbumView.builder()
                .id(obj.id().toString())
                .musicArtistId(obj.musicArtistId().toString())
                .release(obj.release().toString())
                .title(obj.title())
                .build();
    }
}
