package com.example.filmBooking.service;

import com.example.filmBooking.model.Customer;

import java.util.List;


public interface CustomerService {
    List<Customer> fillAll();

    Customer save(Customer customer);

    Customer update(String id, Customer customer);

    void delete(String id);

    Customer findById(String id);

    void autoCheckPoint();

    List<Customer> findByPromotion(String idPromotion);

    Customer findByEmail(String email);

}
