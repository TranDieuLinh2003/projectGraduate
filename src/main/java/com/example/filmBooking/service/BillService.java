package com.example.filmBooking.service;

import com.example.filmBooking.model.Bill;

import java.util.List;


public interface BillService {
    List<Bill> findAll();

    Bill save(Bill bill);

    Bill update(String id, Bill bill);

    void delete(String id);

    Bill findById(String id);

}
