package com.example.filmBooking.repository;

import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.BillFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BillFoodRepository extends JpaRepository<BillFood, String> {
    @Query(value = "from BillFood bf where bf.bill.id = :billId ")
    List<BillFood> findAllByBill(String billId);
}
