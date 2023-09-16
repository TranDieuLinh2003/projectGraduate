package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.model.dto.ScheduleDto;
import com.example.filmBooking.repository.CinemaRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.impl.CinemaServiceImpl;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import com.example.filmBooking.service.impl.ScheduleServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/filmbooking")

public class FilmBookingController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieServiceImpl service;

    @Autowired
    private CinemaServiceImpl cinemaService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private CinemaRepository repository;

    @GetMapping("/trangchu")
    public String getAllPosts( Model model) {

//      phim đang chiếu
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);

//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);

////      rap
//        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
//        model.addAttribute("listcinema", listcinema);
//
////      phim theo rạp
//        List<ScheduleDto> scheduleDtoList = repository.ListAll();
//        model.addAttribute("scheduleDtoList", scheduleDtoList);
        return "users/FilmBooking";
    }


    @GetMapping("/movie/edit/{id}")
    public String chiTietPhim(@PathVariable("id") UUID id, Model model) {
        Movie movie = service.findById(id);
        model.addAttribute("movie", movie);

        return "users/ChiTietPhim";

    }


    @GetMapping("/phimchieu")
    public String showPhimChieu(Model model) {
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);

//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);

//      rap
        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
        model.addAttribute("listcinema", listcinema);


        return "users/Phim";
    }

    @GetMapping("/lichchieu")
    public String showLichChieu() {
        return "users/LichChieu";
    }

    @GetMapping("/chitietphim")
    public String showChiTietPhim() {
        return "users/ChiTietPhim";
    }


//
//    public static void main(String[] args) {
//        Movie movie = new Movie();
//        movie.setPremiereDate(Date.valueOf(String.valueOf(LocalDateTime.of(2023, 8, 7, 9, 9))));
//        movie.setPremiereDate(Date.valueOf(String.valueOf(LocalDateTime.of(2023, 8, 7, 9, 9))));
//
//        List<String> daysOfWeek = movie.getDaysOfWeek();
//        for (String day : daysOfWeek) {
//            System.out.println(day);
//        }
//    }

}