package com.example.filmBooking.service;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.Rank;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> fillAll();

    Customer save(Customer customer);

    Customer update(UUID id, Customer customer);

    void delete(UUID id);

    Customer findById(UUID id);

    String soSanhPoint(List<Rank> listRank, UUID id);

}
