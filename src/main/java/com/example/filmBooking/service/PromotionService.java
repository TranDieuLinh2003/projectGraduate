package com.example.filmBooking.service;

import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Promotion;

import java.util.List;
import java.util.UUID;

public interface PromotionService {
    List<Promotion> fillAll();

    Promotion save(Promotion promotion);

    Promotion update(UUID id, Promotion promotion);

    void delete(UUID id);

    Promotion findById(UUID id);
}
