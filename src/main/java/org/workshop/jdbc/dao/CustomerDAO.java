package org.workshop.jdbc.dao;

import org.workshop.jdbc.dto.CustomerDTO;

import java.util.List;

public interface CustomerDAO {

    void save(CustomerDTO dto);

    void delete(CustomerDTO customer);

    void update(CustomerDTO customer);

    CustomerDTO findById(int id);

    CustomerDTO findByName(String name);

    List<CustomerDTO> findAll();

}
