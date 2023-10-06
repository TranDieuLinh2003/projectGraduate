package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.SeatType;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.repository.SeatTypeRepository;
import com.example.filmBooking.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;


@Service
public class SeatTypeServiceImpl implements SeatTypeService {
    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<SeatType> getAll() {
        return seatTypeRepository.findAll();
    }

    @Override
    public SeatType save(SeatType seatType) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        seatType.setCode("ST" + value);
        return seatTypeRepository.save(seatType);
    }

    @Override
    public SeatType update(String id, SeatType seatType) {
        SeatType seatTypeNew = findById(id);
        seatTypeNew.setName(seatType.getName());
        seatTypeNew.setPrice(seatType.getPrice());
        seatTypeNew.setDescription(seatType.getDescription());
        return seatTypeRepository.save(seatTypeNew);
    }

    @Override
    public void delete(String id) {
        seatTypeRepository.delete(findById(id));
    }

    @Override
    public SeatType findById(String id) {
        return seatTypeRepository.findById(id).get();
    }

//    @Scheduled(cron = "* 0*0 *17,2 * * *")
    public void priceSchedule() {
        List<Schedule> schedules= scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            if (schedule.getStartAt().getHour() >= 17&& schedule.getStartAt().getHour()<=2) {
                List<SeatType> seatTypes = seatTypeRepository.findAll();
                for (SeatType seatType : seatTypes) {
                    // Thay đổi giá ghế suất chiếu trên 17 giờ thành một giá mới (ví dụ 150.00)
                    BigDecimal newPrice = BigDecimal.valueOf(0.1).multiply(seatType.getPrice()).add(seatType.getPrice());
                    seatType.setPrice(newPrice);
                }
            }
        }

        // In ra thông tin sau khi thay đổi giá
        for (Schedule schedule : schedules) {
            System.out.println("Suất chiếu: " + schedule.getId());
            List<SeatType> seats = seatTypeRepository.findAll();
            for (SeatType seat : seats) {
                System.out.println("Ghế: " + seat.getId() + " - Giá: " + seat.getPrice());
            }
        }
    }


}
