package ru.ponitkov.music.repository;

import ru.ponitkov.music.connection.ConnectionGetter;
import ru.ponitkov.music.dto.musicartist.controller.MusicArtistPasswordController;
import ru.ponitkov.music.entity.MusicArtist;
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

public class MusicArtistRepository {
    private static final String INSERT_TEMPLATE =
            "INSERT INTO music_artist(name, debut_year, email, password) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL_TEMPLATE =
            "SELECT id, name, debut_year, email FROM music_artist";

    private static final String SELECT_BY_ID_TEMPLATE =
            "SELECT name, debut_year, email FROM music_artist WHERE id = ?";

    private static final String UPDATE_TEMPLATE =
            "UPDATE music_artist SET name = ?, debut_year = ?, email = ?, password = ? WHERE id = ?";

    private static final String DELETE_TEMPLATE =
            "DELETE FROM music_artist WHERE id = ?";

    private static final String SELECT_PASSWORD_BY_LOGIN_TEMPLATE =
            "SELECT password FROM music_artist WHERE email = ?";

    private static final String SELECT_BY_LOGIN_TEMPLATE =
            "SELECT id, name, debut_year, password FROM music_artist WHERE email = ?";

    private static final Logger LOGGER = Logger.getLogger(MusicArtistRepository.class.getName());
    private final ConnectionGetter connectionGetter;

    public MusicArtistRepository(ConnectionGetter connectionGetter) {
        this.connectionGetter = connectionGetter;
    }

    public void insert(MusicArtist entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(INSERT_TEMPLATE)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getDebutYear());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert music artist entity: ", e);
            throw new DaoException(e);
        }
    }

    public List<MusicArtist> findAll() throws DaoException {
        List<MusicArtist> list = new ArrayList<>();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TEMPLATE)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(new MusicArtist(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("debut_year"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select all music artist entities: ", e);
            throw new DaoException(e);
        }

        return list;
    }

    public Optional<MusicArtist> findById(Long id) throws DaoException {
        Optional<MusicArtist> entity = Optional.empty();

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_TEMPLATE)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = Optional.of(new MusicArtist(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("debut_year"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select music artist entity by id: ", e);
            throw new DaoException(e);
        }

        return entity;
    }

    public void update(MusicArtist entity) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEMPLATE)) {

            statement.setLong(5, entity.getId());
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getDebutYear());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update music artist entity: ", e);
            throw new DaoException(e);
        }
    }

    public void delete(Long id) throws DaoException {
        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEMPLATE)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete music artist entity by id: ", e);
            throw new DaoException(e);
        }
    }

    public MusicArtistPasswordController findPasswordByLogin(String login) throws DaoException {
        String password = null;

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN_TEMPLATE)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select music artist entity by login: ", e);

            throw new DaoException(e);
        }

        return MusicArtistPasswordController.builder()
                .password(Optional.ofNullable(password))
                .build();
    }

    public MusicArtist findByLogin(String login) throws DaoException {
        MusicArtist artist = null;

        try (Connection connection = connectionGetter.get();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_TEMPLATE)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    artist = new MusicArtist(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("debut_year"),
                            login,
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to select music artist entity by login: ", e);

            throw new DaoException(e);
        }

        return artist;
    }
}
