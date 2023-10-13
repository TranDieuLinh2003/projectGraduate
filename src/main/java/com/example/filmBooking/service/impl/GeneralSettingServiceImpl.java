package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.GeneralSetting;
import com.example.filmBooking.repository.GeneralSettingRepository;
import com.example.filmBooking.service.GeneralSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;


@Service
public class GeneralSettingServiceImpl implements GeneralSettingService {

    @Autowired
    private GeneralSettingRepository repository;

    @Override
    public List<GeneralSetting> fillAll() {
        return repository.findAll();
    }

    @Override
    public GeneralSetting save(GeneralSetting GeneralSetting) {
//        Random generator = new Random();
//        int value = generator.nextInt((100000 - 1) + 1) + 1;
//        GeneralSetting.setCode("code_" + value);
        return repository.save(GeneralSetting);
    }

    @Override
    public GeneralSetting update(Integer gio1, Integer phut1,
                                 Integer gio2, Integer phut2,
                                 Integer gio3, Integer phut3,
                                 BigDecimal fixedTicketPrice,
                                 Integer percentDay,
                                 Integer percentWeekend,
                                 Integer breakTime) {
        GeneralSetting generalSettingNew = findById("613d6a30-167e-4b7c-985e-b510dc9bae25");
        generalSettingNew.setTimeBeginsToChange(LocalTime.of(gio1, phut1));
        generalSettingNew.setBusinessHours(LocalTime.of(gio2, phut2));
        generalSettingNew.setCloseTime(LocalTime.of(gio3, phut3));
        generalSettingNew.setFixedTicketPrice(fixedTicketPrice);
        generalSettingNew.setBreakTime(breakTime);
        generalSettingNew.setPercentDay(percentDay);
        generalSettingNew.setPercentWeekend(percentWeekend);
        return repository.save(generalSettingNew);
    }

    @Override
    public GeneralSetting findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
