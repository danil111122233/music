package ru.ponitkov.music.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.entity.NominationArtist;
import ru.ponitkov.music.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NominationArtistRepositoryTest {
    private ConnectionGetter connectionGetter;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private NominationArtistRepository repository;

    @BeforeEach
    void setUp() {
        connectionGetter = mock(ConnectionGetter.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        repository = new NominationArtistRepository(connectionGetter);
    }

    @Test
    void insert() throws SQLException, DaoException {
        NominationArtist nominationArtist = new NominationArtist(
                1L,
                1L,
                1L,
                "Nomination Artist Name"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.insert(nominationArtist);

        verify(statement).setLong(1, nominationArtist.getMusicArtistId());
        verify(statement).setLong(2, nominationArtist.getAwardId());
        verify(statement).setString(3, nominationArtist.getName());

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
        when(resultSet.getLong("music_artist_id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getLong("award_id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getString("name")).thenReturn("Nomination Artist 1").thenReturn("Nomination Artist 2");

        var foundNominationArtists = repository.findAll();

        assertEquals(2, foundNominationArtists.size());
        assertEquals(1L, foundNominationArtists.get(0).getId());
        assertEquals(1L, foundNominationArtists.get(0).getMusicArtistId());
        assertEquals(1L, foundNominationArtists.get(0).getAwardId());
        assertEquals("Nomination Artist 1", foundNominationArtists.get(0).getName());

        assertEquals(2L, foundNominationArtists.get(1).getId());
        assertEquals(2L, foundNominationArtists.get(1).getMusicArtistId());
        assertEquals(2L, foundNominationArtists.get(1).getAwardId());
        assertEquals("Nomination Artist 2", foundNominationArtists.get(1).getName());
    }

    @Test
    void findById() throws SQLException, DaoException {
        Long nominationArtistId = 1L;
        NominationArtist nominationArtist = new NominationArtist(
                nominationArtistId,
                1L,
                1L,
                "Nomination Artist Name"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("music_artist_id")).thenReturn(nominationArtist.getMusicArtistId());
        when(resultSet.getLong("award_id")).thenReturn(nominationArtist.getAwardId());
        when(resultSet.getString("name")).thenReturn(nominationArtist.getName());

        Optional<NominationArtist> foundNominationArtist = repository.findById(nominationArtistId);

        assertTrue(foundNominationArtist.isPresent());
        assertEquals(nominationArtist.getMusicArtistId(), foundNominationArtist.get().getMusicArtistId());
        assertEquals(nominationArtist.getAwardId(), foundNominationArtist.get().getAwardId());
        assertEquals(nominationArtist.getName(), foundNominationArtist.get().getName());
    }

    @Test
    void findByMusicArtistId() throws SQLException, DaoException {
        Long musicArtistId = 1L;
        List<NominationArtist> nominationArtists = new ArrayList<>();
        nominationArtists.add(new NominationArtist(
                1L,
                musicArtistId,
                1L,
                "Nomination Artist 1"
        ));
        nominationArtists.add(new NominationArtist(
                2L,
                musicArtistId,
                2L,
                "Nomination Artist 2"
        ));

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id"))
                .thenReturn(nominationArtists.get(0).getId())
                .thenReturn(nominationArtists.get(1).getId());

        when(resultSet.getLong("award_id"))
                .thenReturn(nominationArtists.get(0).getAwardId())
                .thenReturn(nominationArtists.get(1).getAwardId());

        when(resultSet.getString("name"))
                .thenReturn(nominationArtists.get(0).getName())
                .thenReturn(nominationArtists.get(1).getName());

        List<NominationArtist> foundNominationArtists = repository.findByMusicArtistId(musicArtistId);

        assertEquals(nominationArtists.size(), foundNominationArtists.size());
    }

    @Test
    void update() throws SQLException, DaoException {
        NominationArtist updatedNominationArtist = new NominationArtist(
                1L,
                2L,
                2L,
                "Updated Nomination Artist Name"
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.update(updatedNominationArtist);

        verify(statement).setLong(1, updatedNominationArtist.getMusicArtistId());
        verify(statement).setLong(2, updatedNominationArtist.getAwardId());
        verify(statement).setString(3, updatedNominationArtist.getName());
        verify(statement).setLong(4, updatedNominationArtist.getId());

        verify(statement).executeUpdate();
    }

    @Test
    void delete() throws SQLException, DaoException {
        Long nominationArtistId = 1L;

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.delete(nominationArtistId);

        verify(statement).setLong(1, nominationArtistId);

        verify(statement).executeUpdate();
    }
}
