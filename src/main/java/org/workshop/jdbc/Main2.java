package org.workshop.jdbc;

import org.workshop.jdbc.connection.ConnectionFactory;
import org.workshop.jdbc.dao.CustomerDAO;
import org.workshop.jdbc.dao.CustomerDAOImpl;
import org.workshop.jdbc.dto.CustomerDTO;

import java.sql.*;

public class Main2 {

    public static void main(String[] args) {

        createDatabase();
        CustomerDAO customerDAO = new CustomerDAOImpl();

        customerDAO.save(new CustomerDTO("Jan", "Kowalski", "jan.kowalski@gmail.com"));
        //customerDAO.findById();

        //  customerDAO.update(); //name update
        // customerDAO.findByName();

        // customerDAO.delete();

        customerDAO.findAll()
                .forEach(c -> System.out.println(c.toString()));

    }

    private static void createDatabase() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                            "ID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                            "FIRSTNAME VARCHAR(50)," +
                            "LASTNAME  VARCHAR(50)," +
                            "EMAIL     VARCHAR(50))"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
