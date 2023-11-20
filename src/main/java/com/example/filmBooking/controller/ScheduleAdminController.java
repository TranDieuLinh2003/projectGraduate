package com.example.filmBooking.controller;

import com.example.filmBooking.common.ResponseBean;
import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Room;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.service.CinemaService;
import com.example.filmBooking.service.MovieService;
import com.example.filmBooking.service.RoomService;
import com.example.filmBooking.service.ScheduleService;
import com.example.filmBooking.service.TicketService;
import com.example.filmBooking.service.impl.ScheduleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleAdminController {
    @Autowired
    private ScheduleService service;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private ModelMapper modelMapper;
    ;

    @GetMapping("/find-all")
    public String viewSchedule(Model model) {
        return findAll(model, 1, null, null, null, null, null);
    }

    @GetMapping("/find-all/page/{pageNumber}")
    @Operation(summary = "[Hiển thị tất cả]")
    public String findAll(Model model, @PathVariable("pageNumber") Integer currentPage, @Param(value = "nameCinema") String nameCinema,
                          @Param(value = "nameMovie") String nameMovie,
                          @DateTimeFormat(pattern = "dd/MM/yyyy")
                          @Param(value = "startAt") LocalDate startAt,
                          @Param(value = "startTime") Integer startTime,
                          @Param(value = "endTime") Integer endTime) {
        Page<Schedule> page = scheduleService.getAll(currentPage);
        nameCinema = Strings.isEmpty(nameCinema) ? null : nameCinema;
        nameMovie = Strings.isEmpty(nameMovie) ? null : nameMovie;
        if (nameCinema != null || nameMovie != null || startAt != null || startTime != null || endTime != null) {
            page = scheduleService.searchSchedule(nameCinema, startAt, nameMovie, startTime, endTime, currentPage);
        }
        model.addAttribute("nameCinema", nameCinema);
        model.addAttribute("nameMovie", nameMovie);
        model.addAttribute("startAt", startAt);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listSchedule", page.getContent());
        List<Movie> movieId = movieService.showPhishowPhimSapChieuAndDangChieumSapChieu();
        List<Room> roomId = roomService.fillAll();
        List<Room> roomCapacity = roomService.roomCapacity();
        List<Cinema> cinemaId = cinemaService.fillAll();
        model.addAttribute("roomCapacity", roomCapacity);
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("movieId", movieId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("schedule", new Schedule());
        return "admin/schedule";
    }

    @PostMapping("/update/{pageNumber}")
    @Operation(summary = "[Thêm mới]")
    public String save(Model model,
                       @RequestParam(name = "id") String id,
                       @RequestParam(name = "room") Room room,
                       @RequestParam(name = "movie") Movie movie,
                       @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
                       @RequestParam(name = "startAt") LocalDateTime startAt,
                       @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
                       @RequestParam(name = "finishAt") LocalDateTime finishAt,
//                       @RequestParam(name = "time") LocalTime time,
                       @RequestParam(name = "price") BigDecimal price,
                       @PathVariable("pageNumber") Integer currentPage
    )  {
        try {
            Schedule schedule = Schedule.builder()
                    .id(id)
                    .room(room)
                    .movie(movie)
                    .startAt(startAt)
                    .finishAt(finishAt)
                    .price(price)
                    .build();
            if (service.update(id,schedule) instanceof Schedule) {
                model.addAttribute("thanhCong", "Sửa lịch chiếu thành công");
            } else {
                model.addAttribute("thatBai", "Sửa lịch chiếu thất bại");
            }
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("schedule", new Schedule());
            return "redirect:/schedule/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/schedule";
        }
//        em test xem
//        model.addAttribute("currentPage", currentPage);

    }

   @PostMapping("/batch-save/{pageNumber}")
    @Operation(summary = "[Thêm mới]")
    public String generateSchedule(Model model,
                                   @RequestParam("listRoomChecked") List<String> listRoomChecked,
                                   @RequestParam("listMovieChecked") List<String> listMovieChecked,
                                   @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                   @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
//                                   @RequestParam("room") Room room,
                                   @PathVariable("pageNumber") Integer currentPage
    ) {
        System.out.println("vào rồi");
        try {
            if (service.generateSchedule(listRoomChecked,listMovieChecked, startTime, endTime) instanceof Schedule) {
                model.addAttribute("thanhCong", "Thêm lịch chiếu thành công");
            } else {
                model.addAttribute("thatBai", "Thêm lịch chiếu thất bại");
            }
            model.addAttribute("listRoomChecked", listRoomChecked);
            model.addAttribute("listMovieChecked", listMovieChecked);
            model.addAttribute("currentPage", currentPage);
            return "redirect:/schedule/find-all";
        } catch (Exception e) {
            e.printStackTrace();
            return "admin/schedule";
        }
    }

    @GetMapping("/findById/{pageNumber}/{id}")
//    /{currentPage}
    public String findById(Model model, @PathVariable(name = "id") String id, @PathVariable("pageNumber") Integer currentPage) {
        model.addAttribute("schedule", service.findById(id));
        List<Schedule> scheduleList = scheduleService.findAll();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("listSchedule", scheduleList);
        return "admin/schedule";
    }
}
