package ru.ponitkov.music.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.entity.MusicArtist;
import ru.ponitkov.music.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusicArtistRepositoryTest {
    private ConnectionGetter connectionGetter;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private MusicArtistRepository repository;

    @BeforeEach
    void setUp() {
        connectionGetter = mock(ConnectionGetter.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        repository = new MusicArtistRepository(connectionGetter);
    }

    @Test
    void insert() throws SQLException, DaoException {
        MusicArtist artist = new MusicArtist(
                1L,
                "Artist Name",
                2000,
                "artist@example.com",
                "password"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.insert(artist);

        verify(statement).setString(1, artist.getName());
        verify(statement).setInt(2, artist.getDebutYear());
        verify(statement).setString(3, artist.getEmail());
        verify(statement).setString(4, artist.getPassword());

        verify(statement).executeUpdate();
    }

    @Test
    void findAll() throws SQLException, DaoException {
        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getString("name")).thenReturn("Artist 1").thenReturn("Artist 2");
        when(resultSet.getInt("debut_year")).thenReturn(2000).thenReturn(2005);
        when(resultSet.getString("email")).thenReturn("artist1@example.com").thenReturn("artist2@example.com");

        var foundArtists = repository.findAll();

        assertEquals(2, foundArtists.size());
        assertEquals(1L, foundArtists.get(0).getId());
        assertEquals("Artist 1", foundArtists.get(0).getName());
        assertEquals(2000, foundArtists.get(0).getDebutYear());
        assertEquals("artist1@example.com", foundArtists.get(0).getEmail());

        assertEquals(2L, foundArtists.get(1).getId());
        assertEquals("Artist 2", foundArtists.get(1).getName());
        assertEquals(2005, foundArtists.get(1).getDebutYear());
        assertEquals("artist2@example.com", foundArtists.get(1).getEmail());
    }

    @Test
    void findById() throws SQLException, DaoException {
        Long artistId = 1L;
        MusicArtist artist = new MusicArtist(
                artistId,
                "Artist Name",
                2000,
                "artist@example.com",
                "password"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn(artist.getName());
        when(resultSet.getInt("debut_year")).thenReturn(artist.getDebutYear());
        when(resultSet.getString("email")).thenReturn(artist.getEmail());
        when(resultSet.getString("password")).thenReturn(artist.getPassword());

        Optional<MusicArtist> foundArtist = repository.findById(artistId);

        assertTrue(foundArtist.isPresent());
        assertEquals(artist.getName(), foundArtist.get().getName());
        assertEquals(artist.getDebutYear(), foundArtist.get().getDebutYear());
        assertEquals(artist.getEmail(), foundArtist.get().getEmail());
        assertEquals(artist.getPassword(), foundArtist.get().getPassword());
    }

    @Test
    void update() throws SQLException, DaoException {
        MusicArtist updatedArtist = new MusicArtist(
                1L,
                "Updated Artist Name",
                2005,
                "updated_artist@example.com",
                "new_password"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.update(updatedArtist);

        verify(statement).setString(1, updatedArtist.getName());
        verify(statement).setInt(2, updatedArtist.getDebutYear());
        verify(statement).setString(3, updatedArtist.getEmail());
        verify(statement).setString(4, updatedArtist.getPassword());
        verify(statement).setLong(5, updatedArtist.getId());

        verify(statement).executeUpdate();
    }

    @Test
    void delete() throws SQLException, DaoException {
        Long artistId = 1L;

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.delete(artistId);

        verify(statement).setLong(1, artistId);

        verify(statement).executeUpdate();
    }

    @Test
    void findPasswordByLogin() throws SQLException, DaoException {
        String login = "artist@example.com";
        String expectedPassword = "password";

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("password")).thenReturn(expectedPassword);

        var result = repository.findPasswordByLogin(login);

        assertEquals(expectedPassword, result.password().orElse(null));
    }

    @Test
    void findByLogin() throws SQLException, DaoException {
        String login = "artist@example.com";
        MusicArtist expectedArtist = new MusicArtist(
                1L,
                "Artist Name",
                2000,
                login,
                "password"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedArtist.getId());
        when(resultSet.getString("name")).thenReturn(expectedArtist.getName());
        when(resultSet.getInt("debut_year")).thenReturn(expectedArtist.getDebutYear());
        when(resultSet.getString("password")).thenReturn(expectedArtist.getPassword());

        var result = repository.findByLogin(login);

        assertEquals(expectedArtist.getId(), result.getId());
        assertEquals(expectedArtist.getName(), result.getName());
        assertEquals(expectedArtist.getDebutYear(), result.getDebutYear());
        assertEquals(expectedArtist.getEmail(), result.getEmail());
        assertEquals(expectedArtist.getPassword(), result.getPassword());
    }
}
