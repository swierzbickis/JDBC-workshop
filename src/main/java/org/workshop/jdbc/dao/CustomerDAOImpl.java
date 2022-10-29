package org.workshop.jdbc.dao;

import org.workshop.jdbc.connection.ConnectionFactory;
import org.workshop.jdbc.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void save(CustomerDTO dto) {
        final String SQL = "insert into CUSTOMER values (DEFAULT,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            int createdRows = statement.executeUpdate();
            System.out.println("Created: " + createdRows + " customers");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(CustomerDTO customer) {

    }

    @Override
    public void update(CustomerDTO customer) {

    }

    @Override
    public CustomerDTO findById(int id) {
        return null;
    }

    @Override
    public CustomerDTO findByName(String name) {
        return null;
    }

    @Override
    public List<CustomerDTO> findAll() {
        final String SQL = "select * from CUSTOMER ";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();

            List<CustomerDTO> customers = new ArrayList<>();
            while (resultSet.next()) {
                CustomerDTO customerDTO = new CustomerDTO(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4));
                customers.add(customerDTO);
            }
            System.out.println("Found: " + customers.size() + " customers");
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
