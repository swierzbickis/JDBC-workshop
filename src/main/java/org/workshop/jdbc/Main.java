package org.workshop.jdbc;

import java.sql.*;

public class Main {

    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_DB_URL = "jdbc:h2:mem:testdb";
    private static final String H2_DB_USER = "sa";
    private static final String H2_DB_PASSWORD = "";

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String MYSQL_DB_USER = "root";
    private static final String MYSQL_DB_PASSWORD = "root";

    public static void main(String[] args) {
        try {
            useH2();
            useMySql();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void useH2() throws ClassNotFoundException {
        useDatabase(H2_DRIVER, H2_DB_URL, H2_DB_USER, H2_DB_PASSWORD);
    }

    private static void useMySql() throws ClassNotFoundException {
        useDatabase(MYSQL_DRIVER, MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASSWORD);
    }

    private static void useDatabase(String driver, String url, String user, String password) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            drop(connection);
            create(connection);
            insert(connection);
            update(connection);
            delete(connection);
            read(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void drop(Connection connection) {
        try (Statement dropStatement = connection.createStatement()) {
            dropStatement.execute("DROP TABLE IF EXISTS MOVIES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void create(Connection connection) {
        try (Statement createTableStatement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS MOVIES (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "title VARCHAR(255), " +
                    "genre VARCHAR(255), " +
                    "yearOfRelease INTEGER, " +
                    "PRIMARY KEY (id))";
            createTableStatement.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Connection connection) {
        try (Statement insertItemStatement = connection.createStatement();) {
            String insertStarWarsQuery = "INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES ('Star Wars', 'Action', 2015)";
            String insertHarryPotterQuery = "INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES ('Harry Potter', 'Fantasy', 2001)";
            String insertRockyQuery = "INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES ('Rocky', 'Sports', 1979)";
            insertItemStatement.execute(insertStarWarsQuery);
            insertItemStatement.execute(insertHarryPotterQuery);
            insertItemStatement.execute(insertRockyQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void update(Connection connection) {
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE MOVIES SET title = ? WHERE id = ?")) {
            updateStatement.setString(1, "Star Wars Force Awakens");
            updateStatement.setInt(2, 1);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void delete(Connection connection) {
        try (Statement deleteItemStatement = connection.createStatement()) {
            String deleteItemQuery = "DELETE FROM MOVIES WHERE id = 2";
            deleteItemStatement.execute(deleteItemQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void read(Connection connection) {
        try (Statement readItemsStatement = connection.createStatement()) {
            String readItemsQuery = "SELECT * FROM MOVIES";
            ResultSet rs = readItemsStatement.executeQuery(readItemsQuery);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int yearOfRelease = rs.getInt("yearOfRelease");

                System.out.println("#####################");
                System.out.println("Id: " + id);
                System.out.println("Title: " + title);
                System.out.println("Genre: " + genre);
                System.out.println("Year of release: " + yearOfRelease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
