package com.example.filmBooking.service;

import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Promotion;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PromotionService {
    List<Promotion> fillAll();

    Promotion save(Promotion promotion);

    Promotion update(String id, Promotion promotion);

    void delete(String id);

    Promotion findById(String id);

    List<Promotion> listVoucherCustomer(String customerId);
}
