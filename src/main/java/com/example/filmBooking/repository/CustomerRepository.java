package com.example.filmBooking.repository;

import com.example.filmBooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, String> {

