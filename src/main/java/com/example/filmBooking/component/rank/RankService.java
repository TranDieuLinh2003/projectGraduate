package com.example.filmBooking.component.rank;

import com.example.filmBooking.model.Rank;

import java.util.List;
import java.util.UUID;

public interface RankService {
    List<Rank> fillAll();

    Rank save(Rank rank);

    Rank findById(UUID id);

    void delete(UUID id);
}
