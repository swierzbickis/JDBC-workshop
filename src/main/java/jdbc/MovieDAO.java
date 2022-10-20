package jdbc;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {
    void createTable();

    void createMovie(Movie fantasy);

    Optional<Movie> findMovieById(int i);

    void updateMoviesTitle(int i, String back_to_the_future);

    List<Movie> findAll();

    void deleteMovie(int i);

    void deleteTable();
}
