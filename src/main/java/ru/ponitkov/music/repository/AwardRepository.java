package ru.ponitkov.music.repository;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AwardRepository {
    private static final String INSERT_TEMPLATE =
            "INSERT INTO award(music_artist_id, year, place) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_TEMPLATE =
            "SELECT id, music_artist_id, year, place FROM award";

    private static final String SELECT_BY_ID_TEMPLATE =
            "SELECT music_artist_id, year, place FROM award WHERE id = ?";

    private static final String UPDATE_TEMPLATE =
            "UPDATE award SET music_artist_id = ?, year = ?, place = ? WHERE id = ?";

    private static final String DELETE_TEMPLATE =
            "DELETE FROM award WHERE id = ?";

    private static final String SELECT_BY_ARTIST_ID_TEMPLATE =
            "SELECT id, year, place FROM award WHERE music_artist_id = ?";

    private static final Logger LOGGER = Logger.getLogger(AwardRepository.class.getName());
    private final ConnectionGetter connectionGetter;

    public AwardRepository(ConnectionGetter connectionGetter) {
        this.connectionGetter = connectionGetter;
    }

    public void insert(Award entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(INSERT_TEMPLATE)) {

            statement.setLong(1, entity.getMusicArtistId());
            statement.setInt(2, entity.getYear());
            statement.setInt(3, entity.getPlace());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert award entity: ", e);
            throw new DaoException(e);
        }
    }

    public List<Award> findAll() throws DaoException {
        List<Award> list = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TEMPLATE)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new Award(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getInt("year"),
                        resultSet.getInt("place")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select all award entities: ", e);
            throw new DaoException(e);
        }

        return list;
    }

    public Optional<Award> findById(Long id) throws DaoException {
        Optional<Award> entity = Optional.empty();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = Optional.of(new Award(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getInt("year"),
                        resultSet.getInt("place")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select award entity by id: ", e);
            throw new DaoException(e);
        }

        return entity;
    }

    public List<Award> findByMusicArtistId(Long id) {
        List<Award> awards = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ARTIST_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                awards.add(new Award(
                        resultSet.getLong("id"),
                        id,
                        resultSet.getInt("year"),
                        resultSet.getInt("place")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select award entity by artist id: " + e.getMessage());

            throw new DaoException(e);
        }

        return awards;
    }

    public void update(Award entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEMPLATE)) {

            statement.setLong(4, entity.getId());
            statement.setLong(1, entity.getMusicArtistId());
            statement.setInt(2, entity.getYear());
            statement.setInt(3, entity.getPlace());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update award entity: ", e);
            throw new DaoException(e);
        }
    }

    public void delete(Long id) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEMPLATE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete award entity by id: ", e);
            throw new DaoException(e);
        }
    }
}
