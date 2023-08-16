package com.example.filmBooking.service;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.Food;
import com.example.filmBooking.model.Rank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
public interface FoodService {
    List<Food> fillAll();

    Food save(Food food);

    Food update(UUID id, Food food);

    void delete(UUID id);

    Food findById(UUID id);

}
