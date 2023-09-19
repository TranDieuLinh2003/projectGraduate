package com.example.filmBooking.repository;

import com.example.filmBooking.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BillRepository extends JpaRepository<Bill, String> {

}
