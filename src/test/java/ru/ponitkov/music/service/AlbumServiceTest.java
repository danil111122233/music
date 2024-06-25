package ru.ponitkov.music.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ponitkov.music.dto.album.controller.AlbumController;
import ru.ponitkov.music.entity.Album;
import ru.ponitkov.music.exception.DaoException;
import ru.ponitkov.music.mapper.AlbumMapper;
import ru.ponitkov.music.repository.AlbumRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlbumServiceTest {
    private static Album album;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private AlbumMapper albumMapper;
    @InjectMocks
    private AlbumService albumService;

    @BeforeAll
    static void init() {
        album = new Album(
                1L,
                1L,
                LocalDate.now(),
                "Test Album"
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert() throws DaoException {
        AlbumController albumController = new AlbumController(
                1L,
                1L,
                LocalDate.now(),
                "Test Album"
        );

        when(albumMapper.mapToEntity(any(AlbumController.class))).thenReturn(album);

        albumService.insert(albumController);

        verify(albumRepository, times(1)).insert(any(Album.class));
    }

    @Test
    void findByMusicArtistId() {
        Long artistId = 1L;
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(1L, artistId, LocalDate.now(), "Album 1"));
        albums.add(new Album(2L, artistId, LocalDate.now(), "Album 2"));

        when(albumRepository.findByMusicArtistId(artistId)).thenReturn(albums);
        when(albumMapper.mapToController(any(Album.class))).thenAnswer(invocation -> {
            Album albumEntity = invocation.getArgument(0);
            return new AlbumController(artistId, albumEntity.getMusicArtistId(), albumEntity.getRelease(), albumEntity.getTitle());
        });

        List<AlbumController> foundAlbums = albumService.findByMusicArtistId(artistId);

        assertEquals(2, foundAlbums.size());
        assertEquals("Album 1", foundAlbums.get(0).title());
        assertEquals("Album 2", foundAlbums.get(1).title());
    }

    @Test
    void findById() {
        Long albumId = 1L;
        Album albumEntity = new Album(albumId, 1L, LocalDate.now(), "Test Album");

        when(albumRepository.findById(albumId)).thenReturn(albumEntity);
        when(albumMapper.mapToController(albumEntity)).thenReturn(new AlbumController(albumId, albumEntity.getMusicArtistId(), albumEntity.getRelease(), albumEntity.getTitle()));

        AlbumController foundAlbum = albumService.findById(albumId);

        assertEquals(albumEntity.getTitle(), foundAlbum.title());
    }

    @Test
    void update() throws DaoException {
        AlbumController albumController = new AlbumController(1L, 1L, LocalDate.now(), "Test Album");
        Album albumEntity = new Album(1L, albumController.musicArtistId(), albumController.release(), albumController.title());

        doNothing().when(albumRepository).update(any(Album.class));
        when(albumMapper.mapToEntity(any(AlbumController.class))).thenReturn(albumEntity);

        albumService.update(albumController);

        verify(albumRepository, times(1)).update(any(Album.class));
    }

    @Test
    void delete() throws DaoException {
        Long albumId = 1L;

        doNothing().when(albumRepository).delete(albumId);

        albumService.delete(albumId);

        verify(albumRepository, times(1)).delete(albumId);
    }
}
