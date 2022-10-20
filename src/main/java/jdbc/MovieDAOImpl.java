package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {

    private final Connection connection;

    public MovieDAOImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try (final Statement createTableStatement = connection.createStatement()) {
            final String createTableQuery = "CREATE TABLE IF NOT EXISTS MOVIES (id INTEGER AUTO_INCREMENT, " +
                    "title VARCHAR(255), " +
                    "genre VARCHAR(255), " +
                    "yearOfRelease INTEGER, " +
                    "PRIMARY KEY (id))";
            createTableStatement.execute(createTableQuery);
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public void deleteTable() {
        try (final Statement deleteStructureStatement = connection.createStatement()) {
            deleteStructureStatement.execute("DROP TABLE MOVIES");
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public void createMovie(final Movie movie) {
        try (final PreparedStatement insertItemStatement = connection.prepareStatement("INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES (?, ?, ?)")) {
            insertItemStatement.setString(1, movie.getTitle());
            insertItemStatement.setString(2, movie.getGenre());
            insertItemStatement.setInt(3, movie.getYearOfRelease());
            insertItemStatement.executeUpdate();
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public void deleteMovie(final int id) {
        try (final PreparedStatement deleteItemStatement = connection.prepareStatement("DELETE FROM MOVIES WHERE id = ?")) {
            deleteItemStatement.setInt(1, id);
            deleteItemStatement.executeUpdate();
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public void updateMoviesTitle(final int id, final String newTitle) {
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE MOVIES SET title = ? WHERE id = ?")) {
            updateStatement.setString(1, newTitle);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public Optional<Movie> findMovieById(final int id) {
        try (PreparedStatement searchStatement = connection.prepareStatement("SELECT * FROM MOVIES WHERE id = ?")) {
            searchStatement.setInt(1, id);
            final boolean searchResult = searchStatement.execute();
            if (searchResult) {
                final ResultSet foundMovie = searchStatement.getResultSet();
                if (foundMovie.next()) {
                    final String title = foundMovie.getString(2);
                    final String genre = foundMovie.getString(3);
                    final Integer yearOfRelease = foundMovie.getInt(4);
                    return Optional.of(new Movie(id, title, genre, yearOfRelease));
                }
            }
            return Optional.empty();
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }

    @Override
    public List<Movie> findAll() {
        try (final Statement createTableStatement = connection.createStatement()) {
            final String findAllQuery = "SELECT * FROM MOVIES";
            final ResultSet moviesResultSet = createTableStatement.executeQuery(findAllQuery);
            final List<Movie> movies = new ArrayList<>();
            while (moviesResultSet.next()) {
                final Integer id = moviesResultSet.getInt(1);
                final String title = moviesResultSet.getString(2);
                final String genre = moviesResultSet.getString(3);
                final Integer yearOfRelease = moviesResultSet.getInt(4);
                movies.add(new Movie(id, title, genre, yearOfRelease));
            }
            return movies;
        } catch (final SQLException exp) {
            throw new DatabaseActionException(exp);
        }
    }
}
