package com.example.filmBooking.repository;

import com.example.filmBooking.model.GeneralSetting;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GeneralSettingRepository extends JpaRepository<GeneralSetting, String> {
}
