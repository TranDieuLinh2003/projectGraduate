package com.example.filmBooking.repository;

import com.example.filmBooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("Select custom From Customer custom Where custom.email = :email")
    Customer findEmail(@Param("email") String email);

}
