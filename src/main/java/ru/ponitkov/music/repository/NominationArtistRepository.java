package ru.ponitkov.music.repository;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class NominationArtistRepository {
    private static final String INSERT_TEMPLATE =
            "INSERT INTO nomination_artists(music_artist_id, award_id, name) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_TEMPLATE =
            "SELECT id, music_artist_id, award_id, name FROM nomination_artists";

    private static final String SELECT_BY_ID_TEMPLATE =
            "SELECT music_artist_id, award_id, name FROM nomination_artists WHERE id = ?";

    private static final String UPDATE_TEMPLATE =
            "UPDATE nomination_artists SET music_artist_id = ?, award_id = ?, name = ? WHERE id = ?";

    private static final String DELETE_TEMPLATE =
            "DELETE FROM nomination_artists WHERE id = ?";

    private static final String SELECT_BY_ARTIST_ID_TEMPLATE =
            "SELECT id, award_id, name FROM nomination_artists WHERE music_artist_id = ?";

    private static final Logger LOGGER = Logger.getLogger(NominationArtistRepository.class.getName());
    private final ConnectionGetter connectionGetter;

    public NominationArtistRepository(ConnectionGetter connectionGetter) {
        this.connectionGetter = connectionGetter;
    }

    public void insert(NominationArtist entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(INSERT_TEMPLATE)) {

            statement.setLong(1, entity.getMusicArtistId());
            statement.setLong(2, entity.getAwardId());
            statement.setString(3, entity.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert nomination artist entity: ", e);
            throw new DaoException(e);
        }
    }

    public List<NominationArtist> findAll() throws DaoException {
        List<NominationArtist> list = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TEMPLATE)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new NominationArtist(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getLong("award_id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select all nomination artist entities: ", e);
            throw new DaoException(e);
        }

        return list;
    }

    public Optional<NominationArtist> findById(Long id) throws DaoException {
        Optional<NominationArtist> entity = Optional.empty();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = Optional.of(new NominationArtist(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getLong("award_id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select nomination artist entity by id: ", e);
            throw new DaoException(e);
        }

        return entity;
    }

    public List<NominationArtist> findByMusicArtistId(Long id) {
        List<NominationArtist> nominationArtists = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ARTIST_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nominationArtists.add(new NominationArtist(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getLong("award_id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select nomination artist entity by artist id: " + e.getMessage());

            throw new DaoException(e);
        }

        return nominationArtists;
    }

    public void update(NominationArtist entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEMPLATE)) {

            statement.setLong(4, entity.getId());
            statement.setLong(1, entity.getMusicArtistId());
            statement.setLong(2, entity.getAwardId());
            statement.setString(3, entity.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update nomination artist entity: ", e);
            throw new DaoException(e);
        }
    }

    public void delete(Long id) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEMPLATE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete nomination artist entity by id: ", e);
            throw new DaoException(e);
        }
    }
}
