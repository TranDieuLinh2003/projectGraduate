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
    public GeneralSetting update(LocalTime timeBeginsToChange,
                                 LocalTime businessHours,
                                 LocalTime closeTime,
                                 BigDecimal fixedTicketPrice,
                                 Integer percentDay,
                                 Integer percentWeekend,
                                 Integer breakTime,
                                 Integer waitingTime
    ) {
        GeneralSetting generalSettingNew = findById("hihi");
        generalSettingNew.setTimeBeginsToChange(timeBeginsToChange);
        generalSettingNew.setBusinessHours(businessHours);
        generalSettingNew.setCloseTime(closeTime);
        generalSettingNew.setFixedTicketPrice(fixedTicketPrice);
        generalSettingNew.setBreakTime(breakTime);
        generalSettingNew.setPercentDay(percentDay);
        generalSettingNew.setPercentWeekend(percentWeekend);
        generalSettingNew.setWaitingTime(waitingTime);
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
