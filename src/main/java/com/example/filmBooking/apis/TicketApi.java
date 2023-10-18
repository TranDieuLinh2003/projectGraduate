package com.example.filmBooking.apis;

import com.example.filmBooking.model.*;
import com.example.filmBooking.model.dto.DtoSeat;

import com.example.filmBooking.repository.FootRepository;
import com.example.filmBooking.service.impl.FoodServiceImpl;
import com.example.filmBooking.service.impl.ScheduleServiceImpl;
import com.example.filmBooking.service.impl.SeatServiceImpl;
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

    @Autowired
    private SeatServiceImpl seatService;

    @Autowired
    private FootRepository foodService;

    @GetMapping("/show/schedule")
    private ResponseEntity<List<Schedule>> getSchedule(@RequestParam String cinemaId,
                                                       @RequestParam String movieId,
                                                       @RequestParam String startAt,
                                                       @RequestParam String startTime) {
        return new ResponseEntity<>(scheduleService.getSchedule(cinemaId, movieId, startAt, startTime), HttpStatus.OK);
    }


    @GetMapping("/show/seat")
    private ResponseEntity<List<DtoSeat>> getSeat(@RequestParam String cinemaId,
                                                  @RequestParam String movieId,
                                                  @RequestParam String startAt,
                                                  @RequestParam String startTime) {
        return new ResponseEntity<>(seatService.getSeats(cinemaId, movieId, startAt, startTime), HttpStatus.OK);
    }


    @GetMapping("/show/food")
    private ResponseEntity<List<Food>> getAllFood() {
        return new ResponseEntity<>(foodService.findAll(), HttpStatus.OK);
    }
}
