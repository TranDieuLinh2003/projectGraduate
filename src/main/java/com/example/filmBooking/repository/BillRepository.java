package com.example.filmBooking.repository;

import com.example.filmBooking.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


public interface BillRepository extends JpaRepository<Bill, String> {
    @Query(value = "SELECT * FROM projectLinh.bill b where b.status = 0;", nativeQuery = true)
    Page<Bill> billStatusZero(Pageable pageable);

    @Query(value = "SELECT * FROM projectLinh.bill b where b.status = 1;", nativeQuery = true)
    Page<Bill> billStatusOne(Pageable pageable);

//    Page<Bill> findByDateCreateBetweenAndDateCreate
    Page<Bill> findByDateCreateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = 0")
    String countSoldTicketsWithStatusZero();
}
