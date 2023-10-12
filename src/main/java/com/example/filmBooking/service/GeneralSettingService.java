package com.example.filmBooking.service;

import com.example.filmBooking.model.GeneralSetting;

import java.util.List;


public interface GeneralSettingService {
    List<GeneralSetting> fillAll();

    GeneralSetting save(GeneralSetting GeneralSetting);

    GeneralSetting update(Integer gio1, Integer phut1,
                          Integer gio2, Integer phut2,
                          Integer gio3, Integer phut3,
                          BigDecimal fixedTicketPrice,
                          Integer percentDay,
                          Integer percentWeekend,
                          Integer breakTime);

    void delete(String id);

    GeneralSetting findById(String id);

}
