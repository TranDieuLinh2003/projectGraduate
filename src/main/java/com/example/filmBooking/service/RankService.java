package com.example.filmBooking.service;

import com.example.filmBooking.model.Rank;

import java.util.List;
import java.util.UUID;

public interface RankService {
    List<Rank> fillAll();

    Rank save(Rank rank);

    Rank update(UUID id, Rank rank);

    void delete(UUID id);

    Rank findById(UUID id);

}
