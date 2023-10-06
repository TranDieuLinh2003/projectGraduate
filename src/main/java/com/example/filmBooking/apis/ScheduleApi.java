package com.example.filmBooking.apis;

import com.example.filmBooking.model.dto.ScheduleDto;
import com.example.filmBooking.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/schedule")
public class ScheduleApi {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/time-at")
    public List<String> getTime(@RequestParam String movieId, @RequestParam String cinemaId ) {
        return scheduleService.getTimeAt(movieId,cinemaId);
    }
    @GetMapping("/startAt-at")
    public List<String> getstartAt(@RequestParam String movieId, @RequestParam String cinemaId) {
        return scheduleService.getStartAt(movieId,cinemaId);
    }
    @GetMapping("/finishAt-at")
    public List<String> getgetFinishAt(@RequestParam String movieId, @RequestParam String cinemaId) {
        return scheduleService.getStartAt(movieId,cinemaId);
    }

//    @GetMapping
//    public List<ScheduleDto> getSchedules(@RequestParam String movieId, @RequestParam String cinemaId,
//                                          @RequestParam String startAt,
//                                          @RequestParam String roomId){
//        return scheduleService.getSchedules(movieId,cinemaId,startAt,roomId);
//    }


}
