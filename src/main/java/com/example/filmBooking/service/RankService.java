package com.example.filmBooking.service;

import com.example.filmBooking.model.Rank;

import java.util.List;


public interface RankService {
    List<Rank> fillAll();

    Rank save(Rank rank);

    Rank update(String id, Rank rank);

    void delete(String id);

    Rank findById(String id);

}
