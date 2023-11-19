package com.example.filmBooking.apis;

import com.example.filmBooking.model.*;
import com.example.filmBooking.model.dto.DtoSeat;

import com.example.filmBooking.repository.FootRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.impl.*;
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

    @Autowired
    private PromotionServiceImpl promotionService;

    @Autowired
    private GeneralSettingServiceImpl generalSettingService;


    @GetMapping("/show/schedule")
    private ResponseEntity<List<Schedule>> getSchedule(@RequestParam String cinemaId,
                                                       @RequestParam String movieId,
                                                       @RequestParam String startAt,
                                                       @RequestParam String startTime,
                                                       @RequestParam String nameRoom) {
        return new ResponseEntity<>(scheduleService.getSchedule(cinemaId, movieId, startAt, startTime,nameRoom), HttpStatus.OK);
    }
    @GetMapping("/show/schedule1")
    private ResponseEntity<List<Schedule>> getSchedule1(@RequestParam String movieName,
                                                       @RequestParam String startAt,
                                                        @RequestParam String nameRoom) {
        return new ResponseEntity<>(scheduleService.getSchedule1(movieName, startAt,nameRoom), HttpStatus.OK);
    }

    @GetMapping("/show/seat")
    private ResponseEntity<List<DtoSeat>> getSeat(@RequestParam String cinemaId,
                                                  @RequestParam String movieId,
                                                  @RequestParam String startAt,
                                                  @RequestParam String startTime,
                                                  @RequestParam String nameRoom) {
        return new ResponseEntity<>(seatService.getSeats(cinemaId, movieId, startAt, startTime,nameRoom), HttpStatus.OK);
    }

    @GetMapping("/show/ticket")
    private ResponseEntity<List<Ticket>> getSTicket(@RequestParam String cinemaId,
                                                  @RequestParam String movieId,
                                                  @RequestParam String startAt,
                                                  @RequestParam String startTime,
                                                    @RequestParam String nameRoom ) {
        return new ResponseEntity<>(ticketService.getTicket(cinemaId, movieId, startAt, startTime,nameRoom), HttpStatus.OK);
    }

    @GetMapping("/show/seat1")
    private ResponseEntity<List<DtoSeat>> getSeat1(@RequestParam String movieName,
                                                  @RequestParam String startAt,
                                                   @RequestParam String nameRoom) {
        return new ResponseEntity<>(seatService.getSeats1(movieName, startAt,nameRoom), HttpStatus.OK);
    }

    @GetMapping("/show/food")
    private ResponseEntity<List<Food>> getAllFood() {
        return new ResponseEntity<>(foodService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/show/voucher")
    private ResponseEntity<List<Promotion>> getAllVoucher(@RequestParam String customerId) {
        return new ResponseEntity<>(promotionService.listVoucherCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/show/generalSetting")
    private ResponseEntity<List<GeneralSetting>> getAllGeneralSetting() {
        return new ResponseEntity<>(generalSettingService.fillAll(), HttpStatus.OK);
    }

}
