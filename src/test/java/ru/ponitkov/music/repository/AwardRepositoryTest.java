package ru.ponitkov.music.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.entity.Award;
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

class AwardRepositoryTest {
    private ConnectionGetter connectionGetter;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private AwardRepository repository;

    @BeforeEach
    void setUp() {
        connectionGetter = mock(ConnectionGetter.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        repository = new AwardRepository(connectionGetter);
    }

    @Test
    void insert() throws SQLException, DaoException {
        Award award = new Award(
                1L,
                1L,
                2022,
                1
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.insert(award);

        verify(statement).setLong(1, award.getMusicArtistId());
        verify(statement).setInt(2, award.getYear());
        verify(statement).setInt(3, award.getPlace());

        verify(statement).executeUpdate();
    }


    @Test
    void findAll() throws SQLException, DaoException {
        List<Award> awards = new ArrayList<>();
        awards.add(new Award(
                1L,
                1L,
                2022,
                1
        ));
        awards.add(new Award(
                2L,
                1L,
                2023,
                2
        ));

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id"))
                .thenReturn(awards.get(0).getId())
                .thenReturn(awards.get(1).getId());

        when(resultSet.getLong("music_artist_id"))
                .thenReturn(awards.get(0).getMusicArtistId())
                .thenReturn(awards.get(1).getMusicArtistId());

        when(resultSet.getInt("year"))
                .thenReturn(awards.get(0).getYear())
                .thenReturn(awards.get(1).getYear());

        when(resultSet.getInt("place"))
                .thenReturn(awards.get(0).getPlace())
                .thenReturn(awards.get(1).getPlace());

        List<Award> foundAwards = repository.findAll();

        assertEquals(awards.size(), foundAwards.size());
    }

    @Test
    void findById() throws SQLException, DaoException {
        Long awardId = 1L;
        Award award = new Award(
                awardId,
                1L,
                2022,
                1
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("music_artist_id")).thenReturn(award.getMusicArtistId());
        when(resultSet.getInt("year")).thenReturn(award.getYear());
        when(resultSet.getInt("place")).thenReturn(award.getPlace());

        Optional<Award> foundAward = repository.findById(awardId);

        assertTrue(foundAward.isPresent());
        assertEquals(award.getMusicArtistId(), foundAward.get().getMusicArtistId());
        assertEquals(award.getYear(), foundAward.get().getYear());
        assertEquals(award.getPlace(), foundAward.get().getPlace());
    }

    @Test
    void findByMusicArtistId() throws SQLException, DaoException {
        Long artistId = 1L;
        List<Award> awards = new ArrayList<>();
        awards.add(new Award(
                1L,
                artistId,
                2022,
                1
        ));
        awards.add(new Award(
                2L,
                artistId,
                2023,
                2
        ));

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id"))
                .thenReturn(awards.get(0).getId())
                .thenReturn(awards.get(1).getId());

        when(resultSet.getInt("year"))
                .thenReturn(awards.get(0).getYear())
                .thenReturn(awards.get(1).getYear());

        when(resultSet.getInt("place"))
                .thenReturn(awards.get(0).getPlace())
                .thenReturn(awards.get(1).getPlace());

        List<Award> foundAwards = repository.findByMusicArtistId(artistId);

        assertEquals(awards.size(), foundAwards.size());
    }

    @Test
    void update() throws SQLException, DaoException {
        Award updatedAward = new Award(
                1L,
                2L,
                2023,
                2
        );

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.update(updatedAward);

        verify(statement).setLong(1, updatedAward.getMusicArtistId());
        verify(statement).setInt(2, updatedAward.getYear());
        verify(statement).setInt(3, updatedAward.getPlace());
        verify(statement).setLong(4, updatedAward.getId());

        verify(statement).executeUpdate();
    }

    @Test
    void delete() throws SQLException, DaoException {
        Long awardId = 1L;

        when(connectionGetter.get()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        repository.delete(awardId);

        verify(statement).setLong(1, awardId);

        verify(statement).executeUpdate();
    }
}
