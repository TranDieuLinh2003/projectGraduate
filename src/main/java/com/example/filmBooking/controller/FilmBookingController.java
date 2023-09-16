package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.service.ScheduleService;
import com.example.filmBooking.service.impl.CinemaServiceImpl;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/filmbooking")

public class FilmBookingController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieServiceImpl service;

    @Autowired
    private CinemaServiceImpl cinemaService;

    @Autowired
    private ScheduleService scheduleService;


    @RequestMapping(value = "/trangchu", method = RequestMethod.GET)
    public String getAllPosts(Model model) {
//      phim đang chiếu
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);
//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);
//      rap
        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
        model.addAttribute("listcinema", listcinema);


        return "users/FilmBooking";
    }

    @GetMapping("/phimchieu")
    public String showPhimChieu() {
        return "users/Phim";
    }

    @GetMapping("/lichchieu")
    public String showLichChieu(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll(); // Lấy danh sách lịch chiếu từ Service hoặc Repository
        model.addAttribute("scheduleList", scheduleList);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM - EEEE", new Locale("vi"));
        List<String> scheduleDates = scheduleList.stream()
                .map(schedule -> schedule.getStartAt().format(formatter))
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("scheduleDates", scheduleDates);
        return "users/LichChieu";
    }
    @GetMapping("/api/lichchieu")
    public ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(scheduleService.findAll());
    }


}
