package com.example.filmBooking.service;

import com.example.filmBooking.model.GeneralSetting;

import java.util.List;


public interface GeneralSettingService {
    List<GeneralSetting> fillAll();

    GeneralSetting save(GeneralSetting GeneralSetting);

    GeneralSetting update(String id, GeneralSetting GeneralSetting);

    void delete(String id);

    GeneralSetting findById(String id);

}
