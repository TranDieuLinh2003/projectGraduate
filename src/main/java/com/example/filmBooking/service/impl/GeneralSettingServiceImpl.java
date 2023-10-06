package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.GeneralSetting;
import com.example.filmBooking.repository.GeneralSettingRepository;
import com.example.filmBooking.service.GeneralSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GeneralSetting update(String id, GeneralSetting generalSetting) {
        GeneralSetting generalSettingNew = findById(id);
        generalSettingNew.setTimeBeginsToChange(generalSetting.getTimeBeginsToChange());
        generalSettingNew.setBreakTime(generalSetting.getBreakTime());
        generalSettingNew.setPercentDay(generalSetting.getPercentDay());
        generalSettingNew.setPercentWeekend(generalSetting.getPercentWeekend());
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
