package ru.ponitkov.music.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.entity.Album;
import ru.ponitkov.music.exception.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlbumRepositoryTest {
    private ConnectionGetter connectionGetter;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private AlbumRepository repository;

    @BeforeEach
    void setUp() {
        connectionGetter = mock(ConnectionGetter.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        repository = new AlbumRepository(connectionGetter);
    }

    @Test
    void insert() throws SQLException, DaoException {
        Album album = new Album(
                1L,
                1L,
                LocalDate.of(2020, 5, 10),
                "Test Album"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.insert(album);

        verify(statement).setLong(1, album.getMusicArtistId());
        verify(statement).setDate(2, Date.valueOf(album.getRelease()));
        verify(statement).setString(3, album.getTitle());

        verify(statement).executeUpdate();
    }

    @Test
    void findAll() throws SQLException, DaoException {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(
                1L,
                1L,
                LocalDate.of(2020, 5, 10),
                "Test Album 1"
        ));
        albums.add(new Album(
                2L,
                1L,
                LocalDate.of(2021, 7, 20),
                "Test Album 2"
        ));

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id"))
                .thenReturn(albums.get(0).getId())
                .thenReturn(albums.get(1).getId());

        when(resultSet.getLong("music_artist_id"))
                .thenReturn(albums.get(0).getMusicArtistId())
                .thenReturn(albums.get(1).getMusicArtistId());

        when(resultSet.getDate("release"))
                .thenReturn(Date.valueOf(albums.get(0).getRelease()))
                .thenReturn(Date.valueOf(albums.get(1).getRelease()));

        when(resultSet.getString("title"))
                .thenReturn(albums.get(0).getTitle())
                .thenReturn(albums.get(1).getTitle());

        List<Album> foundAlbums = repository.findAll();

        assertEquals(albums.size(), foundAlbums.size());
    }

    @Test
    void findById() throws SQLException, DaoException {
        Long albumId = 1L;
        Album album = new Album(
                albumId,
                1L,
                LocalDate.of(2020, 5, 10),
                "Test Album"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("music_artist_id")).thenReturn(album.getMusicArtistId());
        when(resultSet.getDate("release")).thenReturn(Date.valueOf(album.getRelease()));
        when(resultSet.getString("title")).thenReturn(album.getTitle());

        Album foundAlbum = repository.findById(albumId);

        assertEquals(album.getId(), foundAlbum.getId());
        assertEquals(album.getMusicArtistId(), foundAlbum.getMusicArtistId());
        assertEquals(album.getRelease(), foundAlbum.getRelease());
        assertEquals(album.getTitle(), foundAlbum.getTitle());
    }

    @Test
    void findByMusicArtistId() throws SQLException, DaoException {
        Long artistId = 1L;
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(
                1L,
                artistId,
                LocalDate.of(2020, 5, 10),
                "Test Album 1"
        ));
        albums.add(new Album(
                2L,
                artistId,
                LocalDate.of(2021, 7, 20),
                "Test Album 2"
        ));

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id"))
                .thenReturn(albums.get(0).getId())
                .thenReturn(albums.get(1).getId());

        when(resultSet.getDate("release"))
                .thenReturn(Date.valueOf(albums.get(0).getRelease()))
                .thenReturn(Date.valueOf(albums.get(1).getRelease()));

        when(resultSet.getString("title"))
                .thenReturn(albums.get(0).getTitle())
                .thenReturn(albums.get(1).getTitle());

        List<Album> foundAlbums = repository.findByMusicArtistId(artistId);

        assertEquals(albums.size(), foundAlbums.size());
    }

    @Test
    void update() throws SQLException, DaoException {
        Album updatedAlbum = new Album(
                1L,
                2L,
                LocalDate.of(2020, 5, 10),
                "Updated Album"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.update(updatedAlbum);

        verify(statement).setLong(1, updatedAlbum.getMusicArtistId());
        verify(statement).setDate(2, Date.valueOf(updatedAlbum.getRelease()));
        verify(statement).setString(3, updatedAlbum.getTitle());
        verify(statement).setLong(4, updatedAlbum.getId());

        verify(statement).executeUpdate();
    }

    @Test
    void delete() throws SQLException, DaoException {
        Long albumId = 1L;

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.delete(albumId);

        verify(statement).setLong(1, albumId);

        verify(statement).executeUpdate();
    }
}
