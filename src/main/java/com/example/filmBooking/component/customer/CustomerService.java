package com.example.filmBooking.component.customer;

import com.example.filmBooking.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> fillAll();

    Customer save(Customer customer);

    Customer update(UUID id, Customer customer);

    void delete(UUID id);

    Customer findById(UUID id);

}
