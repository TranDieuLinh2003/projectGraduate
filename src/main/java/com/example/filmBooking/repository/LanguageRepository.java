package com.example.filmBooking.repository;

import com.example.filmBooking.model.Language;
import com.example.filmBooking.model.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, String> {
    List<Language> findByNameContains(String keyCode);
}
