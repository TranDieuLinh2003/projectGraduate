package com.example.filmBooking.service;

import com.example.filmBooking.model.Food;

import java.util.List;

public interface FoodService {
    List<Food> fillAll();

    Food save(Food food);

    Food update(String id, Food food);

    void delete(String id);

    Food findById(String id);

}
