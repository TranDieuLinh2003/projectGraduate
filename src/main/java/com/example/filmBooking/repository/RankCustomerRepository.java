package com.example.filmBooking.repository;

import com.example.filmBooking.model.RankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RankCustomerRepository extends JpaRepository<RankCustomer, String> {
}
