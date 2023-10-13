package com.example.filmBooking.apis;

import com.example.filmBooking.model.*;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import com.example.filmBooking.service.impl.RoomServiceImpl;
import com.example.filmBooking.service.impl.ScheduleServiceImpl;
import com.example.filmBooking.service.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/ticket")
public class TicketApi {

    @Autowired
    private TicketServiceImpl ticketService;

    @Autowired
    private ScheduleServiceImpl scheduleService;


    @GetMapping("/schedule")
    private ResponseEntity<List<Schedule>> getSchedule(@RequestParam String cinemaId,
                                                       @RequestParam String movieId,
                                                       @RequestParam String startAt,
                                                       @RequestParam String startTime) {
        return new ResponseEntity<>(scheduleService.getSchedule(cinemaId, movieId, startAt,startTime), HttpStatus.OK);
    }


}
