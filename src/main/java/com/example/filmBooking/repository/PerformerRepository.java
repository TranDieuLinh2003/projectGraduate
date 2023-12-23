package com.example.filmBooking.repository;

import com.example.filmBooking.model.Performer;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, String> {
    List<Performer> findByNameContains(String keyCode);
}
