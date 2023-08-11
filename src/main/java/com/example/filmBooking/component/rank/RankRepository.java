package com.example.filmBooking.component.rank;

import com.example.filmBooking.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RankRepository extends JpaRepository<Rank, UUID> {
}
