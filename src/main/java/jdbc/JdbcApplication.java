package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcApplication {

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_DB_URL = "jdbc:h2:mem:testdb";
    private static final String H2_DB_USER = "sa";
    private static final String H2_DB_PASSWORD = "";

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String MYSQL_DB_USER = "root";
    private static final String MYSQL_DB_PASSWORD = "root";

    public static void main(String[] args) throws ClassNotFoundException {
        useH2();
        useMySql();
    }

    private static void useH2() throws ClassNotFoundException {
        useDatabase(H2_DRIVER, H2_DB_URL, H2_DB_USER, H2_DB_PASSWORD);
    }

    private static void useMySql() throws ClassNotFoundException {
        useDatabase(MYSQL_DRIVER, MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASSWORD);
    }

    private static void useDatabase(String driver, String url, String user, String password) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            final MovieDAO movieDAOImpl = new MovieDAOImpl(connection);
            movieDAOImpl.createTable();
            movieDAOImpl.createMovie(new Movie("Back to the futureee with typo", "Fantasy", 1985));
            movieDAOImpl.createMovie(new Movie("Alien", "Fantasy", 1980));
            movieDAOImpl.findMovieById(1).ifPresent(System.out::println);
            movieDAOImpl.updateMoviesTitle(1, "Back to the future");
            movieDAOImpl.findAll().forEach(System.out::println);
            movieDAOImpl.deleteMovie(2);
            System.out.println(movieDAOImpl.findAll().size());
            movieDAOImpl.deleteTable();
        } catch (final SQLException exp) {
            System.err.println("Oops, something went wrong");
        }
    }


}
