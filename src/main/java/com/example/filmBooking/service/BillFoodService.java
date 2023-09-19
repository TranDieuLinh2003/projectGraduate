package com.example.filmBooking.service;

import com.example.filmBooking.model.BillFood;

import java.util.List;


public interface BillFoodService {
    List<BillFood> findAll();

    BillFood save(BillFood billFood);

    BillFood update(String id, BillFood billFood);

    void delete(String id);

    BillFood findById(String id);

}
