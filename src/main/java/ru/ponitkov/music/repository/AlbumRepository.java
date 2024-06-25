package ru.ponitkov.music.repository;

import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.entity.Album;
import ru.ponitkov.music.exception.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlbumRepository {
    private static final String INSERT_TEMPLATE =
            "INSERT INTO album(music_artist_id, release, title) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_TEMPLATE =
            "SELECT id, music_artist_id, release, title FROM album";

    private static final String SELECT_BY_ID_TEMPLATE =
            "SELECT music_artist_id, release, title FROM album WHERE id = ?";

    private static final String SELECT_BY_ARTIST_ID_TEMPLATE =
            "SELECT id, release, title FROM album WHERE music_artist_id = ?";

    private static final String UPDATE_TEMPLATE =
            "UPDATE album SET music_artist_id = ?, release = ?, title = ? WHERE id = ?";

    private static final String DELETE_TEMPLATE =
            "DELETE FROM album WHERE id = ?";

    private static final Logger LOGGER = Logger.getLogger(AlbumRepository.class.getName());
    private final ConnectionGetter connectionGetter;

    public AlbumRepository(ConnectionGetter connectionGetter) {
        this.connectionGetter = connectionGetter;
    }

    public void insert(Album entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(INSERT_TEMPLATE)) {

            statement.setLong(1, entity.getMusicArtistId());
            statement.setDate(2, Date.valueOf(entity.getRelease()));
            statement.setString(3, entity.getTitle());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert album entity: ", e);
            throw new DaoException(e);
        }
    }

    public List<Album> findAll() throws DaoException {
        List<Album> list = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TEMPLATE)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new Album(
                        resultSet.getLong("id"),
                        resultSet.getLong("music_artist_id"),
                        resultSet.getDate("release").toLocalDate(),
                        resultSet.getString("title")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select all album entities: ", e);
            throw new DaoException(e);
        }

        return list;
    }

    public Album findById(Long id) throws DaoException {
        Album entity = null;

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = new Album(
                        id,
                        resultSet.getLong("music_artist_id"),
                        resultSet.getDate("release").toLocalDate(),
                        resultSet.getString("title")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select album entity by id: ", e);
            throw new DaoException(e);
        }

        return entity;
    }

    public List<Album> findByMusicArtistId(Long id) {
        List<Album> albums = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ARTIST_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                albums.add(new Album(
                        resultSet.getLong("id"),
                        id,
                        resultSet.getDate("release").toLocalDate(),
                        resultSet.getString("title")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select album entity by artist id: " + e.getMessage());

            throw new DaoException(e);
        }

        return albums;
    }

    public void update(Album entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEMPLATE)) {

            statement.setLong(4, entity.getId());
            statement.setLong(1, entity.getMusicArtistId());
            statement.setDate(2, Date.valueOf(entity.getRelease()));
            statement.setString(3, entity.getTitle());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update album entity: ", e);
            throw new DaoException(e);
        }
    }

    public void delete(Long id) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEMPLATE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete album entity by id: ", e);
            throw new DaoException(e);
        }
    }
}
